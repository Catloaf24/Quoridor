class Spielleiter
{
    static FeldData[][] spielFeld;

    static Spieler spieler1;
    static Spieler spieler2;

    private Spieler currentPlayer = spieler1;

    public Spielleiter()
    {
        for (int Row = 0; Row < 9; ++Row)
            for (int Col = 0; Col < 9; ++Col)
                spielFeld[Row][Col] = new FeldData();

        spieler1 = new Spieler(new Direction(1, 2, 3, 4));
        spieler2 = new Spieler(new Direction(2, 1, 4, 3));
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

    void NächsterZug() {
        System.out.println("Spieler " + currentPlayer + " ist am Zug");
        System.out.println("Willst du dich bewgegen(1) oder eine Wand platzieren(2)?");
        int choice = Integer.parseInt(System.console().readLine());
        //Case: moving
        int choiceDir = 0;
        //Case: placing wall
        Vec2 start = new Vec2(0, 0);
        int offset = 0;
        int side = 0;

        if (choice == 1) {
            System.out.println("Welche Richtung? (O: 1, U: 2, L: 3, R: 4)");
            choiceDir = Integer.parseInt(System.console().readLine());
        }
        if (choice == 2) {
            System.out.println("Gib das X des ersten Feldes an:");
            start.x = Integer.parseInt(System.console().readLine());
            System.out.println("Gib das Y des ersten Feldes an:");
            start.y = Integer.parseInt(System.console().readLine());
            System.out.println("Welche Richtung? (O: 1, U: 2, L: 3, R: 4)");
            offset = Integer.parseInt(System.console().readLine());
            System.out.println("Welche Seite? (O: 1, U: 2, L: 3, R: 4)");
            side = Integer.parseInt(System.console().readLine());

            SetWall(start, offset, side);
        }

        if (choice == 1 && CanMoveTo(currentPlayer.pos, choiceDir))  {
            switch (choiceDir) {
                case 1:
                    currentPlayer.pos.y--;
                    break;
                case 2:
                    currentPlayer.pos.y++;
                    break;
                case 3:
                    currentPlayer.pos.x--;
                    break;
                case 4:
                    currentPlayer.pos.x++;
                    break;
            }
        }

        currentPlayer = (currentPlayer == spieler1) ? spieler2 : spieler1;
    }

    void Gameloop() {

    }

}