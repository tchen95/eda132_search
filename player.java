// Emily Zhou and Tammy Chen
// EDA132 VT2016

/*
 * | player 
 * | + tally 
 * | + name
 * ----------
 * | + getName() : String
 * | + getTally() : int
 * | + addTally(int value)
 */

public class player {
    public String name; // player piece identification, " O" or " X"
    public int tally; // number of players' pieces on the board

    public player(String name){
    	this.name = name;
    	this.tally = 0;
    }

    public player(player Player) {
        this.name = Player.name;
        this.tally = Player.tally;
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

    
