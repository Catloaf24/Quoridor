public class NewFeldData {
	int blocked[] = new int[4];
	
	public int blocked(int dir) {
		return blocked[dir];
	}

	public void block(int dir) {
		blocked[dir] = 1;
	}
}
