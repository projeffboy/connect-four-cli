package assignment4Game;

public class Configuration {
	
	public int[][] board;
	public int[] available;
	boolean spaceLeft;
	
	public Configuration(){
		board = new int[7][6];
		available = new int[7];
		spaceLeft = true;
	}
	
	public void print(){
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++){
			System.out.print("|");
			for (int j = 0; j < 7; j++){
				if (board[j][5-i] == 0){
					System.out.print("   |");
				}
				else{
					System.out.print(" "+ board[j][5-i]+" |");
				}
			}
			System.out.println();
		}
	}
	
	public void addDisk (int index, int player){
		// ADD YOUR CODE HERE
		if (spaceLeft && available[index] < 6) {
			board[index][available[index]] = player;
			available[index]++;
		} else {
			for (int i = 0; i <= 6; i++) {
				if (available[index] >= 6) {
					break;
				}
				if (i == 6) {
					spaceLeft = false;
				}
			}
		}
	}
	
	public void removeDisk (int index, int player){
		if (available[index] >= 1) {
			board[index][available[index] - 1] = 0;
			available[index]--;
			spaceLeft = true;
		}
	}
	
	public boolean isWinning (int lastColumnPlayed, int player){
		// ADD YOUR CODE HERE
		int i = lastColumnPlayed;
		int j = available[i] - 1;
		
		int vertical = 0;
		int horizontal = 0;
		int posDiagonal = 0;
		int negDiagonal = 0;
		
		// If board piece is from player
		// If piece is at the top or there is no piece above it
		//if (board[i][j] == player && (available[i] == 6 || board[i][j + 1] == 0)) {
			// Down
			if (j >= 4) {
				for (int k = 1; k <= 3; k++) {
					if (board[i][j - k] == player) {
						vertical++;
					} else {
						break;
					}
				}
			}
			
			// Left
			for(int k = 1; k <= 3; k++) {
				if (i - k >= 0 && board[i - k][j] == player) {
					horizontal++;
				} else {
					break;
				}
			}
			
			// Right
			for(int k = 1; k <= 3; k++) {
				if (i + k <= 6 && board[i + k][j] == player) {
					horizontal++;
				} else {
					break;
				}
			}
			
			// Bottom-Left
			for(int k = 1; k <= 3; k++) {
				if (i - k >= 0 && j - k >= 0 && board[i - k][j - k] == player) {
					posDiagonal++;
				} else {
					break;
				}
			}
			
			// Top-Right
			for(int k = 1; k <= 3; k++) {
				if (i + k <= 6 && j + k <= 5 && board[i + k][j + k] == player) {
					posDiagonal++;
				} else {
					break;
				}
			}
			
			// Top-Left
			for(int k = 1; k <= 3; k++) {
				if (i - k >= 0 && j + k <= 5 && board[i - k][j + k] == player) {
					negDiagonal++;
				} else {
					break;
				}
			}
			
			// Bottom-Right
			for(int k = 1; k <= 3; k++) {
				if (i + k <= 6 && j - k >= 0 && board[i + k][j - k] == player) {
					negDiagonal++;
				} else {
					break;
				}
			}
		//}
		
		if (horizontal >= 3 || vertical >= 3 || posDiagonal >= 3 || negDiagonal >= 3) {
			return true;
		} else {
			return false;
		}
	}
	
	public int canWinNextRound (int player){
		// ADD YOUR CODE HERE
		for (int i = 0; i <= 6; i++) {
			if (available[i] < 6) {
				addDisk(i, player);
				if (isWinning(i, player)) {
					removeDisk(i, player);
					return i;
				}
				removeDisk(i, player);
			}
		}
		
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public int canWinTwoTurns (int player){
		// ADD YOUR CODE HERE
		for (int i = 0; i <= 6; i++) {
			if (available[i] < 6) {
				addDisk(i, player);
				for (int j = 0; j <= 6; j++) {
					if (available[j] < 6) {
						addDisk(j, player % 2 + 1);
						if (canWinNextRound(player) == -1) {
							removeDisk(j, player % 2 + 1);
							break;
						}
						removeDisk(j, player % 2 + 1);
					}
					if (j == 6) {
						removeDisk(i, player);
						return i;
					}
				}
				removeDisk(i, player);
			}
		}
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}
	
}
