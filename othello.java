// Emily Zhou and Tammy Chen
import java.util.ArrayList;
import java.util.Scanner;

public class othello {

    public static void main(String[] args) {
	System.out.println("Welcome to Othello!");
        gameboard currentGameboard = new gameboard(new ArrayList<ArrayList<String>>());
        currentGameboard.populateBoard();
        gameboard copy = new gameboard(currentGameboard);
	player human = new player(" X");
	player computer = new player(" O");
	player copyHuman = new player(human);
	player copyComputer = new player(computer);
	ArrayList<Integer> numbMove = alphaBeta(copy, 4, -100, 100, true, copyComputer, copyHuman);
	System.out.println("Move: ("+numbMove.get(0)+","+numbMove.get(1)+")");

	// player currentPlayer = human;
	// player otherPlayer = computer;
	// human.addTally(2);
	// computer.addTally(2);
	// boolean gameComplete = false;
	// while (!gameComplete) {
	//     if (doesPlayerHaveMoves(currentPlayer, currentGameboard)) {
	// 		if (currentPlayer.getName() == " X") {
	// 			currentGameboard.printBoard();
	// 			System.out.println(currentPlayer.getName() + ": " + currentPlayer.getTally() +
	// 					   "," + otherPlayer.getName() + ": " + otherPlayer.getTally());
						    
	// 			Scanner reader = new Scanner(System.in);
	// 			System.out.println("It's your turn! What move would you like to make?");
	// 			String input = reader.next();	    
	// 			boolean makeMove = currentGameboard.placePiece(currentPlayer, otherPlayer, input);
	// 			while (!makeMove) {
	// 		    	System.out.println("That move is an illegal move. Try another one!");
	// 		    	String tryMoveAgain = reader.next();
	// 		    	makeMove = currentGameboard.placePiece(currentPlayer, otherPlayer, tryMoveAgain);
	// 			}
					
	// 		} else {
	// 			player copyComputer = new player(currentPlayer);
	// 			player copyHuman = new player(otherPlayer);
	// 			gameboard copyGameBoard = new gameboard(currentGameboard);
	// ArrayList<Integer> numbMove = alphaBeta(copyGameBoard, 4, -100, 100, true, copyComputer, copyHuman);
	// 			String newMove = convertIntoString(numbMove.get(0), numbMove.get(1));
	// 			boolean makeMove = currentGameboard.placePiece(currentPlayer, otherPlayer, newMove);
	// 			currentGameboard.printBoard();
	// 			System.out.println("Your opponent has placed a piece onto " + newMove);
	// 		}
	// 		player saveState = otherPlayer;
	// 		otherPlayer = currentPlayer;
	// 		currentPlayer = saveState;
	//     } else if (doesPlayerHaveMoves(otherPlayer, currentGameboard)) {
	// 		player saveState = otherPlayer;
	// 		otherPlayer = currentPlayer;
	// 		currentPlayer = saveState;
	//     } else {
	// 		gameComplete = true;
	//     }

	// } if (human.tally > computer.tally) {
	//     System.out.println(human.name + " is the winner with a score of " + human.tally + "-" + computer.tally);
	// } else if (computer.tally > human.tally) {
	//     System.out.println(computer.name + " is the winner with a score of " + computer.tally + "-" + human.tally);			
	// } else {
	//     System.out.println("The game is a draw, with a score of " + human.tally + "-" + computer.tally);
	// }

    }

    public static ArrayList<Integer> alphaBeta(gameboard updatedBoard, int depth, int alpha, int beta, boolean isMaxPlayer, player currentPlayer, player otherPlayer) {
    	//base case: if depth is 0 or neither player has a move to make
    	if (depth <= 0 || !doesPlayerHaveMoves(currentPlayer, updatedBoard) && !doesPlayerHaveMoves(otherPlayer, updatedBoard)) {
	    if (isMaxPlayer) {
		System.out.println("depth = "+depth);
		ArrayList<Integer> movesAndTally = new ArrayList<Integer>();
		movesAndTally.add(-1000); // sentry value, no meaning
		movesAndTally.add(-1000);
		movesAndTally.add(currentPlayer.getTally() - otherPlayer.getTally());
		// heuristic value, for computer - human
		// when isMax is true (i.e. computer is "currentPlayer")
		return movesAndTally;
	    } else {
		ArrayList<Integer> movesAndTally = new ArrayList<Integer>();
		movesAndTally.add(-1000); // sentry value, no meaning
		movesAndTally.add(-1000);
		movesAndTally.add(otherPlayer.getTally() - currentPlayer.getTally());
		// heuristic value, this time isMax is false (i.e. computer is "otherPlayer")
		// and we need computer - human = "otherPlayer" - "currentPlayer"
		return movesAndTally;
	    }
    	}
    	//checking if current player is maximizing player
    	if (isMaxPlayer) {
	    int value = -100;
	    //checks if the current player doesn't have a move, but the opposing player does
	    if (!doesPlayerHaveMoves(currentPlayer, updatedBoard) && doesPlayerHaveMoves(otherPlayer, updatedBoard)) {
		ArrayList<Integer> bestMoveSoFar = new ArrayList<Integer>();
		bestMoveSoFar.add(0);
		bestMoveSoFar.add(0);
		bestMoveSoFar.add(0);
		ArrayList<Integer> possibleMove = alphaBeta(updatedBoard, depth, alpha, beta, !isMaxPlayer, otherPlayer, currentPlayer);
		value = Math.max(value, possibleMove.get(2));
		alpha = Math.max(alpha, value);
		if (beta <= alpha) {
		    return bestMoveSoFar;
		}
		possibleMove.set(2, value);
		bestMoveSoFar = possibleMove;
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
		    ArrayList<Integer> currentMove = allPossibleMoves.get(index);
		    copyBoard.placePiece(currentPlayer, otherPlayer, convertIntoString(currentMove.get(0), currentMove.get(1)));
		    ArrayList<Integer> result = alphaBeta(copyBoard, depth - 1, alpha, beta, !isMaxPlayer, otherPlayer, currentPlayer);
		    System.out.println("For loop Move: ("+currentMove.get(0)+","+currentMove.get(1)+")");
		    if (result.get(2) > value) {
			value = result.get(2);
			bestMoveSoFar = result;
		    }
		    alpha = Math.max(alpha, value);
		    if (beta <= alpha) {
			break;
		    }
		}

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
		return possibleMove;
	    } else {
		//if both players have a move
		ArrayList<ArrayList<Integer>> allPossibleMoves = getAllMoves(currentPlayer, updatedBoard);
		ArrayList<Integer> bestMoveSoFar = new ArrayList<Integer>();
		bestMoveSoFar.add(0);
		bestMoveSoFar.add(0);
		bestMoveSoFar.add(0);
		for (int index = 0; index < allPossibleMoves.size(); index++) {
		    gameboard copyBoard = new gameboard(updatedBoard);
		    ArrayList<Integer> currentMove = allPossibleMoves.get(index);
		    copyBoard.placePiece(currentPlayer, otherPlayer, convertIntoString(currentMove.get(0), currentMove.get(1)));
		    ArrayList<Integer> result = alphaBeta(copyBoard, depth - 1, alpha, beta, !isMaxPlayer, otherPlayer, currentPlayer);
		    if (result.get(2) < value) {
			value = result.get(2);
			bestMoveSoFar = result;
		    }
		    beta = Math.min(beta, value);
		    if (beta <= alpha) {
			break;
		    }
		}
		System.out.println("Move: ("+bestMoveSoFar.get(0)+","+bestMoveSoFar.get(1)+")");
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
	String move = "";
	move += Integer.toString(row + 1);
	char c = (char)(col + 97);
	move += Character.toString(c);
	return move;
    }

}
