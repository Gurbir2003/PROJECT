package model;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
		
	}
	
	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
}
