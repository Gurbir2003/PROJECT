package model;

import java.util.HashMap;
import java.util.Map;

public class Player {
	
	private String name;
	private Map<PieceType, Integer> pieceSet;
	private Color teamColor;
	
	public Player(String name, Color tc) {
		this.name = name;
		this.teamColor = tc;
		this.pieceSet = new HashMap<PieceType, Integer>();
		this.initializePieceSet();
	}
	
	private void initializePieceSet() {
		this.pieceSet.put(PieceType.FLAG, 1);
		this.pieceSet.put(PieceType.MARSHAL, 1);
		this.pieceSet.put(PieceType.GENERAL, 1);
		this.pieceSet.put(PieceType.COLONEL, 2);
		this.pieceSet.put(PieceType.MAJOR, 3);
		this.pieceSet.put(PieceType.CAPTAIN, 4);
		this.pieceSet.put(PieceType.LIEUTENANT, 4);
		this.pieceSet.put(PieceType.SERGEANT, 4);
		this.pieceSet.put(PieceType.MINER, 5);
		this.pieceSet.put(PieceType.SCOUT, 8);
		this.pieceSet.put(PieceType.SPY, 1);
		this.pieceSet.put(PieceType.BOMB, 6);
	}

	/**
	 * @return the pieceSet
	 */
	public Map<PieceType, Integer> getPieceSet() {
		return pieceSet;
	}

	/**
	 * @return the teamColor
	 */
	public Color getTeamColor() {
		return teamColor;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
