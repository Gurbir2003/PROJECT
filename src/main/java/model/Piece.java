package model;

public class Piece {
	
	private PieceType pieceType;
	private Color pieceColor;
	
	public Piece(PieceType pt, Color c) {
		this.pieceType = pt;
		this.pieceColor = c;
	}

	/**
	 * @return the pieceType
	 */
	public PieceType getPieceType() {
		return pieceType;
	}

	/**
	 * @return the pieceColor
	 */
	public Color getPieceColor() {
		return pieceColor;
	}


	/*public void move(Direction d) {
		if (d == Direction.FORWARD) {
			Square next = new Square(position.getRow(), position.getCol() - 1);
			this.setPosition(next);
		}
		else if (d == Direction.LEFT) {
			Square next = new Square(position.getRow() - 1, position.getCol());
			this.setPosition(next);
		}
	}*/

}

