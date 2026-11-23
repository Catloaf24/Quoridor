/*class Spielleiter
{
    public static FeldData[][] spielFeld = new FeldData[9][9];

    static NewSpieler spieler1;
    static NewSpieler spieler2;

    GUI gui;

    private NewSpieler currentPlayer = spieler1;

    public Spielleiter()
    {
        for (int Row = 0; Row < 9; ++Row)
            for (int Col = 0; Col < 9; ++Col)
                spielFeld[Row][Col] = new FeldData();

        spieler1 = new NewSpieler(new Direction(1, 2, 3, 4));
        spieler2 = new NewSpieler(new Direction(2, 1, 4, 3));

        gui = new GUI();
        gui.steuerungB.setAktiv(true);
        gui.steuerungR.setAktiv(true);
    }

    static boolean CanMoveTo(Vec2 pos, int dir)
    {
        return !spielFeld[pos.x][pos.y].IsBlocked(dir);
    }

    void SetWall(Vec2 feld, int offset, int dir)
    {
        if (offset > 1 || -1 > offset || offset == 0)
        {
            System.out.println("Offset not allowed");
            return;
        }

        if (dir == 1)
            spielFeld[feld.x + offset][feld.y].BlockSide(dir);
        else 
            spielFeld[feld.x][feld.y + offset].BlockSide(dir);
    }

    public void MakeMove(int dx, int dy) {
        int dir = currentPlayer.GetDirectionEnum(currentPlayer.pos, new Vec2(dx, dy));
        if (!CanMoveTo(currentPlayer.pos, dir)) return;
    }

    public boolean CheckWin(NewSpieler player) {
        if (player.pos.y == player.winRow) 
            return true;
        else 
            return false;
    }

    void Gameloop() {
        
    }
}
    */