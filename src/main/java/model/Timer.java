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
		this.setHours(h);
		this.setMinutes(m);
		this.setSeconds(s);
	}
	
	// Default Constructor
	public Timer(String name) {
		this.setHours(0);
		this.setMinutes(0);
		this.setSeconds(0);
		this.name = name;
	}
	
	// What will the thread do
	public void run() {
		System.out.println("Thread started...");
		try {
			while(true) {
				setSeconds(getSeconds() + 1);
				if (getSeconds() == 60) {
					setSeconds(0);
					setMinutes(getMinutes() + 1);
					if (getMinutes() == 60) {
						setMinutes(0);
						setHours(getHours() + 1);
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
		String str = String.format("%02d:%02d:%02d", getHours(), getMinutes(), getSeconds());
		return str;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}
}

