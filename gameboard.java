import java.util.ArrayList;
import java.util.Collections;

public class gameboard {

	ArrayList<ArrayList<String>> board;
	
	public gameboard() {
		board = new ArrayList<ArrayList<String>>();
	}

	public void placePiece(player currentPlayer, String position) {
		int row = convertIntoNumbers(position).get(0);
		int column = convertIntoNumbers(position).get(1);
		board.get(row).set(column, currentPlayer.name);
	}

	public void populateBoard() {
		for (int index = 0; index < 8; index++) {
			ArrayList<String> newRow = new ArrayList<String>(Collections.nCopies(8, " /"));
			board.add(index, newRow);
		}
		board.get(3).set(3, " O");
		board.get(3).set(4, " X");
		board.get(4).set(3, " X");
		board.get(4).set(4, " O");
	}

	public void printBoard() {
		for (int index = 0; index < 8; index++) {
			System.out.print(index+1);
			for (int innerIndex = 0; innerIndex < 8; innerIndex++) {
				if (innerIndex == 7) {
					System.out.println(board.get(index).get(innerIndex));
				} else {
					System.out.print(board.get(index).get(innerIndex));
				}
			}
		}
		System.out.println("  a b c d e f g h");
	}

	public static ArrayList<Integer> convertIntoNumbers(String position) {
		int row = (int) position.charAt(0) - 49;
		int column = (int) position.charAt(1) - 97;
		ArrayList<Integer> integerPositions = new ArrayList<Integer>();
		integerPositions.add(row);
		integerPositions.add(column);
		return integerPositions;
	}
}