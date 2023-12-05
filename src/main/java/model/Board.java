package model;

/**
 * The Board class represents the game board that will be used for the game(Stratego). The board consists of a grid of 10 x 10 squares
 * where pieces can be placed. The board is initialised with grassy squares and four water squares in specific positions.
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
	
	public void displayLogicalBoard() {
		for(int row=0; row<NUM_ROWS; row++) {
			for(int col=0; col<NUM_COLS; col++) {
				System.out.print(grid[row][col]);
			}
			System.out.println();
		}
	}
	
	public void displayPhysicalBoard() {
		for(int row=0; row<NUM_ROWS; row++) {
			for(int col=0; col<NUM_COLS; col++) {
				if(grid[row][col].getPiece() == null) {
					System.out.print("??? ");
				}
				else {
					System.out.print(grid[row][col].getPiece().getPieceColor().getColor().charAt(0) + "" +
									String.format("%02d", grid[row][col].getPiece().getPieceType().getRank()) + " ");
				}
			}
			System.out.println();
		}
	}
	
	public void placePiece(int i, int j, Piece p) {
		grid[i][j].setPiece(p); 
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
