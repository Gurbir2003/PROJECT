package model;

public class Timer implements Runnable{
	
	// Attributes
	private int hours;
	private int minutes;
	private int seconds; 
	
	// Constructor
	public Timer(int h, int m, int s) {
		this.hours = h;
		this.minutes = m;
		this.seconds = s;
	}
	
	// Default Constructor
	public Timer() {
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
		
	}
	
	// This method tells what the thread will do.
	public void run() {
		System.out.println("Thread Started...");
		try {
			while (true) {
				seconds += 1;
				if(seconds == 60) {
					seconds = 0;
					minutes += 1;
					if(minutes == 60) {
						minutes = 0;
						hours += 1;
					}
				}
				System.out.println(this); //Use toString to print the timer
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String toString() {
		String str = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		return str;
		
		
	}

}
