// Emily Zhou and Tammy Chen
public class othello {

    public static void main(String[] args) {
        gameboard currentGameboard = new gameboard();
        currentGameboard.populateBoard();
        currentGameboard.printBoard();
		player p1 = new player("Lester");
		p1.addTally(3);
		System.out.println(p1.getName()+"......tally = "+p1.getTally());
    }

}