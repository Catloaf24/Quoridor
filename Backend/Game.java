public class Game {

	//Philipp
	//erstellt 2 neue Spieler, einen SL und ein objekt für den derzeitigen Spieler
	static NewSpieler s1 = new NewSpieler(1);
	static NewSpieler s2 = new NewSpieler(2);

	static NewSpielleiter sl = new NewSpielleiter();

	static NewSpieler currentPlayer;

	//~50/50 split Philipp/Leon
	//Die tatsächliche hauptmethode
	public static void main(String[] args) throws InterruptedException {
		//weise den steuerungen spieler und den sl zu, damit sie auf Methoden zugreifen können 
		GUI gui = new GUI();
		gui.steuerungR.setSL(sl);
		gui.steuerungB.setSL(sl);
		gui.steuerungR.setSpieler(s1);
		gui.steuerungB.setSpieler(s2);
		gui.wandR.setSL(sl);
		gui.wandB.setSL(sl);
		gui.wandR.setSpieler(s1);
		gui.wandB.setSpieler(s2);

		//setzt den aktiven Spieler auf s1 und aktiviert/deaktiviert die passende Steuerungen
		currentPlayer = s1;
		gui.steuerungR.setAktiv(true);
		gui.steuerungB.setAktiv(false);
		gui.wandR.setAktiv(true);
		gui.wandB.setAktiv(false);

		//solange keiner der Spieler gewonnen hat...
		while(sl.checkWin(s1, s2) == false) {
			//falls der aktive Spieler fertig ist und nicht gewonnen hat, überspringe alles
			if (currentPlayer.isDone() == true && sl.checkWin(s1, s2) == false) {
				//falls der aktive Spieler nicht fertig ist, nicht gewonnen hat und s1 ist:
				if (currentPlayer == s1) {
					//s2.done zurücksetzen, steuerungen aktivieren/deaktivieren und am ende s2 zum aktiven Spieler machen
					s2.setDone(false);
					gui.steuerungR.setAktiv(true);
					gui.wandR.setAktiv(true);
					gui.steuerungB.setAktiv(false);
					gui.wandB.setAktiv(false);
					currentPlayer = s2;
					currentPlayer.setDone(false);
				}
				//andernfalls war s2 der aktive Spieler:
				else {
					//s1.done zurücksetzen, steuerungen aktivieren/deaktivieren und am ende s1 zum aktiven Spieler machen
					s1.setDone(false);
					gui.steuerungB.setAktiv(true);
					gui.wandB.setAktiv(true);
					gui.steuerungR.setAktiv(false);
					gui.wandR.setAktiv(false);
					currentPlayer = s1;
					currentPlayer.setDone(false);
				}
			}
			// Waruuuuuum muss man hier pausen, damit die GUI nicht freezed????
			Thread.sleep(100);
		}
	}
}
