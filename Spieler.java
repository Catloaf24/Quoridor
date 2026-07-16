

public class Spieler 
{
    int ubrigeWande;
    Vec2 pos;
    Direction orientation;

    public Spieler(Direction side) 
    {
        ubrigeWande = 10;
        orientation = side;

        if (orientation.left == 3) {
            pos = new Vec2(0, 4);
        }
        else if (orientation.left == 4) {
            pos = new Vec2(8, 4);
        }
        else {
            System.out.println("Falsche Seite angegeben!");
        }
    }
    
    public void MoveTo(int dir)
    {
        if (dir == 1 && Spielleiter.CanMoveTo(Vec2(pos.x, pos.y - 1), orientation))
        {
            pos.y -= 1;
        }
        else if (dir == 2 && Spielleiter.CanMoveTo(Vec2(pos.x, pos.y - 1), orientation))
        {
            pos.y += 1;
        }
        else if (dir == 3 && Spielleiter.CanMoveTo(Vec2(pos.x - 1, pos.y), orientation))
        {
            pos.x -= 1;
        }
        else if (dir == 4 && Spielleiter.CanMoveTo(Vec2(pos.x + 1, pos.y), orientation))
        {
            pos.x += 1;
        }
    }

    public void SetWall(Vec2 feld, int offset, int dir) 
    {
        if ((offset == orientation.left || offset == orientation.right) && (dir == orientation.left || dir == Drection.right)) {
            System.out.println("Falsche Seite angegeben. Felder können nicht blockiert werden");
            return;
        }
        else if ((offset == orientation.up || offset == orientation.down) && (dir == orientation.up || dir == orientation.down)) {
            System.out.println("Falsche Seite angegeben. Felder können nicht blockiert werden");
            return;
        }

        if (offset == orientation.up && dir == orientation.left) {
            Spielleiter.spielFeld[feld.x][feld.y].BlockSide(orientation.left);
            Spielleiter.spielFeld[feld.x][feld.y - 1].BlockSide(orientation.left);
            Spielleiter.spielFeld[feld.x - 1][feld.y].BlockSide(orientation.right);
            Spielleiter.spielFeld[feld.x - 1][feld.y - 1].BlockSide(orientation.right);
        }
        else if (offset == orientation.up && dir == orientation.right) {
            Spielleiter.spielFeld[feld.x][feld.y].BlockSide(orientation.right);
            Spielleiter.spielFeld[feld.x][feld.y - 1].BlockSide(orientation.right);
            Spielleiter.spielFeld[feld.x + 1][feld.y].BlockSide(orientation.left);
            Spielleiter.spielFeld[feld.x + 1][feld.y - 1].BlockSide(orientationleft);
        }
        else if (offset == orientation.down && dir == orientation.left) {
            Spielleiter.spielFeld[feld.x][feld.y].BlockSide(orientation.left);
            Spielleiter.spielFeld[feld.x][feld.y + 1].BlockSide(orientation.left);
            Spielleiter.spielFeld[feld.x - 1][feld.y].BlockSide(orientation.right);
            Spielleiter.spielFeld[feld.x - 1][feld.y + 1].BlockSide(orientation.right);
        }
        else if (offset == orientation.down && dir == orientation.right) {
            Spielleiter.spielFeld[feld.x][feld.y].BlockSide(orientation.right);
            Spielleiter.spielFeld[feld.x][feld.y + 1].BlockSide(orientation.right);
            Spielleiter.spielFeld[feld.x + 1][feld.y].BlockSide(orientation.left);
            Spielleiter.spielFeld[feld.x + 1][feld.y + 1].BlockSide(orientation.left);
        }
        else if (offset == orientation.left && dir == orientation.up) {
            Spielleiter.spielFeld[feld.x][feld.y].BlockSide(orientation.up);
            Spielleiter.spielFeld[feld.x - 1][feld.y].BlockSide(orientation.up);
            Spielleiter.spielFeld[feld.x][feld.y - 1].BlockSide(orientation.down);
            Spielleiter.spielFeld[feld.x - 1][feld.y - 1].BlockSide(orientation.down);
        }
        else if (offset == orientation.left && dir == orientation.down) {
            Spielleiter.spielFeld[feld.x][feld.y].BlockSide(orientation.down);
            Spielleiter.spielFeld[feld.x - 1][feld.y].BlockSide(orientation.down);
            Spielleiter.spielFeld[feld.x][feld.y].BlockSide(orientation.up);
            Spielleiter.spielFeld[feld.x - 1][feld.y].BlockSide(orientation.up);
        }
        else if (offset == orientation.right && dir == orientation.up) {
            Spielleiter.spielFeld[feld.x][feld.y].BlockSide(orientation.up);
            Spielleiter.spielFeld[feld.x + 1][feld.y].BlockSide(orientation.up);
        }
        else if (offset == orientation.right && dir == orientation.down) {
            Spielleiter.spielFeld[feld.x][feld.y].BlockSide(orientation.down);
            Spielleiter.spielFeld[feld.x + 1][feld.y].BlockSide(orientation.down);
        }
    }

}
