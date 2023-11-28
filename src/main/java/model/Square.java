package model;

public class Square {
	
	private int row;
	private int col;
	private SquareType type;
	private Piece piece;
	
	public Square(int r, int c, SquareType t, Piece p) {
		this.row = r;
		this.col = c;
		this.type = t;
		this.piece = p;
	}

	/**
	 * @return the row of the square
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column of the square
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @return the type
	 */
	public SquareType getType() {
		return type;
	}
	
	
	/**
	 * @return the piece
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * @param piece the piece to set
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public String toString() {
		if (this.type == SquareType.WATER) {
			return "[##]";
		}
		else {
			return "[" + row + "" + col + "]";
		}
	}
}

