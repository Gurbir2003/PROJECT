package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PieceTypeTest {

	// Test for checking and verifying that the names of PieceTypes match their expected values

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

	// Test for checking the rank of ePieceTypes match their expected values
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
	
	// Test for checking the result of the Bomb PieceType against another PieceType 
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

	// Test for checking the result of the Marshal PieceType against another PieceType 
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
	
	// Test for checking the result of the General PieceType against another PieceType 
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
	
	// Test for checking the result of the Colonel PieceType against another PieceType 
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
	
	// Test for checking the result of the Major PieceType against another PieceType 
	@Test
	public void testPieceFightRankMajor() {
		assertTrue(PieceType.MAJOR.compareWith(PieceType.MINER) == 1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.CAPTAIN) == 1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.COLONEL) == -1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.GENERAL) == -1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.LIEUTENANT) == 1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.MAJOR) == 0);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.SCOUT) == 1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.SERGEANT) == 1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.SPY) == 1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.MARSHAL) == -1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.BOMB) == -1);
 
	}
	
	// Test for checking the result of the Captain PieceType against another PieceType 
	@Test
	public void testPieceFightRankCaptain() {
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.MINER) == 1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.CAPTAIN) == 0);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.COLONEL) == -1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.GENERAL) == -1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.LIEUTENANT) == 1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.MAJOR) == -1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.SCOUT) == 1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.SERGEANT) == 1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.SPY) == 1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.MARSHAL) == -1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.BOMB) == -1);
 
	}
	
	// Test for checking the result of the Lieutenant PieceType against another PieceType 
	@Test
	public void testPieceFightRankLieutenant() {
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.MINER) == 1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.CAPTAIN) == -1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.COLONEL) == -1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.GENERAL) == -1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.LIEUTENANT) == 0);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.MAJOR) == -1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.SCOUT) == 1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.SERGEANT) == 1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.SPY) == 1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.MARSHAL) == -1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.BOMB) == -1);
 
	}
	
	// Test for checking the result of the Sergeant PieceType against another PieceType 
	@Test
	public void testPieceFightRankSergeant() {
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.MINER) == 1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.CAPTAIN) == -1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.COLONEL) == -1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.GENERAL) == -1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.LIEUTENANT) == -1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.MAJOR) == -1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.SCOUT) == 1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.SERGEANT) == 0);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.SPY) == 1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.MARSHAL) == -1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.BOMB) == -1);
 
	}
	
	// Test for checking the result of the Miner PieceType against another PieceType 
	@Test
	public void testPieceFightRankMiner() {
		assertTrue(PieceType.MINER.compareWith(PieceType.MINER) == 0);
		assertTrue(PieceType.MINER.compareWith(PieceType.CAPTAIN) == -1);
		assertTrue(PieceType.MINER.compareWith(PieceType.COLONEL) == -1);
		assertTrue(PieceType.MINER.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.MINER.compareWith(PieceType.GENERAL) == -1);
		assertTrue(PieceType.MINER.compareWith(PieceType.LIEUTENANT) == -1);
		assertTrue(PieceType.MINER.compareWith(PieceType.MAJOR) == -1);
		assertTrue(PieceType.MINER.compareWith(PieceType.SCOUT) == 1);
		assertTrue(PieceType.MINER.compareWith(PieceType.SERGEANT) == -1);
		assertTrue(PieceType.MINER.compareWith(PieceType.SPY) == 1);
		assertTrue(PieceType.MINER.compareWith(PieceType.MARSHAL) == -1);
		assertTrue(PieceType.MINER.compareWith(PieceType.BOMB) == 1);
 
	}
	
	// Test for checking the result of the Scout PieceType against another PieceType 
	@Test
	public void testPieceFightRankScout() {
		assertTrue(PieceType.SCOUT.compareWith(PieceType.MINER) == -1);
		assertTrue(PieceType.SCOUT.compareWith(PieceType.CAPTAIN) == -1);
		assertTrue(PieceType.SCOUT.compareWith(PieceType.COLONEL) == -1);
		assertTrue(PieceType.SCOUT.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.SCOUT.compareWith(PieceType.GENERAL) == -1);
		assertTrue(PieceType.SCOUT.compareWith(PieceType.LIEUTENANT) == -1);
		assertTrue(PieceType.SCOUT.compareWith(PieceType.MAJOR) == -1);
		assertTrue(PieceType.SCOUT.compareWith(PieceType.SCOUT) == 0);
		assertTrue(PieceType.SCOUT.compareWith(PieceType.SERGEANT) == -1);
		assertTrue(PieceType.SCOUT.compareWith(PieceType.SPY) == 1);
		assertTrue(PieceType.SCOUT.compareWith(PieceType.MARSHAL) == -1);
		assertTrue(PieceType.SCOUT.compareWith(PieceType.BOMB) == -1);
 
	}
	
	// Test for checking the result of the Spy PieceType against another PieceType 
	@Test
	public void testPieceFightRankSpy() {
		assertTrue(PieceType.SPY.compareWith(PieceType.MINER) == -1);
		assertTrue(PieceType.SPY.compareWith(PieceType.CAPTAIN) == -1);
		assertTrue(PieceType.SPY.compareWith(PieceType.COLONEL) == -1);
		assertTrue(PieceType.SPY.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.SPY.compareWith(PieceType.GENERAL) == -1);
		assertTrue(PieceType.SPY.compareWith(PieceType.LIEUTENANT) == -1);
		assertTrue(PieceType.SPY.compareWith(PieceType.MAJOR) == -1);
		assertTrue(PieceType.SPY.compareWith(PieceType.SCOUT) == -1);
		assertTrue(PieceType.SPY.compareWith(PieceType.SERGEANT) == -1);
		assertTrue(PieceType.SPY.compareWith(PieceType.SPY) == 0);
		assertTrue(PieceType.SPY.compareWith(PieceType.MARSHAL) == 1);
		assertTrue(PieceType.SPY.compareWith(PieceType.BOMB) == -1);
 
	}
	
	// Test for checking the result of the Flag PieceType against another PieceType
	@Test
	public void testPieceFightRankFlag() {
		assertTrue(PieceType.MINER.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.SPY.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.SERGEANT.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.LIEUTENANT.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.CAPTAIN.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.MAJOR.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.COLONEL.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.GENERAL.compareWith(PieceType.FLAG) == 1);
		assertTrue(PieceType.MARSHAL.compareWith(PieceType.FLAG) == 1);
	}
}
