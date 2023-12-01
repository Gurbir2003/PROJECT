package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
	
	Player p;
	
	@BeforeEach
	public void init() {
		p = new Player("Player 1", Color.RED);
	}
	
	@Test
	public void testPieceSetCreation() {
		assertEquals(p.getPieceSet().get(PieceType.FLAG), 1);
		assertEquals(p.getPieceSet().get(PieceType.MARSHAL), 1);
		assertEquals(p.getPieceSet().get(PieceType.GENERAL), 1);
		assertEquals(p.getPieceSet().get(PieceType.COLONEL), 2);
		assertEquals(p.getPieceSet().get(PieceType.MAJOR), 3);
		assertEquals(p.getPieceSet().get(PieceType.CAPTAIN), 4);
		assertEquals(p.getPieceSet().get(PieceType.LIEUTENANT), 4);
		assertEquals(p.getPieceSet().get(PieceType.SERGEANT), 4);
		assertEquals(p.getPieceSet().get(PieceType.MINER), 5);
		assertEquals(p.getPieceSet().get(PieceType.SCOUT), 8);
		assertEquals(p.getPieceSet().get(PieceType.SPY), 1);
		assertEquals(p.getPieceSet().get(PieceType.BOMB), 6);
	}

}
