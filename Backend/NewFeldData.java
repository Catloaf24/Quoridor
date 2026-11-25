//Leon
public class NewFeldData {
	int blocked[] = new int[4];
	
	//erzeuge ein neues "feld" und entblocke alle richtungen
	public NewFeldData() {
		for (int i = 0; i < 4; i++) {
			blocked[i] = 0;
		}
	}

	//Leon
	//überprüfe, ob eine bestimmte richtung blockiert ist (0 = oben, 1 = unten, 2 = links, 3 = rechts)
	public int blocked(int dir) {
		return blocked[dir];
	}

	//Philipp
	//blockiere eine bestimmte richtung 
	public void block(int dir) {
		blocked[dir] = 1;
	}
}