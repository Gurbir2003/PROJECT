package model;

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

