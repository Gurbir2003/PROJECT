package model;


import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Singleton design pattern
public class Timer {
    private int hours;
    private int minutes;
    private int seconds;
    private boolean running;
    private final StringProperty timeString = new SimpleStringProperty(this, "timeString", "00:00:00");

    
    public void start() {
        running = true;
        Thread timerThread = new Thread(() -> {
            try {
                while (running) {
                    incrementTime();
                    String time = toString();
                    Platform.runLater(() -> timeString.set(time)); // Update on FX thread
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                running = false;
                Thread.currentThread().interrupt();
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }

    public void stop() {
        running = false;
    }

    private void incrementTime() {
        setSeconds(getSeconds() + 1);
        if (getSeconds() == 60) {
            setSeconds(0);
            setMinutes(getMinutes() + 1);
            if (getMinutes() == 60) {
                setMinutes(0);
                setHours(getHours() + 1);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", getHours(), getMinutes(), getSeconds());
    }

    public StringProperty timeStringProperty() {
        return timeString;
    }

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
}
