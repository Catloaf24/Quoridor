import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

//Wand-Steuerung für Spieler BLAU: Maus auf eine Kante -> halbtransparente Vorschau, Linksklick -> Wand setzen
//Steuerung an/aus mit wandB.setAktiv(false/true)
//Steuerung aktiv? -> wandB.istAktiv()
//Wand-Modus (Vorschau + Klick) wird mit den Tasten - (Minus) umgeschaltet: wandB.toggleWandModus()

public class WandSteuerungB
{
    private static final String TEXTUR_HOR  = "Textures/WallBHor.png";
    private static final String TEXTUR_VERT = "Textures/WallBVert.png";

    private static final float VORSCHAU_ALPHA = 0.5f;
    private static final Integer WAND_LAYER = Integer.valueOf(3);   // gleiche Ebene wie die Vorrats-Wände

    private final JLayeredPane layers;
    private final JComponent spielerRed;
    private final JComponent spielerBlue;
    private final WandBrett brett;          // gemeinsamer Wand-Zustand (derselbe für Blau und Rot!)
    private final JComponent[] vorrat;      // die Vorrats-Wände von Blau (oben im GUI), darf null sein

    private final Image bildHor;
    private final Image bildVert;
    private final VorschauPanel vorschauPanel = new VorschauPanel();

    private static final int[] TOGGLE_TASTEN = { KeyEvent.VK_MINUS, KeyEvent.VK_SUBTRACT };

    private boolean aktiv = true;
    private boolean wandModus = false;      // true = Spieler ist gerade am Wand setzen
    private final Set<Integer> gedrueckt = new HashSet<>();   // Verhindert, dass gedrückt halten ständig umschaltet
    private int uebrigeWaende;

    private WandBrett.Position vorschau = null;     // null = keine Vorschau
    private boolean vorschauGueltig = false;

    private Runnable platziertListener = null;

    /**
     * @param layers      das JLayeredPane aus der GUI
     * @param spielerRed  rote Figur (für die Wegprüfung)
     * @param spielerBlue blaue Figur (für die Wegprüfung)
     * @param brett       gemeinsamer Wand-Zustand
     * @param vorrat      die 10 Vorrats-Wände von Blau; pro gesetzter Wand wird eine ausgeblendet (oder null)
     */
    public WandSteuerungB(JLayeredPane layers, JComponent spielerRed, JComponent spielerBlue,
                          WandBrett brett, JComponent[] vorrat)
    {
        this.layers = layers;
        this.spielerRed = spielerRed;
        this.spielerBlue = spielerBlue;
        this.brett = brett;
        this.vorrat = vorrat;
        this.uebrigeWaende = (vorrat != null) ? vorrat.length : 10;

        bildHor = new ImageIcon(TEXTUR_HOR).getImage();
        bildVert = new ImageIcon(TEXTUR_VERT).getImage();

        // Overlay für die Vorschau: ganz oben, durchsichtig, ohne eigene Maus-Listener (Klicks gehen durch)
        Dimension groesse = layers.getPreferredSize();
        vorschauPanel.setBounds(0, 0, groesse.width, groesse.height);
        layers.add(vorschauPanel, JLayeredPane.PALETTE_LAYER);

        MouseAdapter maus = new MouseAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                aktualisiereVorschau(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e)
            {
                aktualisiereVorschau(e.getX(), e.getY());
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                loescheVorschau();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.getButton() != MouseEvent.BUTTON1) return;
                aktualisiereVorschau(e.getX(), e.getY());
                platziereVorschau();
            }
        };
        layers.addMouseListener(maus);
        layers.addMouseMotionListener(maus);

        // Tasten zum Umschalten des Wand-Modus
        InputMap im = layers.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = layers.getActionMap();
        for (int taste : TOGGLE_TASTEN)
        {
            belegeTaste(im, am, taste);
        }
    }

    private void belegeTaste(InputMap im, ActionMap am, int keyCode)
    {
        String nameAn = "WandB_an_" + keyCode;
        String nameAus = "WandB_aus_" + keyCode;

        im.put(KeyStroke.getKeyStroke(keyCode, 0, false), nameAn);
        am.put(nameAn, new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!aktiv) return;
                if (gedrueckt.add(keyCode))
                {
                    toggleWandModus();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke(keyCode, 0, true), nameAus);
        am.put(nameAus, new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                gedrueckt.remove(keyCode);
            }
        });
    }

    //Schaltet die Steuerung ein oder aus
    public void setAktiv(boolean aktiv)
    {
        this.aktiv = aktiv;
        if (!aktiv)
        {
            wandModus = false;
            gedrueckt.clear();
            loescheVorschau();
        }
    }

    // Wand-Modus ein/aus (an = Vorschau folgt der Maus, Klick setzt die Wand)
    public void setWandModus(boolean an)
    {
        if (an && !aktiv) return;
        wandModus = an;
        if (!an)
        {
            loescheVorschau();
            return;
        }

        // Vorschau sofort an der aktuellen Mausposition zeigen
        PointerInfo info = MouseInfo.getPointerInfo();
        if (info != null && layers.isShowing())
        {
            Point p = info.getLocation();
            SwingUtilities.convertPointFromScreen(p, layers);
            aktualisiereVorschau(p.x, p.y);
        }
    }

    public void toggleWandModus()
    {
        setWandModus(!wandModus);
    }

    public boolean istWandModus()
    {
        return wandModus;
    }

    // Ist die Steuerung aktiv?
    public boolean istAktiv()
    {
        return aktiv;
    }

    // Wie viele Wände hat Blau noch?
    public int getUebrigeWaende()
    {
        return uebrigeWaende;
    }

    // Wird nach jeder erfolgreich gesetzten Wand aufgerufen (z. B. um den Spieler zu wechseln)
    public void setPlatziertListener(Runnable listener)
    {
        this.platziertListener = listener;
    }

    // ------------------------------------------------------------------

    private void aktualisiereVorschau(int mausX, int mausY)
    {
        if (!aktiv || !wandModus)
        {
            loescheVorschau();
            return;
        }

        WandBrett.Position neu = WandBrett.findeKante(mausX, mausY);
        boolean gueltig = neu != null && darfPlatzieren(neu);

        if (java.util.Objects.equals(neu, vorschau) && gueltig == vorschauGueltig) return;

        Rectangle alt = vorschauRechteck();
        vorschau = neu;
        vorschauGueltig = gueltig;
        neuZeichnen(alt);
        neuZeichnen(vorschauRechteck());
    }

    private void loescheVorschau()
    {
        if (vorschau == null) return;
        Rectangle alt = vorschauRechteck();
        vorschau = null;
        vorschauGueltig = false;
        neuZeichnen(alt);
    }

    private boolean darfPlatzieren(WandBrett.Position p)
    {
        if (uebrigeWaende <= 0) return false;

        return brett.darfPlatzieren(p,
                spalte(spielerBlue), zeile(spielerBlue),
                spalte(spielerRed), zeile(spielerRed));
    }

    private void platziereVorschau()
    {
        if (!aktiv || !wandModus || vorschau == null || !vorschauGueltig) return;

        WandBrett.Position p = vorschau;
        Rectangle r = WandBrett.grenzen(p);

        brett.setze(p);
        uebrigeWaende--;

        // Eine Wand aus dem Vorrat entfernen
        if (vorrat != null && uebrigeWaende >= 0 && uebrigeWaende < vorrat.length)
        {
            vorrat[uebrigeWaende].setVisible(false);
        }

        // Die feste Wand ins Spielfeld setzen
        WallPanelB wand = new WallPanelB(p.horizontal ? TEXTUR_HOR : TEXTUR_VERT);
        wand.setOpaque(false);
        wand.setBounds(r);
        layers.add(wand, WAND_LAYER);
        layers.repaint(r);

        vorschau = null;
        vorschauGueltig = false;
        neuZeichnen(r);

        if (platziertListener != null)
        {
            platziertListener.run();
        }
    }

    private Rectangle vorschauRechteck()
    {
        return (vorschau == null) ? null : WandBrett.grenzen(vorschau);
    }

    private void neuZeichnen(Rectangle r)
    {
        if (r == null) return;
        vorschauPanel.repaint(r.x - 2, r.y - 2, r.width + 4, r.height + 4);
    }

    private static int spalte(JComponent figur)
    {
        return (figur.getX() - WandBrett.FELD_X) / WandBrett.ZELLE;
    }

    private static int zeile(JComponent figur)
    {
        return (figur.getY() - WandBrett.FELD_Y) / WandBrett.ZELLE;
    }

    // ------------------------------------------------------------------

    // Zeichnet die halbtransparente Wand (ungültig = rot eingefärbt)
    private class VorschauPanel extends JComponent
    {
        @Override
        protected void paintComponent(Graphics g)
        {
            if (vorschau == null) return;

            Graphics2D g2 = (Graphics2D) g.create();
            Rectangle r = WandBrett.grenzen(vorschau);
            Image bild = vorschau.horizontal ? bildHor : bildVert;

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, VORSCHAU_ALPHA));
            if (vorschauGueltig)
            {
                if (bild.getWidth(null) > 0)
                    g2.drawImage(bild, r.x, r.y, r.width, r.height, null);
                else
                {
                    g2.setColor(new Color(40, 90, 220));
                    g2.fillRect(r.x, r.y, r.width, r.height);
                }
            }
            else
            {
                g2.setColor(new Color(220, 40, 40));
                g2.fillRect(r.x, r.y, r.width, r.height);
            }
            g2.dispose();
        }
    }
}