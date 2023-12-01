package model;

public class Stratego {
	
	public static void main(String[] args) {
		 
	//	Timer timerRunnable = new Timer();
	//	Thread timerThread = new Thread(timerRunnable);
		//timerThread.start();
		

		//Board b = new Board();
		//b.displayLogicalBoard();
		Game game = new Game();
		game.getBoard().displayPhysicalBoard();
	}

}
