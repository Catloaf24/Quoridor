public class Game {

	static NewSpieler s1 = new NewSpieler(1);
	static NewSpieler s2 = new NewSpieler(2);

	static NewSpielleiter sl = new NewSpielleiter();

	static NewSpieler currentPlayer;

	public static void main(String[] args) throws InterruptedException {
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
			if (currentPlayer.isDone() == true && sl.checkWin(s1, s2) == false) {
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
