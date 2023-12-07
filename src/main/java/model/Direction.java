package model;

/**
 * The Direction class is an enum type class and It is used to represent the directions for the pieces on the board.
 * Each direction has a corresponding abbreviation such as "L" for LEFT, "R" for RIGHT, FORWARD for ("F") and BACKWARD for ("B").
 */

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
