package model;

// Singleton design pattern
public class Timer implements Runnable{
	
	// Attributes
	private int hours;
	private int minutes;
	private int seconds;
	private String name;
	
	// Constructor
	public Timer(int h, int m, int s) {
		this.hours = h;
		this.minutes = m;
		this.seconds = s;
	}
	
	// Default Constructor
	public Timer(String name) {
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.name = name;
	}
	
	// What will the thread do
	public void run() {
		System.out.println("Thread started...");
		try {
			while(true) {
				seconds += 1;
				if (seconds == 60) {
					seconds = 0;
					minutes += 1;
					if (minutes == 60) {
						minutes = 0;
						hours += 1;
					}
				}
				System.out.println(this); // Use toString to print the timer
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString() {
		String str = String.format(name + ": %02d:%02d:%02d", hours, minutes, seconds);
		return str;
	}
}
