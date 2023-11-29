package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
	
	private Board board;
	
	@BeforeEach
	public void init() {
		board = new Board();
	}

	@Test
	public void testBoardInitialisation() {
		assertEquals(board.getNumRows(), 10);
		assertEquals(board.getNumCols(), 10);
		assertEquals(board.getBoard()[0][0].getType(), SquareType.GRASS);
		assertEquals(board.getBoard()[5][5].getType(), SquareType.GRASS);
		assertEquals(board.getBoard()[4][2].getType(), SquareType.WATER);
		assertEquals(board.getBoard()[5][6].getType(), SquareType.WATER);
	}
	
	@Test
	public void testBoardPiecePlacement() {
		Piece p = new Piece(PieceType.SPY, Color.RED);
		board.placePiece(0, 0, p);
		assertEquals(board.getBoard()[0][0].getPiece().getPieceType(), PieceType.SPY);
	}
}
