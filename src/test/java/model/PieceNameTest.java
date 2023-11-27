package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PieceNameTest {
	
	@Test
	public void testPieceNames() {
		assertEquals("Flag", PieceName.FLAG.getName());
		assertEquals("Bomb", PieceName.BOMB.getName());
		assertEquals("Spy", PieceName.SPY.getName());
		assertEquals("Scout", PieceName.SCOUT.getName());
		assertEquals("Miner", PieceName.MINER.getName());
		assertEquals("Sergeant", PieceName.SERGEANT.getName());
		assertEquals("Lieutenant", PieceName.LIEUTENANT.getName());
		assertEquals("Captain", PieceName.CAPTAIN.getName());
		assertEquals("Major", PieceName.MAJOR.getName());
		assertEquals("Colonel", PieceName.COLONEL.getName());
		assertEquals("General", PieceName.GENERAL.getName());
		assertEquals("Marshal", PieceName.MARSHAL.getName());
	}

}
