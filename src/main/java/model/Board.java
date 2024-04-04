package model;
/**
 * Manages the game board for Stratego game, including initialising the board, placing pieces, and handling movements and attacks. 
 * The board is a 10x10 grid with specific areas designated as water. 
 * AI player pieces are placed randomly on the board at the start.
 * 
 * Functions include initialising the board with water and grass squares, placing AI player pieces randomly, calculating valid moves for pieces, moving pieces on the board, 
 * and handling attacks between pieces.
 * 
 * Key Methods:
 * - initializeBoard(): Sets up the grid with water and grass squares.
 * - placeAIPiecesRandomly(): Places AI pieces on the board randomly.
 * - calculateValidMoves(int, int): Calculates valid moves for a piece at the given position.
 * - movePiece(int, int, String, int): Moves a piece in a specified direction.
 * - isWaterCell(Position): Checks if a given position is water.
 * - getAttackResult(Piece, Piece, int, int, int, int): Determines the outcome of an attack between two pieces.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import uk.co.gurbir.PROJECT.MessageService;

public class Board {
	
	private Square[][] grid;
	private final int NUM_ROWS = 10;
	private final int NUM_COLS = 10;
	
	private Player aiPlayer;
	
	private MessageService messageService;
	
	public Board(MessageService messageService) {
		this.grid = new Square[NUM_ROWS][NUM_COLS];
		this.messageService = messageService;
		initializeBoard();
		this.aiPlayer = new Player("AI", Color.RED);
	    placeAIPiecesRandomly();
	}
	
	/**
	 * Initialises the game board, marking squares as either water or grass.
	 * Water squares are at positions row (4||5) and column (2||3||6||7), simulating obstacles. 
	 */
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
	
	/**
	 * Places AI player's pieces randomly on the game board.
	 * Iterates over each piece type the AI player has, determines the number of each piece, 
	 * and places them in random positions on the board that are not occupied by other pieces or water.
	 */
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

	/**
	 * Finds random places for the AI to place pieces randomly on the game board.
	 * Ensures the selected position is not water and does not already contain a piece.
	 * @return a valid, random position on the board for AI piece placement.
	 */
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
	
	/**
	 * Prints the current state of the board to the console/terminal.
	 * Displays a 10x10 grid with each square's contents. Water squares and pieces are represented as per their toString implementations.
	 */
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
	
	/**
	 * Places a piece on the board at the specified coordinates.
	 * @param i the row coordinate where the piece is to be placed.
	 * @param j the column coordinate where the piece is to be placed.
	 * @param p the Piece object to be placed on the board.
	 */
	public void placePiece(int i, int j, Piece p) {
		grid[i][j].setPiece(p); 
	}
	
	
	public void clearPlayerPieces() {
	    int startRow = NUM_ROWS / 2;
	    for (int row = startRow; row < NUM_ROWS; row++) {
	        for (int col = 0; col < NUM_COLS; col++) {
	            if (grid[row][col].getPiece() != null && grid[row][col].getPiece().getPieceColor() == Color.BLUE) {
	                grid[row][col].setPiece(null);
	            }
	        }
	    }
	    messageService.addMessage("Player pieces cleared.");
	}
	
	/**
     * Calculates valid moves for a piece at a given position.
     * @param row the row of the piece.
     * @param col the column of the piece.
     * @return a list of valid positions where the piece can move.
     */
    public List<Position> calculateValidMoves(int row, int col) {
        List<Position> validMoves = new ArrayList<>();
        Piece currentPiece = grid[row][col].getPiece();
        
        if (currentPiece != null && currentPiece.getPieceType() == PieceType.SCOUT) {
            // SCOUT can move in straight lines
            addScoutMoves(row, col, validMoves);
        }
        else {
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
        }
        return validMoves;
    }
    
    /**
     * Adds all valid moves for a Scout piece to a list. Scouts can move any number of squares 
     * vertically or horizontally until they encounter an obstacle (another piece or water) or 
     * reach the board's edge. If an enemy piece is encountered, that position is added as a valid 
     * move but the Scout cannot move further in that direction.
     * 
     * @param row The starting row of the Scout.
     * @param col The starting column of the Scout.
     * @param validMoves A list of positions to which the Scout can validly move. This list is updated in-place.
     */
    private void addScoutMoves(int row, int col, List<Position> validMoves) {
        // Directions: Up, Down, Left, Right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];

            while (isWithinBoundaries(nextRow, nextCol) && grid[nextRow][nextCol].getType() != SquareType.WATER) {
                // Stop if there's any piece in the way
                if (grid[nextRow][nextCol].getPiece() != null) {
                    // Add the position if it's an enemy piece, then break
                    if (grid[nextRow][nextCol].getPiece().getPieceColor() != grid[row][col].getPiece().getPieceColor()) {
                        validMoves.add(new Position(nextRow, nextCol));
                    }
                    break;
                }

                validMoves.add(new Position(nextRow, nextCol));
                nextRow += dir[0];
                nextCol += dir[1];
            }
        }
    }
    
    
    /**
     * Moves a Scout piece from its current position to a target position, if valid. 
     * Scouts can move horizontally or vertically over multiple squares until they encounter 
     * an obstacle. If an enemy piece is encountered during the move, combat is initiated.
     * 
     * @param fromRow The starting row of the Scout.
     * @param fromCol The starting column of the Scout.
     * @param toRow The target row for the Scout.
     * @param toCol The target column for the Scout.
     * @return MoveStatus indicating the outcome of the move attempt. This could be a successful move, 
     * an attack, an invalid direction, or an error if the move is not permissible.
     */
    public MoveStatus moveScout(int fromRow, int fromCol, int toRow, int toCol) {
        String direction = determineDirection(fromRow, fromCol, toRow, toCol);
        int[] offset = getDirectionOffset(direction);

        if (offset == null) return MoveStatus.INVALID_DIRECTION;

        int currentRow = fromRow + offset[0];
        int currentCol = fromCol + offset[1];

        while (isWithinBoundaries(currentRow, currentCol)) {
            if (grid[currentRow][currentCol].getType() == SquareType.WATER) break;

            Piece encounteredPiece = grid[currentRow][currentCol].getPiece();
            if (encounteredPiece != null) {
                if (encounteredPiece.getPieceColor() != grid[fromRow][fromCol].getPiece().getPieceColor()) {
                    handleScoutCombat(fromRow, fromCol, currentRow, currentCol);
                    return MoveStatus.ATTACK;
                } else {
                    break;
                }
            }

            if (currentRow == toRow && currentCol == toCol) {
                grid[toRow][toCol].setPiece(grid[fromRow][fromCol].getPiece());
                grid[fromRow][fromCol].setPiece(null);
                return MoveStatus.SIMPLE;
            }

            currentRow += offset[0];
            currentCol += offset[1];
        }

        return MoveStatus.ERROR;
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
			//messageService.addMessage("Invalid direction, please enter Left (L), Right (R), Up (U) or Down (D).");
			return MoveStatus.INVALID_DIRECTION;
		}
		
		int offsetRow = offset[0];
		int offsetCol = offset[1];

		// Cannot go outside of boundaries of the game
		if (!isWithinBoundaries(row, col, offsetRow, offsetCol)) {
			//messageService.addMessage("Destination square is outside the boundaries of the board, please try again");
			return MoveStatus.OUT_OF_BOUND;
		}
		
		// Check that position is not water
		if (grid[row+offsetRow][col+offsetCol].getType() != SquareType.WATER) {
			// Check that position is free
			if (grid[row+offsetRow][col+offsetCol].getPiece() == null) {
				Piece p = grid[row][col].getPiece();
				grid[row][col].setPiece(null);
				grid[row+offsetRow][col+offsetCol].setPiece(p);
				//messageService.addMessage("Move played successfully");
				return MoveStatus.SIMPLE;
			}
			// Check that position contain an enemy piece
			Piece p = grid[row][col].getPiece();
			Piece otherPiece = grid[row+offsetRow][col+offsetCol].getPiece();
			if(p.getPieceColor() != otherPiece.getPieceColor()) {
				AttackStatus attackResult = getAttackResult(p, otherPiece, row, col, offsetRow, offsetCol);
				if (attackResult.equals(AttackStatus.FLAG_CAPTURE)) {
					messageService.addMessage("You captured the Flag!");
					return MoveStatus.FLAG_CAPTURE;
				}
				else if (attackResult.equals(AttackStatus.DRAW) || 
						 attackResult.equals(AttackStatus.LOST) ||
						 attackResult.equals(AttackStatus.WON)) {
					//messageService.addMessage("Attack move played successfully!");
					return MoveStatus.ATTACK;
				}
				else {
					//messageService.addMessage("Could not process the attack move");
					return MoveStatus.ERROR;
				}
			}
		}
		//messageService.addMessage("Invalid Move.");
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
	    Piece piece = grid[fromRow][fromCol].getPiece();
	    if (piece == null) return MoveStatus.ERROR;

	    // If the piece is a SCOUT, handle its unique movement
	    if (piece.getPieceType() == PieceType.SCOUT) {
	        return moveScout(fromRow, fromCol, toRow, toCol);
	    }

	    // Handle movement for other pieces (existing logic)
	    String direction = determineDirection(fromRow, fromCol, toRow, toCol);
	    return movePiece(fromRow, fromCol, direction, calculateMoveDistance(fromRow, fromCol, toRow, toCol));
	}

    private String determineDirection(int fromRow, int fromCol, int toRow, int toCol) {
        if (toRow < fromRow) return "U";
        if (toRow > fromRow) return "D";
        if (toCol < fromCol) return "L";
        if (toCol > fromCol) return "R";
        return "";
    }

    /**
     * Calculates the distance of a move on the board, considering both row and column displacement.
     * The distance is the maximum of the absolute row and column differences, effectively measuring steps in a grid where diagonal moves are not allowed.
     *
     * @param fromRow the starting row position.
     * @param fromCol the starting column position.
     * @param toRow the target row position.
     * @param toCol the target column position.
     * @return the distance of the move as an integer.
     */
    private int calculateMoveDistance(int fromRow, int fromCol, int toRow, int toCol) {
        return Math.max(Math.abs(toRow - fromRow), Math.abs(toCol - fromCol));
    }
    
    /**
     * Handles combat scenarios specifically for a Scout piece attacking another piece.
     * Determines the outcome based on game rules and updates the board accordingly.
     * 
     * @param fromRow the starting row of the Scout piece.
     * @param fromCol the starting column of the Scout piece.
     * @param targetRow the row of the target piece being attacked.
     * @param targetCol the column of the target piece being attacked.
     * @return AttackStatus indicating whether the Scout WON, LOST, or if the result was a DRAW. 
     * Returns ERROR if the attack is invalid (e.g., targeting an ally or an empty square).
     */

    private AttackStatus handleScoutCombat(int fromRow, int fromCol, int targetRow, int targetCol) {
        Piece scoutPiece = grid[fromRow][fromCol].getPiece();
        Piece targetPiece = grid[targetRow][targetCol].getPiece();

        if (targetPiece == null || scoutPiece.getPieceColor() == targetPiece.getPieceColor()) {
            return AttackStatus.ERROR; // Invalid combat scenario
        }

        // Determine the outcome of the combat
        int combatResult = scoutPiece.attack(targetPiece);
        if (combatResult > 0) {
            // SCOUT wins, move to the target square and remove the enemy piece
            grid[targetRow][targetCol].setPiece(scoutPiece);
            grid[fromRow][fromCol].setPiece(null);
            return AttackStatus.WON;
        } else if (combatResult < 0) {
            // SCOUT loses, remove the SCOUT from the board
            grid[fromRow][fromCol].setPiece(null);
            return AttackStatus.LOST;
        } else {
            // Draw, both pieces are removed
            grid[fromRow][fromCol].setPiece(null);
            grid[targetRow][targetCol].setPiece(null);
            return AttackStatus.DRAW;
        }
    }

    /**
     * Determines the outcome of an attack between two pieces on the board.
     * The result can be a win, loss, draw, flag capture, or an error.
     * 
     * @param p the attacking piece.
     * @param otherPiece the defending piece.
     * @param row the row of the attacking piece.
     * @param col the column of the attacking piece.
     * @param offsetRow the row difference between attacker and defender.
     * @param offsetCol the column difference between attacker and defender.
     * @return the status of the attack (WIN, LOST, DRAW, FLAG_CAPTURE, ERROR) based on the combat rules.
     */
	public AttackStatus getAttackResult(Piece p, Piece otherPiece, int row, int col, int offsetRow, int offsetCol) {
		int result = p.attack(otherPiece);
		if (result == 0) {
			messageService.addMessage("It's a draw.");
			messageService.addMessage(p.getPieceColor() + " " + p.getPieceType() + " died.");
			messageService.addMessage(otherPiece.getPieceColor() + " " + otherPiece.getPieceType() + " died.");
			grid[row][col].setPiece(null);
			grid[row+offsetRow][col+offsetCol].setPiece(null);
			return AttackStatus.DRAW;
		}
		else if(result == -1) {
			messageService.addMessage(p.getPieceColor() + " lost the fight.");
			messageService.addMessage(p.getPieceColor() + " " + p.getPieceType() + " died.");
			messageService.addMessage(otherPiece.getPieceColor() + " " + otherPiece.getPieceType() + " survived.");
			grid[row][col].setPiece(null);
			return AttackStatus.LOST;
		}
		else if(result == 1){
			if (otherPiece.getPieceType().equals(PieceType.FLAG)) {
				return AttackStatus.FLAG_CAPTURE;
			}
			messageService.addMessage(p.getPieceColor() + " won the fight.");
			messageService.addMessage(p.getPieceColor() + " " + p.getPieceType() + " survived.");
			messageService.addMessage(otherPiece.getPieceColor() + " " + otherPiece.getPieceType() + " died.");
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
	
	/**
	 * Checks if a position, after applying the given offsets, is within the game board boundaries.
	 * 
	 * @param row the original row of the position.
	 * @param col the original column of the position.
	 * @param offsetRow the row offset to apply.
	 * @param offsetCol the column offset to apply.
	 * @return true if the new position is within the 10x10 board otherwise returns false.
	 */
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
	 * Determines if a specific board position is a water cell, which is non-traversable.
	 * 
	 * @param position the position to check.
	 * @return true if the position is one of the predefined water cells otherwise returns false.
	 */
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
