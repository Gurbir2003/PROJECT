package model;
/**
 * A simple timer for JavaFX applications that updates and runs concurrently to other action that have been going on inn the game.Represented time in HH:MM:SS format.
 * The timer runs in its own thread to avoid blocking the UI and updates every second.
 */

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Timer {
    private int hours;
    private int minutes;
    private int seconds;
    private boolean running;
    private final StringProperty timeString = new SimpleStringProperty(this, "timeString", "00:00:00");

    /**
     * Starts the timer in a separate thread. Continuously increments the time by one second
     * and updates the display string. The timer runs until stopped.
     */
    public void start() {
        running = true;
        Thread timerThread = new Thread(() -> {
            try {
                while (running) {
                    incrementTime();
                    String time = toString();
                    Platform.runLater(() -> timeString.set(time));
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

    /**
     * Stops the timer which terminates the timer thread.
     */
    public void stop() {
        running = false;
    }

    /**
     * Increments the time counters (seconds, minutes, hours) appropriately.
     * Rolls over the seconds to minutes and minutes to hours as needed.
     */
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
