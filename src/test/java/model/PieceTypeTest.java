package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PieceTypeTest {
	
	@Test
	public void testPieceNames() {
		assertEquals("Bomb", PieceType.BOMB.getName());
		assertEquals("Spy", PieceType.SPY.getName());
		assertEquals("Scout", PieceType.SCOUT.getName());
		assertEquals("Miner", PieceType.MINER.getName());
		assertEquals("Sergeant", PieceType.SERGEANT.getName());
		assertEquals("Lieutenant", PieceType.LIEUTENANT.getName());
		assertEquals("Captain", PieceType.CAPTAIN.getName());
		assertEquals("Major", PieceType.MAJOR.getName());
		assertEquals("Colonel", PieceType.COLONEL.getName());
		assertEquals("General", PieceType.GENERAL.getName());
		assertEquals("Marshal", PieceType.MARSHAL.getName());
		assertEquals("Flag", PieceType.FLAG.getName());
	}
	
	@Test
	public void testPieceRank() {
		assertEquals(0, PieceType.BOMB.getRank());
		assertEquals(1, PieceType.SPY.getRank());
		assertEquals(2, PieceType.SCOUT.getRank());
		assertEquals(3, PieceType.MINER.getRank());
		assertEquals(4, PieceType.SERGEANT.getRank());
		assertEquals(5, PieceType.LIEUTENANT.getRank());
		assertEquals(6, PieceType.CAPTAIN.getRank());
		assertEquals(7, PieceType.MAJOR.getRank());
		assertEquals(8, PieceType.COLONEL.getRank());
		assertEquals(9, PieceType.GENERAL.getRank());
		assertEquals(10, PieceType.MARSHAL.getRank());
		assertEquals(11, PieceType.FLAG.getRank());
	}

}
