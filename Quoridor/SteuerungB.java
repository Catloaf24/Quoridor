import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

//Steuerung an/aus mit steuerungB.setAktiv(false/true)
//Steuerung aktiv? -> steuerungR.istAktiv()

public class SteuerungB
{
    private static final int FELD_X = 252;  // linker Rand des Spielfelds in Pixel
    private static final int FELD_Y = 252;  // oberer Rand des Spielfelds in Pixel
    private static final int ZELLE = 64;    // Größe eines Feldes in Pixel
    private static final int ANZAHL = 9;    // Felder pro Reihe / Spalte

    private final JComponent spielerRed;    // die andere Figur (zum Überspringen)
    private final JComponent spielerBlue;   // die eigene Figur

    private boolean aktiv = true;

    NewSpielleiter sl;

    // Verhindert, dass gedrückt halten mehrere Felder auf einmal läuft
    private final Set<Integer> gedrueckt = new HashSet<>();

    /**
     * @param komponente
     * @param spielerRed
     * @param spielerBlue
     */
    public SteuerungB(JComponent komponente, JComponent spielerRed, JComponent spielerBlue)
    {
        this.spielerRed = spielerRed;
        this.spielerBlue = spielerBlue;

        InputMap im = komponente.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = komponente.getActionMap();

        belegeTaste(im, am, KeyEvent.VK_UP, 0, -1);
        belegeTaste(im, am, KeyEvent.VK_DOWN, 0, 1);
        belegeTaste(im, am, KeyEvent.VK_LEFT, -1, 0);
        belegeTaste(im, am, KeyEvent.VK_RIGHT, 1, 0);
    }

    public void setSL(NewSpielleiter sl) {
        this.sl = sl;
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

    // Ist die Steuerung aktiv?
    public boolean istAktiv()
    {
        return aktiv;
    }

    private void belegeTaste(InputMap im, ActionMap am, int keyCode, int dx, int dy)
    {
        String nameAn = "B_an_" + keyCode;
        String nameAus = "B_aus_" + keyCode;

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

    // Bewegt die blaue Figur um dx / dy Felder, falls das Zielfeld erlaubt ist
    private void bewege(int dx, int dy)
    {
        int neuX = spielerBlue.getX() + dx * ZELLE;
        int neuY = spielerBlue.getY() + dy * ZELLE;

        // Nicht aus dem Spielfeld laufen
        if (neuX < FELD_X || neuX > FELD_X + (ANZAHL - 1) * ZELLE) return;
        if (neuY < FELD_Y || neuY > FELD_Y + (ANZAHL - 1) * ZELLE) return;

        // Steht der andere Spieler auf dem Zielfeld, wird er übersprungen
        if (spielerRed.getX() == neuX && spielerRed.getY() == neuY)
        {
            neuX += dx * ZELLE;
            neuY += dy * ZELLE;

            // Hinter dem anderen Spieler ist kein Platz (Spielfeldrand) -> kein Zug
            if (neuX < FELD_X || neuX > FELD_X + (ANZAHL - 1) * ZELLE) return;
            if (neuY < FELD_Y || neuY > FELD_Y + (ANZAHL - 1) * ZELLE) return;
        }

        //NewSpielleiter.validateMove(cPos, Vec2 nPos).

        spielerBlue.setLocation(neuX, neuY);

        // PLATZHALTER 3: Spiellogik über den Zug informieren (z. B. Position speichern, Spieler wechseln)
        // deineSpiellogik.zugGemacht(spielerBlue, dx, dy);
    }
}
