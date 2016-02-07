// Emily Zhou and Tammy Chen
// EDA132 VT2016

import java.util.ArrayList;
import java.util.Scanner;

public class othello {

    public static void main(String[] args) {
	// Game set-up variables 
	Scanner reader = new Scanner(System.in);	
        gameboard currentGameboard = new gameboard(new ArrayList<ArrayList<String>>());
        currentGameboard.populateBoard();
        gameboard copy = new gameboard(currentGameboard);
	player human = new player(" X");
	player computer = new player(" O");
	player currentPlayer = human;
	player otherPlayer = computer;
	human.addTally(2);
	computer.addTally(2);
	int thinkTime = 1000;
	boolean gameComplete = false;

	// Opening lines
	System.out.println("Welcome to Othello!");
	System.out.println("How much time should the computer think? (in milliseconds): ");
        thinkTime = reader.nextInt();

	while (!gameComplete) {
	    if (doesPlayerHaveMoves(currentPlayer, currentGameboard)) {
		if (currentPlayer.getName() == " X") { // X, black, the human, goes first
		    currentGameboard.printBoard();
		    System.out.println(currentPlayer.getName() + ": " + currentPlayer.getTally() +
				       "," + otherPlayer.getName() + ": " + otherPlayer.getTally());
						   
		    System.out.println("It's your turn! What move would you like to make?");
		    String input = reader.next();	    
		    boolean makeMove = currentGameboard.placePiece(currentPlayer, otherPlayer, input);
		    while (!makeMove) {
			System.out.println("That move is an illegal move. Try another one!");
			String tryMoveAgain = reader.next();
			makeMove = currentGameboard.placePiece(currentPlayer, otherPlayer, tryMoveAgain);
		    }
					
		} else { // O, white, the machine, goes next
		    player copyComputer = new player(currentPlayer);
		    player copyHuman = new player(otherPlayer);
		    gameboard copyGameBoard = new gameboard(currentGameboard);

		    // Timed iterative deepening
		    // Referenced http://www.tutorialspoint.com/java/lang/system_currenttimemillis.htm
		    // to understand currentTimeMillis() method

		    long finishTime = System.currentTimeMillis() + thinkTime;
		    int iteration = 1; // initial depth
		    ArrayList<Integer> numbMove = new ArrayList<Integer>();

		    System.out.println(System.currentTimeMillis());
		    while(System.currentTimeMillis() < finishTime){
			numbMove = alphaBeta(copyGameBoard, iteration, -100, 100, true, copyComputer, copyHuman);
			iteration++;
			System.out.println("depth of: "+iteration);
		    }
		    System.out.println(System.currentTimeMillis());
		    
		    String newMove = convertIntoString(numbMove.get(0), numbMove.get(1));
		    boolean makeMove = currentGameboard.placePiece(currentPlayer, otherPlayer, newMove);
		    System.out.println("Your opponent has placed a piece onto " + newMove);
		}

		// Swapping control between "currentPlayer" and "otherPlayer" for "X" and "O"
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

	} if (human.tally > computer.tally) {
	    currentGameboard.printBoard();
	    System.out.println(human.name + " is the winner with a score of " + human.tally + "-" + computer.tally);
	} else if (computer.tally > human.tally) {
	    currentGameboard.printBoard();
	    System.out.println(computer.name + " is the winner with a score of " + computer.tally + "-" + human.tally);			
	} else {
	    currentGameboard.printBoard();
	    System.out.println("The game is a draw, with a score of " + human.tally + "-" + computer.tally);
	}

    }

    public static ArrayList<Integer> alphaBeta(gameboard updatedBoard, int depth, int alpha, int beta, boolean isMaxPlayer, player currentPlayer, player otherPlayer) {
    	//base case: if depth is 0 or neither player has a move to make
    	if (depth <= 0 || !doesPlayerHaveMoves(currentPlayer, updatedBoard) && !doesPlayerHaveMoves(otherPlayer, updatedBoard)) {
	    if (isMaxPlayer) {
		ArrayList<Integer> movesAndTally = new ArrayList<Integer>();
		movesAndTally.add(-1000);
		movesAndTally.add(-1000);
		movesAndTally.add(currentPlayer.getTally() - otherPlayer.getTally());
		return movesAndTally;
	    } else {
		ArrayList<Integer> movesAndTally = new ArrayList<Integer>();
		movesAndTally.add(-1000);
		movesAndTally.add(-1000);
		movesAndTally.add(otherPlayer.getTally() - currentPlayer.getTally());
		return movesAndTally;
	    }
    	}
    	//checking if current player is maximizing player
    	if (isMaxPlayer) {
	    int value = -100;
	    //checks if the current player doesn't have a move, but the opposing player does
	    if (!doesPlayerHaveMoves(currentPlayer, updatedBoard) && doesPlayerHaveMoves(otherPlayer, updatedBoard)) {
		ArrayList<Integer> bestMoveSoFar = new ArrayList<Integer>();
		bestMoveSoFar.add(-1000);
		bestMoveSoFar.add(-1000);
		bestMoveSoFar.add(0);
		ArrayList<Integer> possibleMove = alphaBeta(updatedBoard, depth, alpha, beta, !isMaxPlayer, otherPlayer, currentPlayer);
		value = Math.max(value, possibleMove.get(2));
		alpha = Math.max(alpha, value);
		if (beta <= alpha) {
		    return bestMoveSoFar;
		}
		possibleMove.set(2, value);
		bestMoveSoFar = possibleMove;
		//	System.out.println(bestMoveSoFar);
		return bestMoveSoFar;
	    } else {
		//if both players have a move
		ArrayList<ArrayList<Integer>> allPossibleMoves = getAllMoves(currentPlayer, updatedBoard);
		ArrayList<Integer> bestMoveSoFar = new ArrayList<Integer>();
		bestMoveSoFar.add(0);
		bestMoveSoFar.add(0);
		bestMoveSoFar.add(0);
		for (int index = 0; index < allPossibleMoves.size(); index++) {
		    gameboard copyBoard = new gameboard(updatedBoard);
		    player copyCurrent = new player(currentPlayer);
		    player copyOther = new player(otherPlayer);
		    ArrayList<Integer> currentMove = allPossibleMoves.get(index);
		    int row = currentMove.get(0);
		    int col = currentMove.get(1);
		    copyBoard.placePiece(copyCurrent, copyOther, convertIntoString(row, col));
		    ArrayList<Integer> result = alphaBeta(copyBoard, depth - 1, alpha, beta, !isMaxPlayer, copyOther, copyCurrent);
		    if (result.get(0) == -1000) {
			result.set(0, row);
			result.set(2, col);
		    }
		    if (result.get(2) > value) {
			bestMoveSoFar.set(0, row);
			bestMoveSoFar.set(1, col);
			bestMoveSoFar.set(2, result.get(2));
		    }
		    alpha = Math.max(alpha, value);
		    if (beta <= alpha) {
			break;
		    }
		}
		//	System.out.println(bestMoveSoFar);
		return bestMoveSoFar;
	    }

    	} else {
	    int value = 100;
	    //checks if the current player doesn't have a move, but the opposing player does
	    if (!doesPlayerHaveMoves(currentPlayer, updatedBoard) && doesPlayerHaveMoves(otherPlayer, updatedBoard)) {
		ArrayList<Integer> bestMoveSoFar = new ArrayList<Integer>();
		bestMoveSoFar.add(0);
		bestMoveSoFar.add(0);
		bestMoveSoFar.add(0);
		ArrayList<Integer> possibleMove = alphaBeta(updatedBoard, depth, alpha, beta, !isMaxPlayer, otherPlayer, currentPlayer);
		value = Math.min(value, possibleMove.get(2));
		beta = Math.min(beta, value);
		if (beta <= alpha) {
		    return bestMoveSoFar;
		}
		possibleMove.set(2, value);
		bestMoveSoFar = possibleMove;
		//	System.out.println(bestMoveSoFar);
		return bestMoveSoFar;
	    } else {
		//if both players have a move
		ArrayList<ArrayList<Integer>> allPossibleMoves = getAllMoves(currentPlayer, updatedBoard);
		ArrayList<Integer> bestMoveSoFar = new ArrayList<Integer>();
		bestMoveSoFar.add(0);
		bestMoveSoFar.add(0);
		bestMoveSoFar.add(0);
		for (int index = 0; index < allPossibleMoves.size(); index++) {
		    gameboard copyBoard = new gameboard(updatedBoard);
		    player copyCurrent = new player(currentPlayer);
		    player copyOther = new player(otherPlayer);
		    ArrayList<Integer> currentMove = allPossibleMoves.get(index);
		    int row = currentMove.get(0);
		    int col = currentMove.get(1);
		    copyBoard.placePiece(copyCurrent, copyOther, convertIntoString(row, col));
		    ArrayList<Integer> result = alphaBeta(copyBoard, depth - 1, alpha, beta, !isMaxPlayer, otherPlayer, currentPlayer);
		    if (result.get(0) == -1000) {
			result.set(0, row);
			result.set(1, col);
		    }
		    if (result.get(2) < value) {
			bestMoveSoFar.set(0, row);
			bestMoveSoFar.set(1, col);
			bestMoveSoFar.set(2, result.get(2));
		    }
		    beta = Math.min(beta, value);
		    if (beta <= alpha) {
			break;
		    }
		}
		//	System.out.println(bestMoveSoFar);
		return bestMoveSoFar;
	    }
    	}
    }

    //returns a 2D ArrayList of integers that describe all possible moves for a given player and a given board state
    public static ArrayList<ArrayList<Integer>> getAllMoves(player Player, gameboard currentGameboard) {
    	ArrayList<ArrayList<Integer>> allMoves = new ArrayList<ArrayList<Integer>>();
    	for (int row = 0; row < 8; row++) {
	    for (int col = 0; col < 8; col++) {
		if (currentGameboard.isLegalMove(Player, row, col)) {
		    ArrayList<Integer> move = new ArrayList<Integer>();
		    move.add(row);
		    move.add(col);
		    allMoves.add(move);
		}
	    }
	}
	return allMoves;
    }

    // Checks to see if the particular player has any available moves on the gameboard state given
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

    // Converting row/col integer into a <1-9><a-h> string
    public static String convertIntoString(int row, int col){
	//	System.out.println(row + " " + col);
	String move = "";
	move += Integer.toString(row + 1);
	char c = (char)(col + 97);
	move += Character.toString(c);
	return move;
    }

}
