package model;

public enum Direction {
	
	LEFT("L"),
	RIGHT("R"),
	FORWARD("F"),
	BACKWARD("B");

	private final String direction;
	
	Direction(String d){
		this.direction = d;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}
}
