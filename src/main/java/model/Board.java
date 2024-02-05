package model;

/**
 * The Board class represents the game board that will be used for the game(Stratego). The board consists of a grid of 10 x 10 squares where pieces can be placed.
 * The board is initialised with grass squares and four water squares in specific positions.
 * It provides methods to display the logical and physical representation of the board, as well as to place a piece on the board.
 *
 */
public class Board {
	
	private Square[][] grid;
	private final int NUM_ROWS = 10;
	private final int NUM_COLS = 10;
	
	public Board() {
		this.grid = new Square[NUM_ROWS][NUM_COLS];
		initializeBoard();
	}
	
	private void initializeBoard() {
		for(int row=0; row<NUM_ROWS; row++) {
			for(int col=0; col<NUM_COLS; col++) {
				if (row == 4 && (col == 2 || col == 3 || col == 6 || col == 7)) {
					grid[row][col] = new Square(row, col, SquareType.WATER, null);
				}
				else if (row == 5 && (col == 2 || col == 3 || col == 6 || col == 7)) {
					grid[row][col] = new Square(row, col, SquareType.WATER, null);
				}
				else {
					grid[row][col] = new Square(row, col, SquareType.GRASS, null);
				}
			}
		}
	}
	
	public void displayBoard() {
		System.out.println("   0   1   2   3   4   5   6   7   8   9");
		for(int row=0; row<NUM_ROWS; row++) {
			for(int col=0; col<NUM_COLS; col++) {
				if(col == 0) {
					System.out.print(row + " ");
				}
				System.out.print(grid[row][col]);
			}
			System.out.println();
		}
	}
	
	
	public void placePiece(int i, int j, Piece p) {
		grid[i][j].setPiece(p); 
	}
	
	/**
	 * The method checks whether there is a free square around the selected one. 
	 * False otherwise.
	 * @param row the row number
	 * @param col the column number
	 * @return true if there is any free square around the selected position. False otherwise.
	 */
	public boolean lookAround(int row, int col) {
		if (this.hasFreeSquare(row, col, 1, 0) || 
				this.hasFreeSquare(row, col, -1, 0) || 
				this.hasFreeSquare(row, col, 0, 1) || 
				this.hasFreeSquare(row, col, 0, -1)) {
			return true;
		}
		return false;
	}
	
	
	private boolean hasFreeSquare(int row, int col, int offsetRow, int offsetCol) {
		// Can't select Water square or Empty Square
		if (grid[row][col].getType() == SquareType.WATER ||
			grid[row][col].getPiece() == null) {
			return false;
		}
		
		// Cannot go outside of boundaries of the game
		if (!isWithinBoundaries(row, col, offsetRow, offsetCol)) {
			return false;
		}
		
		// Look around
		if (grid[row+offsetRow][col+offsetCol].getType() != SquareType.WATER) {
			if (grid[row+offsetRow][col+offsetCol].getPiece() == null) {
				return true;
			}
			Piece p = grid[row][col].getPiece();
			Piece otherPiece = grid[row+offsetRow][col+offsetCol].getPiece();
			if(p.getPieceColor() != otherPiece.getPieceColor()) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean movePiece(int row, int col, String direction) {
		int[] offset = getDirectionOffset(direction);
		if (offset == null) {
			System.out.println("Invalid direction, please enter Left (L), Right (R), Up (U) or Down (D).");
			return false;
		}
		int offsetRow = offset[0];
		int offsetCol = offset[1];
		
		// Cannot go outside of boundaries of the game
		if (!isWithinBoundaries(row, col, offsetRow, offsetCol)) {
			System.out.println("Destination square is outside the boundaries of the board, please try again");
			return false;
		}
		
		// Check that position is not water
		if (grid[row+offsetRow][col+offsetCol].getType() != SquareType.WATER) {
			// Check that position is free
			if (grid[row+offsetRow][col+offsetCol].getPiece() == null) {
				Piece p = grid[row][col].getPiece();
				grid[row][col].setPiece(null);
				grid[row+offsetRow][col+offsetCol].setPiece(p);
				System.out.println("Move played successfully");
				return true;
			}
			// Check that position contain an enemy piece
			Piece p = grid[row][col].getPiece();
			Piece otherPiece = grid[row+offsetRow][col+offsetCol].getPiece();
			if(p.getPieceColor() != otherPiece.getPieceColor()) {
				int result = p.attack(otherPiece);
				if (result == 0) {
					System.out.println("It's a draw.");
					System.out.println(p.getPieceColor() + " " + p.getPieceType() + " died.");
					System.out.println(otherPiece.getPieceColor() + " " + otherPiece.getPieceType() + " died.");
					grid[row][col].setPiece(null);
					grid[row+offsetRow][col+offsetCol].setPiece(null);
				}
				else if(result == -1) {
					System.out.println(p.getPieceColor() + " lost the fight.");
					System.out.println(p.getPieceColor() + " " + p.getPieceType() + " died.");
					System.out.println(otherPiece.getPieceColor() + " " + otherPiece.getPieceType() + " survived.");
					grid[row][col].setPiece(null);
				}
				else {
					System.out.println(p.getPieceColor() + " won the fight.");
					System.out.println(p.getPieceColor() + " " + p.getPieceType() + " survived.");
					System.out.println(otherPiece.getPieceColor() + " " + otherPiece.getPieceType() + " died.");
					grid[row][col].setPiece(null);
					grid[row+offsetRow][col+offsetCol].setPiece(p);
				}
				System.out.println("Move played successfully");
				return true;
			}
		}
		System.out.println("Invalid Move.");
		return false;
	}
	
	/**
	 * Function that return the offset couple (x, y) depending on the direction
	 * @param direction the direction the player wants to move
	 * @return the couple representing the offset to reach that direction
	 */
	private int[] getDirectionOffset(String direction) {
		if (direction.equalsIgnoreCase("up") || direction.equalsIgnoreCase("u")) {
			int[] toReturn = {-1, 0};
			return toReturn;
		}
		if (direction.equalsIgnoreCase("down") || direction.equalsIgnoreCase("d")) {
			int[] toReturn = {1, 0};
			return toReturn;
		}
		if (direction.equalsIgnoreCase("left") || direction.equalsIgnoreCase("l")) {
			int[] toReturn = {0, -1};
			return toReturn;
		}
		if (direction.equalsIgnoreCase("right") || direction.equalsIgnoreCase("r")) {
			int[] toReturn = {0, 1};
			return toReturn;
		}
		return null;
	}
	
	private boolean isWithinBoundaries(int row, int col, int offsetRow, int offsetCol) {
		if (row + offsetRow < 0 || row + offsetRow > 9) {
			return false;
		}
		if (col + offsetCol < 0 || col + offsetCol > 9) {
			return false;
		}
		return true;
	}
	

	/**
	 * @return the board
	 */
	public Square[][] getBoard() {
		return grid;
	}

	/**
	 * @return the numRows
	 */
	public int getNumRows() {
		return NUM_ROWS;
	}

	/**
	 * @return the numCols
	 */
	public int getNumCols() {
		return NUM_COLS;
	}
}

