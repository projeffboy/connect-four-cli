
package assignment4Game;

import java.io.*;

public class Game {
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;
		
		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;
		
		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}
	
	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
		// ADD YOUR CODE HERE
		int column = -1;
		
		System.out.println("Current board:");
		c.print();
		
		while (column == -1 || column >= 7 || c.available[column] >= 6) {
			System.out.print("Your turn: ");
			try {
				column = Integer.parseInt(keyboard.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return column; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public static int firstMovePlayer1 (){
		return 3;
	}
	
	public static int movePlayer1 (int columnPlayed2, Configuration c){
		// ADD YOUR CODE HERE
		int column = c.canWinNextRound(1);
		column = column == -1 ? c.canWinTwoTurns(1) : column;
		
		if (column == -1) {
			int sign = -1;
			int i = 1;
			
			column = columnPlayed2;
			while(c.available[column] >= 6) {
				if (column == 6) {
					column--;
				} else if (column == 0) {
					column++;
				} else {
					column += sign * i;
					sign *= -1;
					i++;
				}
			}
		}
		
		return column; // DON'T FORGET TO CHANGE THE RETURN
	}
	
}
