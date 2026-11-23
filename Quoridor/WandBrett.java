import java.awt.Rectangle;
import java.util.ArrayDeque;

public class WandBrett
{
    public static final int FELD_X  = 252;
    public static final int FELD_Y  = 252;  
    public static final int ZELLE   = 64;   
    public static final int ANZAHL  = 9;    
    public static final int KNOTEN  = ANZAHL - 1;

    public static final int WAND_DICKE  = 7;            
    public static final int WAND_LAENGE = 2 * ZELLE;    
    public static final int KANTEN_TOLERANZ = 12;       

    public static final int ZIELZEILE_BLAU = 0;         
    public static final int ZIELZEILE_ROT  = ANZAHL - 1;

    /** Eine Wandposition: Ausrichtung + Knoten. */
    public static class Position
    {
        public final boolean horizontal;
        public final int a;
        public final int b;

        public Position(boolean horizontal, int a, int b)
        {
            this.horizontal = horizontal;
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o)
        {
            if (!(o instanceof Position)) return false;
            Position p = (Position) o;
            return p.horizontal == horizontal && p.a == a && p.b == b;
        }

        @Override
        public int hashCode()
        {
            return (horizontal ? 1 : 0) + 2 * a + 20 * b;
        }
    }

    private final boolean[][] horizontal = new boolean[KNOTEN][KNOTEN]; // [a][b]
    private final boolean[][] vertikal   = new boolean[KNOTEN][KNOTEN]; // [a][b]

    // ------------------------------------------------------------------
    // Maus -> Wand
    // ------------------------------------------------------------------

    /**
     * Wandposition zu einer Mausposition (Pixel im Spielfenster).
     * Gibt null zurück, wenn die Maus nicht nah genug an einer inneren Kante liegt.
     */
    public static Position findeKante(int mausX, int mausY)
    {
        double fx = (mausX - FELD_X) / (double) ZELLE;  // Position in Feldeinheiten
        double fy = (mausY - FELD_Y) / (double) ZELLE;

        // Kandidat vertikale Kante: nächste senkrechte Linie 1..8
        int k = (int) Math.round(fx);
        double distV = Math.abs(fx - k) * ZELLE;
        boolean vOk = k >= 1 && k <= KNOTEN && fy >= 0 && fy <= ANZAHL && distV <= KANTEN_TOLERANZ;

        // Kandidat horizontale Kante: nächste waagrechte Linie 1..8
        int l = (int) Math.round(fy);
        double distH = Math.abs(fy - l) * ZELLE;
        boolean hOk = l >= 1 && l <= KNOTEN && fx >= 0 && fx <= ANZAHL && distH <= KANTEN_TOLERANZ;

        if (!vOk && !hOk) return null;

        if (vOk && (!hOk || distV < distH))
        {
            // Wand liegt auf Linie k, mittig auf dem nächsten Knoten entlang der Linie
            int b = klemme((int) Math.round(fy), 1, KNOTEN) - 1;
            return new Position(false, k - 1, b);
        }
        else
        {
            int a = klemme((int) Math.round(fx), 1, KNOTEN) - 1;
            return new Position(true, a, l - 1);
        }
    }

    /** Pixel-Rechteck einer Wand (so groß wie die Texturen 128x7 bzw. 7x128). */
    public static Rectangle grenzen(Position p)
    {
        if (p.horizontal)
        {
            int x = FELD_X + p.a * ZELLE;
            int y = FELD_Y + (p.b + 1) * ZELLE - WAND_DICKE / 2;
            return new Rectangle(x, y, WAND_LAENGE, WAND_DICKE);
        }
        else
        {
            int x = FELD_X + (p.a + 1) * ZELLE - WAND_DICKE / 2;
            int y = FELD_Y + p.b * ZELLE;
            return new Rectangle(x, y, WAND_DICKE, WAND_LAENGE);
        }
    }

    // ------------------------------------------------------------------
    // Regeln
    // ------------------------------------------------------------------

    /** Ist an dieser Stelle Platz (keine Überlappung, keine Kreuzung)? */
    public boolean istFrei(Position p)
    {
        int a = p.a, b = p.b;
        if (a < 0 || b < 0 || a >= KNOTEN || b >= KNOTEN) return false;

        if (p.horizontal)
        {
            if (horizontal[a][b]) return false;
            if (vertikal[a][b]) return false;                  // Kreuzung
            if (a > 0 && horizontal[a - 1][b]) return false;   // überlappt links
            if (a < KNOTEN - 1 && horizontal[a + 1][b]) return false; // überlappt rechts
        }
        else
        {
            if (vertikal[a][b]) return false;
            if (horizontal[a][b]) return false;                // Kreuzung
            if (b > 0 && vertikal[a][b - 1]) return false;     // überlappt oben
            if (b < KNOTEN - 1 && vertikal[a][b + 1]) return false; // überlappt unten
        }
        return true;
    }

    /**
     * Darf die Wand gesetzt werden? Prüft Platz UND dass beide Spieler
     * danach noch einen Weg zu ihrer Zielzeile haben.
     */
    public boolean darfPlatzieren(Position p, int blauSpalte, int blauZeile, int rotSpalte, int rotZeile)
    {
        if (!istFrei(p)) return false;

        setze(p);   // probeweise
        boolean ok = hatWeg(blauSpalte, blauZeile, ZIELZEILE_BLAU)
                  && hatWeg(rotSpalte, rotZeile, ZIELZEILE_ROT);
        entferne(p);
        return ok;
    }

    public void setze(Position p)
    {
        if (p.horizontal) horizontal[p.a][p.b] = true;
        else              vertikal[p.a][p.b] = true;
    }

    public void entferne(Position p)
    {
        if (p.horizontal) horizontal[p.a][p.b] = false;
        else              vertikal[p.a][p.b] = false;
    }

    /**
     * Versperrt eine Wand den Schritt von Feld (spalte, zeile) um (dx, dy)?
     * Auch für die Figurenbewegung nutzbar (liefert auch true am Spielfeldrand).
     */
    public boolean istBlockiert(int spalte, int zeile, int dx, int dy)
    {
        int zs = spalte + dx;
        int zz = zeile + dy;
        if (zs < 0 || zs >= ANZAHL || zz < 0 || zz >= ANZAHL) return true;

        if (dx == 1)  return v(spalte, zeile - 1) || v(spalte, zeile);
        if (dx == -1) return v(spalte - 1, zeile - 1) || v(spalte - 1, zeile);
        if (dy == 1)  return h(spalte - 1, zeile) || h(spalte, zeile);
        if (dy == -1) return h(spalte - 1, zeile - 1) || h(spalte, zeile - 1);
        return false;
    }

    /** Gibt es (Breitensuche) einen Weg von diesem Feld zur Zielzeile? */
    public boolean hatWeg(int startSpalte, int startZeile, int zielZeile)
    {
        boolean[][] besucht = new boolean[ANZAHL][ANZAHL];
        ArrayDeque<int[]> warteschlange = new ArrayDeque<>();
        warteschlange.add(new int[] { startSpalte, startZeile });
        besucht[startSpalte][startZeile] = true;

        int[][] richtungen = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        while (!warteschlange.isEmpty())
        {
            int[] f = warteschlange.poll();
            if (f[1] == zielZeile) return true;

            for (int[] r : richtungen)
            {
                if (istBlockiert(f[0], f[1], r[0], r[1])) continue;
                int ns = f[0] + r[0];
                int nz = f[1] + r[1];
                if (besucht[ns][nz]) continue;
                besucht[ns][nz] = true;
                warteschlange.add(new int[] { ns, nz });
            }
        }
        return false;
    }

    // ------------------------------------------------------------------

    private boolean h(int a, int b)
    {
        return a >= 0 && b >= 0 && a < KNOTEN && b < KNOTEN && horizontal[a][b];
    }

    private boolean v(int a, int b)
    {
        return a >= 0 && b >= 0 && a < KNOTEN && b < KNOTEN && vertikal[a][b];
    }

    private static int klemme(int wert, int min, int max)
    {
        return Math.max(min, Math.min(max, wert));
    }
}
