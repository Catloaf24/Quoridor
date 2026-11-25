//Philipp
public class NewSpieler {
	int side = 0;
	int amtWalls = 20;
	private boolean done = false;

	Vec2 pos;

	//erzeuge einen neuen Spieler mit einer seite und setzt die startposition
	public NewSpieler(int side) {
		this.side = side;
		this.pos = (side == 1) ? new Vec2(4, 0) : new Vec2(4, 8);
	}

	//Philipp
	//setter, setzt done = true
	public void setDone(boolean d) {
		this.done = d;
	}

	//Leon
	//getter, gibt done zurück
	public boolean isDone() {
		return done;
	}
}