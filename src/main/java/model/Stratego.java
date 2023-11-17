package model;

public class Stratego {
	
	public static void main(String[] args) {
		 
		Timer timerRunnable = new Timer();
		Thread timerThread = new Thread(timerRunnable);
		timerThread.start();
	}

}
