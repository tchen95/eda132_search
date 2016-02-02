// Emily Zhou and Tammy Chen
import java.util.ArrayList;
public class othello {

    public static void main(String[] args) {
        gameboard currentGameboard = new gameboard();
        currentGameboard.populateBoard();
        currentGameboard.printBoard();
		player p1 = new player("Lester");
		p1.addTally(3);
		System.out.println(p1.getName()+"......tally = "+p1.getTally());
		ArrayList<Integer> positions = gameboard.convertIntoNumbers("8h");
		System.out.println(positions.get(0));
		System.out.println(positions.get(1));
    }

}