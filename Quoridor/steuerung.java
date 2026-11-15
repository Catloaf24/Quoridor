import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class steuerung
{
    private static final int FELD_X = 252;  // linker Rand des Spielfelds in Pixel
    private static final int FELD_Y = 252;  // oberer Rand des Spielfelds in Pixel
    private static final int ZELLE = 64;    // Größe eines Feldes in Pixel
    private static final int ANZAHL = 9;    // Felder pro Reihe / Spalte

    private final JComponent spielerRed;
    private final JComponent spielerBlue;

    // Verhindert, dass gedrückt halten mehrere Felder auf einmal läuft
    private final Set<Integer> gedrueckt = new HashSet<>();

    /**
     * @param komponente
     * @param spielerRed
     * @param spielerBlue
     */
    public steuerung(JComponent komponente, JComponent spielerRed, JComponent spielerBlue)
    {
        this.spielerRed = spielerRed;
        this.spielerBlue = spielerBlue;

        InputMap im = komponente.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = komponente.getActionMap();

        // Spieler Rot: WASD
        belegeTaste(im, am, KeyEvent.VK_W, spielerRed, 0, -1);
        belegeTaste(im, am, KeyEvent.VK_S, spielerRed, 0, 1);
        belegeTaste(im, am, KeyEvent.VK_A, spielerRed, -1, 0);
        belegeTaste(im, am, KeyEvent.VK_D, spielerRed, 1, 0);

        // Spieler Blau: Pfeiltasten
        belegeTaste(im, am, KeyEvent.VK_UP, spielerBlue, 0, -1);
        belegeTaste(im, am, KeyEvent.VK_DOWN, spielerBlue, 0, 1);
        belegeTaste(im, am, KeyEvent.VK_LEFT, spielerBlue, -1, 0);
        belegeTaste(im, am, KeyEvent.VK_RIGHT, spielerBlue, 1, 0);
    }

    private void belegeTaste(InputMap im, ActionMap am, int keyCode, JComponent spieler, int dx, int dy)
    {
        String nameAn = "an_" + keyCode;
        String nameAus = "aus_" + keyCode;

        im.put(KeyStroke.getKeyStroke(keyCode, 0, false), nameAn);
        am.put(nameAn, new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (gedrueckt.add(keyCode))
                {
                    bewege(spieler, dx, dy);
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

    // Bewegt ein Panel um dx / dy Felder, falls das Zielfeld erlaubt ist
    // Bewegt ein Panel um dx / dy Felder, falls das Zielfeld erlaubt ist
private void bewege(JComponent spieler, int dx, int dy)
{
    int neuX = spieler.getX() + dx * ZELLE;
    int neuY = spieler.getY() + dy * ZELLE;

    // Nicht aus dem Spielfeld laufen
    if (neuX < FELD_X || neuX > FELD_X + (ANZAHL - 1) * ZELLE) return;
    if (neuY < FELD_Y || neuY > FELD_Y + (ANZAHL - 1) * ZELLE) return;

    // Steht der andere Spieler auf dem Zielfeld, wird er übersprungen
    JComponent anderer = (spieler == spielerRed) ? spielerBlue : spielerRed;
    if (anderer.getX() == neuX && anderer.getY() == neuY)
    {
        neuX += dx * ZELLE;
        neuY += dy * ZELLE;

        // Hinter dem anderen Spieler ist kein Platz (Spielfeldrand) -> kein Zug
        if (neuX < FELD_X || neuX > FELD_X + (ANZAHL - 1) * ZELLE) return;
        if (neuY < FELD_Y || neuY > FELD_Y + (ANZAHL - 1) * ZELLE) return;
    }

    // PLATZHALTER 2: Regelprüfung deiner Spiellogik (z. B. Wände, wer ist am Zug)
    // if (!deineSpiellogik.darfZiehen(spieler, dx, dy)) return;

    spieler.setLocation(neuX, neuY);

    // PLATZHALTER 3: Spiellogik über den Zug informieren (z. B. Position speichern, Spieler wechseln)
    // deineSpiellogik.zugGemacht(spieler, dx, dy);
}
}