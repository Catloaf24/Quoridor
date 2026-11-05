public class Spieler 
{
    int ubrigeWande;
    Vec2 pos;
    Direction orientation;

    public Spieler(Direction side) 
    {
        ubrigeWande = 10;
        orientation = side;

        pos = (side.left == 3) ? new Vec2(0, 4) : new Vec2(8, 4);
    }

    private Vec2 GetDirectionVector(int dir) 
    {
        return switch (dir) 
        {
            case 1 -> new Vec2(0, -1);
            case 2 -> new Vec2(0, 1); 
            case 3 -> new Vec2(-1, 0); 
            case 4 -> new Vec2(1, 0); 
            default -> null;
        };
    }

    private int GetDirectionEnum(Vec2 from, Vec2 to) 
    {
        if (to.y < from.y) return orientation.up;
        if (to.y > from.y) return orientation.down;
        if (to.x < from.x) return orientation.left;
        return orientation.right;
    }

    private boolean IsValidPosition(Vec2 p) 
    {
        return p.x >= 0 && p.x < Spielleiter.spielFeld.length && p.y >= 0 && 
               p.y < Spielleiter.spielFeld[0].length;
    }
    
    public void MoveTo(int dir)
    {
        Vec2 delta = GetDirectionVector(dir);
        if (delta == null) return;

        Vec2 targetPos = new Vec2(pos.x + delta.x, pos.y + delta.y);
        if (Spielleiter.CanMoveTo(targetPos, dir)) 
        {
            pos = targetPos;
        }
    }
/*
    /*
     * @param feld     // Position des Feldes auf dem man die Wand plazieren will
     * @param offset   // Richtung wo es plaziert wird (oben / unten)
     * @param wallSide // Richtung ins welche Feld es über geht (rechts / links)     
    /
    public void SetWall(Vec2 feld, int offset, int wallSide) 
    {
        Vec2 offsetDir = GetDirectionVector(offset), wallDir = GetDirectionVector(wallSide);

        if ((offsetDir.x != 0 && wallDir.x != 0) || (offsetDir.y != 0 && wallDir.y != 0))
        {
            System.out.println("Ungültige Wand-Konfiguration! Richtungen müssen orthogonal sein.");
            return;
        }

        Vec2 f1 = feld;
        Vec2 f2 = new Vec2(feld.x + offsetDir.x, feld.y + offsetDir.y);
        Vec2 f3 = new Vec2(feld.x + wallDir.x, feld.y + wallDir.y);
        Vec2 f4 = new Vec2(feld.x + offsetDir.x + wallDir.x, feld.y + offsetDir.y + wallDir.y);

        if (!IsValidPosition(f1) || !IsValidPosition(f2) || !IsValidPosition(f3) || !IsValidPosition(f4)) 
        {
            System.out.println("Wand außerhalb des Spielfelds!");
            return;
        }

        BlockWallsBetween(f1, f3);
        BlockWallsBetween(f2, f4);
        
        ubrigeWande--;
    }
*/

    private void BlockWallsBetween(Vec2 a, Vec2 b) 
    {
        int dirFromAToB = GetDirectionEnum(a, b); 
        int dirFromBToA = GetDirectionEnum(b, a);

        Spielleiter.spielFeld[a.x][a.y].BlockSide(dirFromAToB);
        Spielleiter.spielFeld[b.x][b.y].BlockSide(dirFromBToA);
    }
}

Vec2 validFelder(Vec2 f1, Vec2 f2) {
    //oben benachbart
    if (f1.x == f2.y - 1) {
        return new Vec2(orientation.up, orientation.down);
    }
    //unten benachbart
    if (f1.x == f2.y + 1) {
        return new Vec2(orientation.down, orientation.down);
    }
    //links benachbart
    if (f1.y == f2.y - 1) {
        return new Vec2(orientation.left, orientation.right);
    }
    //rechts benachbart
    if (f1.y == f2.y + 1) { 
        return new Vec2(orientation.right, orientation.left);
    }

    return new Vec2(0, 0);
}

void setWall(Vec2 f1, Vec2 f2) {
    int valid = validFelder(f1, f2);

    if (valid.x == 0) return;

    Spielleiter.spielFeld[f1.x][f1.y].BlockSide(valid.x);
    Spielleiter.spielFeld[f2.x][f2.y].BlockSide(valid.y);
}