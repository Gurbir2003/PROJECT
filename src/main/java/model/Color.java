package model;
/**
 * The Color class is an enum type class and It is used to represent the team colors for the players. The players can have either red or blue as their team color.
 */
public enum Color {
	
	RED("Red"),
	BLUE("Blue");

	private final String color;
	
	Color(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
}

