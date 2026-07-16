

public class Spieler 
{
    int ubrigeWande = 10;
    Vec2 pos;
    Direction orientation;

    public Spieler(Direction side) 
    {
        this.orientation = side;
        // Startposition direkt über die Logik der Seite bestimmen
        this.pos = (side.left == 3) ? new Vec2(0, 4) : new Vec2(8, 4);
    }
    
    public void MoveTo(int dir)
    {
        // Richtungs-Vektor anhand der ID (1-4) bestimmen
        Vec2 delta = getDirectionVector(dir);
        if (delta == null) return;

        Vec2 targetPos = new Vec2(pos.x + delta.x, pos.y + delta.y);

        // Validierung und Bewegung
        if (Spielleiter.CanMoveTo(targetPos, orientation)) {
            pos = targetPos;
        }
    }

    public void SetWall(Vec2 feld, Vec2 offsetDir, Vec2 wallDir) 
    {
        // 1. Validierung: Sind die Richtungen orthogonal (im 90-Grad-Winkel)?
        if ((offsetDir.x != 0 && wallDir.x != 0) || (offsetDir.y != 0 && wallDir.y != 0)) {
            System.out.println("Ungültige Wand-Konfiguration! Richtungen müssen orthogonal sein.");
            return;
        }

        // 2. Die 4 betroffenen Felder mathematisch über Vektoren berechnen
        Vec2 f1 = feld;
        Vec2 f2 = new Vec2(feld.x + offsetDir.x, feld.y + offsetDir.y);
        Vec2 f3 = new Vec2(feld.x + wallDir.x, feld.y + wallDir.y);
        Vec2 f4 = new Vec2(feld.x + offsetDir.x + wallDir.x, feld.y + offsetDir.y + wallDir.y);

        // 3. Grenzen prüfen, um Abstürze (IndexOutOfBounds) zu verhindern
        if (!isValid(f1) || !isValid(f2) || !isValid(f3) || !isValid(f4)) {
            System.out.println("Wand außerhalb des Spielfelds!");
            return;
        }

        // 4. Die Blockade sauber setzen (Nutzt die Gegenrichtung für das Nachbarfeld)
        blockWallsBetween(f1, f3);
        blockWallsBetween(f2, f4);
        
        ubrigeWande--; // Wand-Zähler verringern
    }

    // Hilfsmethode: Verknüpft zwei Felder und blockiert die gemeinsame Kante
    private void blockWallsBetween(Vec2 a, Vec2 b) {
        // Berechnet die relative Richtung von A zu B
        int dirFromAToB = getDirectionEnum(a, b); 
        int dirFromBToA = getDirectionEnum(b, a);

        Spielleiter.spielFeld[a.x][a.y].BlockSide(dirFromAToB);
        Spielleiter.spielFeld[b.x][b.y].BlockSide(dirFromBToA);
    }

    private Vec2 getDirectionVector(int dir) {
        return switch (dir) {
            case 1 -> new Vec2(0, -1); // Oben
            case 2 -> new Vec2(0, 1);  // Unten
            case 3 -> new Vec2(-1, 0); // Links
            case 4 -> new Vec2(1, 0);  // Rechts
            default -> null;
        };
    }

    private int getDirectionEnum(Vec2 from, Vec2 to) {
        if (to.y < from.y) return orientation.up;
        if (to.y > from.y) return orientation.down;
        if (to.x < from.x) return orientation.left;
        return orientation.right;
    }

    private boolean isValid(Vec2 p) {
        return p.x >= 0 && p.x < Spielleiter.spielFeld.length && 
               p.y >= 0 && p.y < Spielleiter.spielFeld[0].length;
    }
}
