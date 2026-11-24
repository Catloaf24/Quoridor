//Philipp
public class NewSpieler {
	int side = 0;
	int amtWalls = 20;
	private boolean done = false;

	Vec2 pos;

	public NewSpieler(int side) {
		this.side = side;
		this.pos = (side == 1) ? new Vec2(4, 0) : new Vec2(4, 8);
	}

	public void setDone(boolean d) {
		this.done = d;
	}

	public boolean isDone() {
		return done;
	}
}