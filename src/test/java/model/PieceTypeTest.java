package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	
	@Test
	public void testPieceFightRankBomb() {
		//this.compareWITH(other )
		assertTrue(PieceType.MINER.compareWith(PieceType.BOMB) == 1);
		assertTrue(PieceType.SPY.compareWith(PieceType.BOMB) == -1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.BOMB) == -1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.BOMB) == -1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.BOMB) == -1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.BOMB) == -1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.BOMB) == -1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.BOMB) == -1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.BOMB) == -1);
		assertTrue(PieceType.BOMB.compareWith(PieceType.BOMB) == -1);
	}

	@Test
	public void testPieceFightRankMarshal() {
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.MINER) == 1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.CAPTAIN) == 1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.COLONEL) == 1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.GENERAL) == 1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.LIEUTENANT) == 1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.MAJOR) == 1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.SCOUT) == 1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.SERGEANT) == 1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.SPY) == 1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.MARSHAL) == 0);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.BOMB) == -1);
 
	}
	
	@Test
	public void testPieceFightRankGeneral() {
		assertTrue(PieceType.GENERAL.compareWith(PieceType.MINER) == 1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.CAPTAIN) == 1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.COLONEL) == 1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.GENERAL) == 0);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.LIEUTENANT) == 1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.MAJOR) == 1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.SCOUT) == 1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.SERGEANT) == 1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.SPY) == 1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.MARSHAL) == -1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.BOMB) == -1);
 
	}
	
	@Test
	public void testPieceFightRankColonel() {
		assertTrue(PieceType.COLONEL.compareWith(PieceType.MINER) == 1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.CAPTAIN) == 1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.COLONEL) == 0);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.GENERAL) == -1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.LIEUTENANT) == 1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.MAJOR) == 1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.SCOUT) == 1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.SERGEANT) == 1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.SPY) == 1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.MARSHAL) == -1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.BOMB) == -1);
 
	}
}
