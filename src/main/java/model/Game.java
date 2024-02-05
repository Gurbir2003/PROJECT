package model;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Game class is used to represent the main logic of the game.
 * It is also used to set up the pieces on the board and use to initialise the players and assign them to a team using the createPlayers() method along with allowing the pieces to make movements on the board.
 * 
 *  */

public class Game {
	
	private Player[] players; // Red is P1, Blue is P2
	private Board board;
	
	public Game() {
		players = new Player[2];
		board = new Board();
		createPlayers();
		setUpBoardPieces();
	}
	
	private void createPlayers() {
		// Replace following code with asking player name;
		Player p1 = new Player("Red", Color.RED);
		Player p2 = new Player("Blue", Color.BLUE);
		players[0] = p1;
		players[1] = p2;
	}

	private void setUpBoardPieces() {
		for(Player p: players) {
			Thread thread = new Thread(new Runnable() {
				Player player = p;

				@Override
				public void run() {
					System.out.println("Thread starting for player " + p.getTeamColor().getColor());
					for(Map.Entry<PieceType, Integer> entry: p.getPieceSet().entrySet()) {
						PieceType pt = entry.getKey();
						int amount = entry.getValue();
						int x, y;
						for(int i=0; i<amount; i++) {
							// ASK THE USER TO PUT THAT PIECETYPE THAT AMOUNT OF TIME.
							if (player.getTeamColor() == Color.BLUE) {
								x = ThreadLocalRandom.current().nextInt(6, 10); // generate random int between 6 and 9
								y = ThreadLocalRandom.current().nextInt(0, 10); // generate random int between 0 and 9
								while(board.getBoard()[x][y].getPiece() != null) { // checking that the square is free
									x = ThreadLocalRandom.current().nextInt(6, 10); // generate random int between 6 and 9
									y = ThreadLocalRandom.current().nextInt(0, 10); // generate random int between 0 and 9
								}
								board.placePiece(x, y, new Piece(pt, Color.BLUE));
							}
							else {
								x = ThreadLocalRandom.current().nextInt(0, 4); // generate random int between 0 and 4
								y = ThreadLocalRandom.current().nextInt(0, 10); // generate random int between 0 and 9
								while(board.getBoard()[x][y].getPiece() != null) { // checking that the square is free
									x = ThreadLocalRandom.current().nextInt(0, 4); // generate random int between 0 and 4
									y = ThreadLocalRandom.current().nextInt(0, 10); // generate random int between 0 and 9
								}
								board.placePiece(x, y, new Piece(pt, Color.RED));
							}
						}
					}
				}
			});
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public void play() {
		int turn = 0;
		while(true) {
			if (turn%2 == 0) { // P1 turns
				playerMove(players[0]);
			}
			else { // P2 turns
				playerMove(players[1]);
			}
			turn++;
		}
		/*turn--;
		if (turn%2 == 0) {
			System.out.println("Player " + players[0].getName() + " won the game.");
		}
		else {
			System.out.println("Player " + players[1].getName() + " won the game.");
		}*/
	}
	
	private void playerMove(Player currentPlayer) {
		board.displayBoard();
		selectSquareAndMove(currentPlayer);
	}
	
	
	private void selectSquareAndMove(Player currentPlayer) {
		int square_number;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Player's " + currentPlayer.getName() + " turn.");
		do {
			System.out.print("Please enter Square number to select a piece: ");
			square_number = keyboard.nextInt();
			int row = square_number/10;
			int col = square_number%10;
			System.out.println("(" + row + ", " + col + ")");
			if ((row > 9 || row < 0) || (col > 9 || col < 0)) {
				System.out.println("Invalid row or column number. Please try again");
				square_number = -1;
			}
			else if (board.getBoard()[row][col].getPiece().getPieceType().equals(PieceType.BOMB) || 
					 board.getBoard()[row][col].getPiece().getPieceType().equals(PieceType.FLAG)) {
				System.out.println("Invalid Piece selected. You cannot move a " + 
					 board.getBoard()[row][col].getPiece().getPieceType());
				square_number = -1;
			}
			else if (board.lookAround(row,  col) && board.getBoard()[row][col].getPiece().getPieceColor().compareTo(currentPlayer.getTeamColor()) == 0) {
				selectDirection(keyboard, row, col);
			}
			else if (!board.lookAround(row, col)) {
				System.out.println("Invalid square or no valid square around, please try again");
				square_number = -1;
			}
			else {
				System.out.println("This piece doesn't belong to the current player. Please try again.");
				square_number = -1;
			}
		} while (square_number < 0 || square_number > 99);
	}
	
	
	private void selectDirection(Scanner keyboard, int row, int col) {
		String direction = "";
		boolean moveValid = false;
		do  {
			System.out.print("Please enter a Direction: Left, Right, Up, Down: ");
			direction = keyboard.next();
			moveValid = board.movePiece(row, col, direction);
			if(!moveValid) {
				direction = "";
			}
		} while (!isValidDirection(direction) && !moveValid);
	}
	
	
	private boolean isValidDirection(String direction) {
		return direction.equalsIgnoreCase("left") || direction.equalsIgnoreCase("right") || 
				direction.equalsIgnoreCase("up") || direction.equalsIgnoreCase("down") ||
				direction.equalsIgnoreCase("l") || direction.equalsIgnoreCase("r") || 
				direction.equalsIgnoreCase("u") || direction.equalsIgnoreCase("d");
	}
	
	/*
	private boolean isWon() {
		boolean blueWon = true;
		boolean redWon = true;
		Square[][] grid = board.getBoard();
		for(Square[] row: grid) {
			for(Square cell: row) {
				if(!cell.getType().equals(SquareType.WATER) && !cell.getType().equals(SquareType.GRASS)) {
					if (cell.getPiece().getPieceType().equals(PieceType.FLAG)) {
						if (cell.getPiece().getPieceColor().equals(Color.RED)) {
							blueWon = false;
						}
						else {
							redWon = false;
						}
					}
				}	
			}
		}
		if (!blueWon && !redWon) {
			return false;
		}
		return true;
	}
	*/
	
	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
}

