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

	public void placePiece(player currentPlayer, String position) {
		int row = convertIntoNumbers(position).get(0);
		int column = convertIntoNumbers(position).get(1);
		board.get(row).set(column, currentPlayer.name);
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

    // Takes in which player is making the move and the desired location
    public boolean isLegalMove(player p, int row, int col){
		String piece = "";
		if(p.getName() != " O"){
		    piece = " X";
		} else {
		    piece = " O";
		}

		// Return false if location is out of bounds
		if(row < 0 || row > 7 || col < 0 || col > 7){
		    return false;
		}

		// Return false if location already has a piece there
		if(board.get(row).get(col) != " /"){
		    return false;
		}
		
		// Checking in all 8 directions
		for(int i = 0; i < 8; i++){
		    int r = row;
		    int c = col;
		    switch (i) { // clock-wise direction checking starting with N
			case 0:
			    r--;
			    break;
			case 1:
			    r--;
			    c++;
			    break;
			case 2:
			    c++;
			    break;
			case 3:
			    r++;
			    c++;
			    break;
			case 4:
			    r++;
			    break;
			case 5:
			    r++;
			    c--;
			    break;
			case 6:
			    c--;
			    break;
			case 7:
			    r--;
			    c--;
			    break;
		    }
		    if(r > -1 && r < 8 && c > -1 && c < 8 &&
		       board.get(r).get(c) != piece && board.get(r).get(c) != " /"){
			System.out.println(r + ", "+ c + ": "+board.get(r).get(c));

			boolean hasPiece = true;
			while(hasPiece){
			    switch (i) { // clock-wise direction checking starting with N
			    case 0:
				r--;
				break;
			    case 1:
				r--;
				c++;
				break;
			    case 2:
				c++;
				break;
			    case 3:
				r++;
				c++;
				break;
			    case 4:
				r++;
				break;
			    case 5:
				r++;
				c--;
				break;
			    case 6:
				c--;
				break;
			    case 7:
				r--;
				c--;
				break;
			    }
			
			    if(r < 0 || r > 7 || c < 0 || c > 7){
				break;
			    }

			    if(board.get(r).get(c) == " /"){
				System.out.println("bad (" + r + ","+ c + "): "+board.get(r).get(c));
				hasPiece = false;
			    }
			    if(board.get(r).get(c) == piece){
				System.out.println("good ("+r + ","+ c + "): "+board.get(r).get(c));
				return true;
			    }
			}
		    }
		}		
		return false;
    }


    public ArrayList<Integer> convertIntoNumbers(String position) {
	int row = (int) position.charAt(0) - 49;
	int column = (int) position.charAt(1) - 97;
	ArrayList<Integer> integerPositions = new ArrayList<Integer>();
	integerPositions.add(row);
	integerPositions.add(column);
	return integerPositions;
    }
}

