public class NewSpielleiter {
	NewFeldData[][] feld = new NewFeldData[9][9];

	//NewSpieler s1;
	//NewSpieler s2;

	public NewSpielleiter() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				feld[i][j] = new NewFeldData();
			}
		}
	}

	int getDirection(Vec2 cPos, Vec2 nPos) {
		if (cPos.x == nPos.x && cPos.y == nPos.y + 1) return 1; // up
		if (cPos.x == nPos.x && cPos.y == nPos.y - 1) return 2; // down
		if (cPos.x == nPos.x + 1 && cPos.y == nPos.y) return 3; // left
		if (cPos.x == nPos.x - 1 && cPos.y == nPos.y) return 4; // right

		return -1;
	}

	public boolean validateMove(Vec2 cPos, Vec2 nPos) {
		int dir = getDirection(cPos, nPos);

		if (feld[cPos.x][cPos.y].blocked(dir - 1) == 1) 
			return false;
		else
			System.out.println("Valid move!");
			return true;
	}
	
	public void movePlayer(NewSpieler player, Vec2 nPos) {
		if (validateMove(player.pos, nPos) == true) {
			player.pos = nPos;
		}
		else {
			System.out.println("Invalid move by player " + player.side);
		}
	}

	public void placeWall(NewSpieler player, Vec2 f1, Vec2 f2, Vec2 f3, Vec2 f4) {
		if (getDirection(f1, f2) == -1 || getDirection(f3, f4) == -1) {
			System.out.println("Invalid wall placement! (Wrong dir)");
			return;
		}

		if (feld[f1.x][f1.y].blocked(getDirection(f1, f2) - 1) == 1 || feld[f3.x][f3.y].blocked(getDirection(f3, f4) - 1) == 1){
			System.out.println("Invalid wall placement! (wall already exists)");
			return;
		}

		feld[f1.x][f1.y].block(getDirection(f1, f2) - 1);
		feld[f2.x][f2.y].block(getDirection(f2, f1) - 1);
		feld[f3.x][f3.y].block(getDirection(f3, f4) - 1);
		feld[f4.x][f4.y].block(getDirection(f4, f3) - 1);

		player.amtWalls--;
	}

	public void endTurn(NewSpieler player) {
		player.done = true;
	}

	public boolean checkWin(NewSpieler s1, NewSpieler s2) {
		if (s1.pos.y == 8) {
			System.out.println("Player 1 wins!");
			return true;
		}
		else if (s2.pos.y == 0) {
			System.out.println("Player 2 wins!");
			return true;
		}
		else {
			return false;
		}
	}
}
