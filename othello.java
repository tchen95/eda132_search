// Emily Zhou and Tammy Chen

public class othello {

    public static void main(String[] args) {
        saySomething("Hello World");
	player p1 = new player("Lester");
	p1.addTally(3);
	System.out.println(p1.getName()+"......tally = "+p1.getTally());
    }

    public static void saySomething(String string) {
    	System.out.println(string);
    }

}