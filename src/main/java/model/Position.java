package model;
/**
 * Represents a board position with row and column. 
 * Useful for tracking piece locations on the game board.
 * Equipped with basic operations like setting/getting coordinates and checking equality.
 */

import java.util.Objects;


public class Position {
    private int row;
    private int column;

    /**
     * Constructs a Position instance representing a specific location on the board.
     * 
     * @param row the row coordinate of the position.
     * @param column the column coordinate of the position.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public String toString() {
		return "(" + row + ", " + column + ")";
	}
}