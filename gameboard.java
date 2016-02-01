import java.util.ArrayList;
import java.util.Collections;

public class gameboard {

	ArrayList<ArrayList<String>> board;
	
	public gameboard() {
		board = new ArrayList<ArrayList<String>>();
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
}