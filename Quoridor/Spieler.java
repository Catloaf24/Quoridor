public class Spieler 
{
    int ubrigeWande;
    Vec2 pos;
    Direction orientation;
    int winRow;

    public Spieler(Direction side) 
    {
        ubrigeWande = 10;
        orientation = side;

        pos = (side.up == 2) ? new Vec2(4, 0) : new Vec2(4, 8);
        winRow = (side.up == 2) ? 0 : 8;
    }

    public Vec2 GetDirectionVector(int dir) 
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

    public int GetDirectionEnum(Vec2 from, Vec2 to) 
    {
        if (to.y < from.y) return orientation.up;
        if (to.y > from.y) return orientation.down;
        if (to.x < from.x) return orientation.left;
        return orientation.right;
    }

    /*
    private boolean IsValidPosition(Vec2 p) 
    {
        return p.x >= 0 && p.x < NewSpielleiter.spielFeld.length && p.y >= 0 && 
               p.y < NewSpielleiter.spielFeld[0].length;
    }
               */
    
    /*
    public void MoveTo(int dir)
    {
        Vec2 delta = GetDirectionVector(dir);
        if (delta == null) return;

        Vec2 targetPos = new Vec2(pos.x + delta.x, pos.y + delta.y);
        if (NewSpielleiter.CanMoveTo(targetPos, dir)) 
        {
            pos = targetPos;
        }
    }


    Vec2 validFelder(Vec2 f1, Vec2 f2) {
        //oben benachbart
        if (f1.x == f2.y - 1) {
            return new Vec2(orientation.up, orientation.down);
        }
        //unten benachbart
        if (f1.x == f2.y + 1) {
            return new Vec2(orientation.down, orientation.up);
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
        Vec2 valid = validFelder(f1, f2);

        if (valid.x == 0) return;

        if (!NewSpielleiter.spielFeld[f1.x][f1.y].IsBlocked(valid.x)) {NewSpielleiter.spielFeld[f1.x][f1.y].BlockSide(valid.x);}
        if (!NewSpielleiter.spielFeld[f2.x][f2.y].IsBlocked(valid.y)) {NewSpielleiter.spielFeld[f2.x][f2.y].BlockSide(valid.y);}
    }
        */
}