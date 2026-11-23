import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

//Steuerung an/aus mit steuerungR.setAktiv(false/true)
//Steuerung aktiv? -> steuerungR.istAktiv()

public class SteuerungR
{
    // PLATZHALTER 1: Maße deines Spielfelds anpassen
    private static final int FELD_X = 252;  // linker Rand des Spielfelds in Pixel
    private static final int FELD_Y = 252;  // oberer Rand des Spielfelds in Pixel
    private static final int ZELLE = 64;    // Größe eines Feldes in Pixel
    private static final int ANZAHL = 9;    // Felder pro Reihe / Spalte

    private final JComponent spielerRed;    // die eigene Figur
    private final JComponent spielerBlue;   // die andere Figur (zum Überspringen)

    // Ist die Steuerung gerade an? (am Anfang: an)
    private boolean aktiv = true;

    NewSpielleiter sl;
    NewSpieler s2;

    // Verhindert, dass gedrückt halten mehrere Felder auf einmal läuft
    private final Set<Integer> gedrueckt = new HashSet<>();

    /**
     * @param komponente
     * @param spielerRed
     * @param spielerBlue
     */
    public SteuerungR(JComponent komponente, JComponent spielerRed, JComponent spielerBlue)
    {
        this.spielerRed = spielerRed;
        this.spielerBlue = spielerBlue;

        InputMap im = komponente.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = komponente.getActionMap();

        belegeTaste(im, am, KeyEvent.VK_W, 0, -1);
        belegeTaste(im, am, KeyEvent.VK_S, 0, 1);
        belegeTaste(im, am, KeyEvent.VK_A, -1, 0);
        belegeTaste(im, am, KeyEvent.VK_D, 1, 0);
    }

    public void setSL(NewSpielleiter sl) {
        this.sl = sl;
    }

    public void setSpieler(NewSpieler s1) {
        this.s2 = s2;
    }

    //Schaltet die Steuerung ein oder aus
    public void setAktiv(boolean aktiv)
    {
        this.aktiv = aktiv;
        if (!aktiv)
        {
            gedrueckt.clear();
        }
    }

    /** Ist die Steuerung gerade eingeschaltet? */
    public boolean istAktiv()
    {
        return aktiv;
    }

    private void belegeTaste(InputMap im, ActionMap am, int keyCode, int dx, int dy)
    {
        String nameAn = "R_an_" + keyCode;
        String nameAus = "R_aus_" + keyCode;

        im.put(KeyStroke.getKeyStroke(keyCode, 0, false), nameAn);
        am.put(nameAn, new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!aktiv) return;
                if (gedrueckt.add(keyCode))
                {
                    bewege(dx, dy);
                    steuerungR.setAktiv(false);
                    steuerungB.setAktiv(true);
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

    // Bewegt die rote Figur um dx / dy Felder, falls das Zielfeld erlaubt ist
    private void bewege(int dx, int dy)
    {
        int neuX = spielerRed.getX() + dx * ZELLE;
        int neuY = spielerRed.getY() + dy * ZELLE;

        // Nicht aus dem Spielfeld laufen
        if (neuX < FELD_X || neuX > FELD_X + (ANZAHL - 1) * ZELLE) return;
        if (neuY < FELD_Y || neuY > FELD_Y + (ANZAHL - 1) * ZELLE) return;

        // Steht der andere Spieler auf dem Zielfeld, wird er übersprungen
        if (spielerBlue.getX() == neuX && spielerBlue.getY() == neuY)
        {
            neuX += dx * ZELLE;
            neuY += dy * ZELLE;

            // Hinter dem anderen Spieler ist kein Platz (Spielfeldrand) -> kein Zug
            if (neuX < FELD_X || neuX > FELD_X + (ANZAHL - 1) * ZELLE) return;
            if (neuY < FELD_Y || neuY > FELD_Y + (ANZAHL - 1) * ZELLE) return;
        }

        spielerRed.setLocation(neuX, neuY);

        // PLATZHALTER 3: Spiellogik über den Zug informieren (z. B. Position speichern, Spieler wechseln)
        //Spielleiter.MakeMove(1, dx, dy);
        movePlayer(NewSpieler player, Vec2 nPos);
        
    }
}
