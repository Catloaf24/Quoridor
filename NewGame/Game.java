import GUI.GUI;

public class Game {

	static NewSpieler s1 = new NewSpieler(1);
	static NewSpieler s2 = new NewSpieler(2);

	static NewSpielleiter sl = new NewSpielleiter();

	NewSpieler currentPlayer = s1;

	public static void main(String[] args) {
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
		
	}
}
