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
	    currentGameboard.printBoard();
	    Scanner reader = new Scanner(System.in);
	    System.out.println("What move would you like to make?");
	    String input = reader.next();

	    // Debugging isLegalMove()
	    ArrayList<Integer> positions = currentGameboard.convertIntoNumbers(input);
	    int x = positions.get(0);
	    int y = positions.get(1);

	    if(currentGameboard.isLegalMove(currentPlayer, x, y)){
		System.out.println("This is a legal move");
	    } else {
		System.out.println("Not a legal move");
	    }

	    // end debugging string

	    
	    currentGameboard.placePiece(currentPlayer, input);
	    player saveState = otherPlayer;
	    otherPlayer = currentPlayer;
	    currentPlayer = saveState;
	}
    }

}
