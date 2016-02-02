// Emily Zhou and Tammy Chen
import java.util.ArrayList;
import java.util.Scanner;

public class othello {

    public static void main(String[] args) {
    	System.out.println("Welcome to Othello!");
        gameboard currentGameboard = new gameboard();
        currentGameboard.populateBoard();
		player p1 = new player(" X");
		player p2 = new player(" O");
		p1.addTally(2);
		p2.addTally(2);
		player currentPlayer = p1;
		player otherPlayer = p2;
		boolean gameComplete = false;
		while (!gameComplete) {
			if (doesPlayerHaveMoves(currentPlayer, currentGameboard)) {
			    currentGameboard.printBoard();
			    Scanner reader = new Scanner(System.in);
			    System.out.println("What move would you like to make?");
			    String input = reader.next();	    
			    boolean makeMove = currentGameboard.placePiece(currentPlayer, otherPlayer, input);
			    while (!makeMove) {
			    	System.out.println("That move is an illegal move. Try another one!");
			    	String tryMoveAgain = reader.next();
			    	makeMove = currentGameboard.placePiece(currentPlayer, otherPlayer, tryMoveAgain);
			    }
			    player saveState = otherPlayer;
			    otherPlayer = currentPlayer;
			    currentPlayer = saveState;
			} else if (doesPlayerHaveMoves(otherPlayer, currentGameboard)) {
				player saveState = otherPlayer;
			    otherPlayer = currentPlayer;
			    currentPlayer = saveState;
			} else {
				gameComplete = true;
			}
		}
	}

	public static boolean doesPlayerHaveMoves(player Player, gameboard currentGameboard) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (currentGameboard.isLegalMove(Player, row, col)) {
					return true;
				}
			}
		}
		return false;
	}

}
