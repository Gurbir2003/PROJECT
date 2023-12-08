package model;

/**
 * The Square class represents an individual square on the game board. Each square has a row, a column, a square type which is either grass or water, the square can also contain piece. 
 * piece it contains. The Square class also allows to set a piece on the square and provides a textual representation for display purposes.
 */

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
			return "WWW ";
		}
		else if (this.piece == null ) {
			return "### ";
		}
		else {
			return  piece.getPieceColor().getColor().charAt(0) + "" 
					+ String.format("%02d", piece.getPieceType().getRank()) + " ";
		}
	}
}

