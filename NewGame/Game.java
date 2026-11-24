//import GUI.GUI;

public class Game {

	static NewSpieler s1 = new NewSpieler(1);
	static NewSpieler s2 = new NewSpieler(2);

	static NewSpielleiter sl = new NewSpielleiter();

	static NewSpieler currentPlayer;

	public static void main(String[] args) throws InterruptedException {
		/*
		System.out.println(s1.pos.x + " " + s1.pos.y);
		System.out.println(s2.pos.x + " " + s2.pos.y);

		sl.movePlayer(s1, new Vec2(4, 1));
		System.out.println(s1.pos.x + " " + s1.pos.y);

		sl.movePlayer(s2, new Vec2(4, 7));
		System.out.println(s2.pos.x + " " + s2.pos.y);

		sl.placeWall(s1, new Vec2(4, 4), new Vec2(5, 4), new Vec2(4, 5), new Vec2(5, 5));
		System.out.println(sl.feld[4][4].blocked(3));
		System.out.println(sl.feld[5][4].blocked(2));
		System.out.println(sl.feld[4][5].blocked(3));
		System.out.println(sl.feld[5][5].blocked(2));
		System.out.println(s1.amtWalls);

		sl.placeWall(s2, new Vec2(6, 4), new Vec2(7, 4), new Vec2(6, 5), new Vec2(7, 5));
		System.out.println(sl.feld[6][4].blocked(3));
		System.out.println(sl.feld[7][4].blocked(2));
		System.out.println(sl.feld[6][5].blocked(3));
		System.out.println(sl.feld[7][5].blocked(2));
		System.out.println(s2.amtWalls);
		
		*/

		GUI gui = new GUI();
		gui.steuerungR.setSL(sl);
		gui.steuerungB.setSL(sl);
		gui.steuerungR.setSpieler(s1);
		gui.steuerungB.setSpieler(s2);
		gui.wandR.setSL(sl);
		gui.wandB.setSL(sl);
		gui.wandR.setSpieler(s1);
		gui.wandB.setSpieler(s2);

		currentPlayer = s1;
		gui.steuerungR.setAktiv(true);
		gui.steuerungB.setAktiv(false);
		gui.wandR.setAktiv(true);
		gui.wandB.setAktiv(false);

		while(sl.checkWin(s1, s2) == false) {
			if (currentPlayer.isDone() == true) {
				if (currentPlayer == s1) {
					s2.setDone(false);
					gui.steuerungR.setAktiv(true);
					gui.wandR.setAktiv(true);
					gui.steuerungB.setAktiv(false);
					gui.wandB.setAktiv(false);
					currentPlayer = s2;
					currentPlayer.setDone(false);
				}
				else {
					s1.setDone(false);
					gui.steuerungB.setAktiv(true);
					gui.wandB.setAktiv(true);
					gui.steuerungR.setAktiv(false);
					gui.wandR.setAktiv(false);
					currentPlayer = s1;
					currentPlayer.setDone(false);
				}
			}
			//System.out.println("Current player: " + currentPlayer.side);
			//System.out.println();
			Thread.sleep(100);
		}
	}
}
