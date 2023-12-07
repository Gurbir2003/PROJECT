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
	
	private Player[] players;
	private Board board;
	
	public Game() {
		players = new Player[2];
		board = new Board();
		createPlayers();
		setUpBoardPieces();
	}
	
	private void createPlayers() {
		// Replace following code with asking player name;
		Player p1 = new Player("Player 1", Color.RED);
		Player p2 = new Player("Player 2", Color.BLUE);
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
		int turn = 1;
		Scanner keyboard = new Scanner(System.in);
		int start_square_number = -1;
		String direction = "";
		boolean moveValid = false;
		while(true) {
			start_square_number = -1;
			direction = "";
			moveValid = false;
			board.displayPhysicalBoard();
			while (start_square_number < 0 || start_square_number > 99) {
				System.out.print("Please enter Square number to select a piece: ");
				start_square_number = keyboard.nextInt();
				int row = start_square_number/10;
				int col = start_square_number%10;
				if (board.hasFreeSquare(row, col)) {
					while ((direction != "Left" || direction != "Right" ||
							direction != "Up" || direction != "Down") && !moveValid) {
						System.out.print("Please enter a Direction: Left, Right, Up, Down: ");
						direction = keyboard.next();
						moveValid = board.movePiece(row, col, direction);
						if(moveValid) {
							System.out.println("Move played successfully");
						}
						else {
							System.out.println("Destination square is not valid, please try again");
							direction = "";
						}
					}
				}
				else {
					System.out.println("No valid square around, please try again");
					start_square_number = -1;
				}
			}
		}
	}
	
	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
}
