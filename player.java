// Emily Zhou, Tammy Chen
// player.java
/*
 * | player 
 * | + tally 
 * | + name
 * ----------
 * | + placePiece(int x, int y)
 * | + reversePiece(int x, int y)
 */

public class player {
    public String name; // player identification
    public int tally; // number of players' pieces on the board

    public player(String name){
	this.name = name;
	this.tally = 0;
    }

    public String getName(){
	return name;
    }

    public int getTally(){
	return tally;
    }

    // Pass the number of pieces to be added to the tally
    public void addTally(int value){
	this.tally = this.tally + value;
    }
}

    
