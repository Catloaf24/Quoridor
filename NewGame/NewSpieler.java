public class NewSpieler {
	int side = 0;
	int amtWalls = 10;
	private boolean done = false;

	Vec2 pos;

	public NewSpieler(int side) {
		this.side = side;
		this.pos = (side == 1) ? new Vec2(4, 0) : new Vec2(4, 8);
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isDone() {
		return done;
	}
}