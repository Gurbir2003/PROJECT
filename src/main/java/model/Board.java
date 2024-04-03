package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Board {
	
	private Square[][] grid;
	private final int NUM_ROWS = 10;
	private final int NUM_COLS = 10;
	
	private Player aiPlayer;
	
	public Board() {
		this.grid = new Square[NUM_ROWS][NUM_COLS];
		initializeBoard();
		this.aiPlayer = new Player("AI", Color.RED);
	    placeAIPiecesRandomly();
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
	
	public void placeAIPiecesRandomly() {
	    for (Map.Entry<PieceType, Integer> entry : aiPlayer.getPieceSet().entrySet()) {
	        PieceType type = entry.getKey();
	        int count = entry.getValue();

	        for (int i = 0; i < count; i++) {
	            Position pos = findRandomFreePositionForAI();
	            if (pos != null) {
	                Piece piece = new Piece(type, aiPlayer.getTeamColor());
	                placePiece(pos.getRow(), pos.getColumn(), piece);
	            }
	        }
	    }
	}

	private Position findRandomFreePositionForAI() {
	    Random rand = new Random();
	    while (true) {
	        int row = rand.nextInt(4);
	        int col = rand.nextInt(NUM_COLS);
	        if (grid[row][col].getPiece() == null && !isWaterCell(new Position(row, col))) {
	            return new Position(row, col);
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
     * Calculates valid moves for a piece at a given position.
     * @param row the row of the piece.
     * @param col the column of the piece.
     * @return a list of valid positions where the piece can move.
     */
    public List<Position> calculateValidMoves(int row, int col) {
        List<Position> validMoves = new ArrayList<>();

        // Define offsets to look around the current position
        int[][] offsets = {
            {-1, 0}, // Up
            {1, 0},  // Down
            {0, -1}, // Left
            {0, 1}   // Right
        };

        for (int[] offset : offsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];

            // Check if the new position is within the board boundaries and not water
            if (isWithinBoundaries(newRow, newCol) && grid[newRow][newCol].getType() != SquareType.WATER) {
                // Check if the square is either free or contains an enemy piece
                if (grid[newRow][newCol].getPiece() == null || 
                    grid[row][col].getPiece().getPieceColor() != grid[newRow][newCol].getPiece().getPieceColor()) {
                    validMoves.add(new Position(newRow, newCol));
                }
            }
        }

        return validMoves;
    }
    
    
    private boolean isWithinBoundaries(int row, int col) {
        return row >= 0 && row < NUM_ROWS && col >= 0 && col < NUM_COLS;
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
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @param direction
	 * @return A MoveStatus representing the status of the Move.
	 */
	public MoveStatus movePiece(int row, int col, String direction, int how_many) {
		int[] offset = getDirectionOffset(direction);
		if (offset == null) {
			System.out.println("Invalid direction, please enter Left (L), Right (R), Up (U) or Down (D).");
			return MoveStatus.INVALID_DIRECTION;
		}
		
		int offsetRow = offset[0];
		int offsetCol = offset[1];

		// Cannot go outside of boundaries of the game
		if (!isWithinBoundaries(row, col, offsetRow, offsetCol)) {
			System.out.println("Destination square is outside the boundaries of the board, please try again");
			return MoveStatus.OUT_OF_BOUND;
		}
		
		// Check that position is not water
		if (grid[row+offsetRow][col+offsetCol].getType() != SquareType.WATER) {
			// Check that position is free
			if (grid[row+offsetRow][col+offsetCol].getPiece() == null) {
				Piece p = grid[row][col].getPiece();
				grid[row][col].setPiece(null);
				grid[row+offsetRow][col+offsetCol].setPiece(p);
				System.out.println("Move played successfully");
				return MoveStatus.SIMPLE;
			}
			// Check that position contain an enemy piece
			Piece p = grid[row][col].getPiece();
			Piece otherPiece = grid[row+offsetRow][col+offsetCol].getPiece();
			if(p.getPieceColor() != otherPiece.getPieceColor()) {
				AttackStatus attackResult = getAttackResult(p, otherPiece, row, col, offsetRow, offsetCol);
				if (attackResult.equals(AttackStatus.FLAG_CAPTURE)) {
					System.out.println("You captured the Flag!");
					return MoveStatus.FLAG_CAPTURE;
				}
				else if (attackResult.equals(AttackStatus.DRAW) || 
						 attackResult.equals(AttackStatus.LOST) ||
						 attackResult.equals(AttackStatus.WON)) {
					System.out.println("Attack move played successfully!");
					return MoveStatus.ATTACK;
				}
				else {
					System.out.println("Could not process the attack move");
					return MoveStatus.ERROR;
				}
			}
		}
		System.out.println("Invalid Move.");
		return MoveStatus.ERROR;
	}
	
	
	public boolean isValidMove(Position from, Position to) {
        List<Position> validMoves = calculateValidMoves(from.getRow(), from.getColumn());
        return validMoves.contains(to);
    }
	
	
	public boolean isEnemyPieceAt(Position position, Color playerColor) {
	    Piece piece = getBoard()[position.getRow()][position.getColumn()].getPiece();
	    return piece != null && piece.getPieceColor() != playerColor;
	}
	
	
	/**
     * Attempts to move a piece on the board from one position to another.
     * 
     * @param fromRow the starting row of the piece to move.
     * @param fromCol the starting column of the piece to move.
     * @param toRow the target row to move the piece to.
     * @param toCol the target column to move the piece to.
     * @return true if the move is successful, false otherwise.
     */
    public MoveStatus movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        // Determine the direction of the move based on from and to positions
        String direction = determineDirection(fromRow, fromCol, toRow, toCol);

        // Call the Board's movePiece method directly with the calculated direction
        MoveStatus moveStatus = movePiece(fromRow, fromCol, direction, calculateMoveDistance(fromRow, fromCol, toRow, toCol));

        return moveStatus;
    }

    private String determineDirection(int fromRow, int fromCol, int toRow, int toCol) {
        if (toRow < fromRow) return "U";
        if (toRow > fromRow) return "D";
        if (toCol < fromCol) return "L";
        if (toCol > fromCol) return "R";
        return "";
    }

    private int calculateMoveDistance(int fromRow, int fromCol, int toRow, int toCol) {
        return Math.max(Math.abs(toRow - fromRow), Math.abs(toCol - fromCol));
    }

	public AttackStatus getAttackResult(Piece p, Piece otherPiece, int row, int col, int offsetRow, int offsetCol) {
		int result = p.attack(otherPiece);
		if (result == 0) {
			System.out.println("It's a draw.");
			System.out.println(p.getPieceColor() + " " + p.getPieceType() + " died.");
			System.out.println(otherPiece.getPieceColor() + " " + otherPiece.getPieceType() + " died.");
			grid[row][col].setPiece(null);
			grid[row+offsetRow][col+offsetCol].setPiece(null);
			return AttackStatus.DRAW;
		}
		else if(result == -1) {
			System.out.println(p.getPieceColor() + " lost the fight.");
			System.out.println(p.getPieceColor() + " " + p.getPieceType() + " died.");
			System.out.println(otherPiece.getPieceColor() + " " + otherPiece.getPieceType() + " survived.");
			grid[row][col].setPiece(null);
			return AttackStatus.LOST;
		}
		else if(result == 1){
			if (otherPiece.getPieceType().equals(PieceType.FLAG)) {
				return AttackStatus.FLAG_CAPTURE;
			}
			System.out.println(p.getPieceColor() + " won the fight.");
			System.out.println(p.getPieceColor() + " " + p.getPieceType() + " survived.");
			System.out.println(otherPiece.getPieceColor() + " " + otherPiece.getPieceType() + " died.");
			grid[row][col].setPiece(null);
			grid[row+offsetRow][col+offsetCol].setPiece(p);
			return AttackStatus.WON;
		}
		else {
			return AttackStatus.ERROR;
		}
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
	
	
	public boolean isWaterCell(Position position) {
        int row = position.getRow();
        int col = position.getColumn();

        if ((row == 4 || row == 5) && (col == 2 || col == 3 || col == 6 || col == 7)) {
            return true;
        }
        return false;
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

	public Player getAiPlayer() {
		return aiPlayer;
	}
}
