package model;
/**
 * The pieceType class is an enum type class and It is used to represent the type of piece on the board i.e. whether a piece is a bomb, spy, scout..etc. 
 * @return the name,rank of the piece
 */
public enum PieceType {
	
    BOMB("Bomb", 0),
    SPY("Spy", 1),
    SCOUT("Scout", 2),
    MINER("Miner", 3),
    SERGEANT("Sergeant", 4),
    LIEUTENANT("Lieutenant", 5),
    CAPTAIN("Captain", 6),
    MAJOR("Major", 7),
    COLONEL("Colonel", 8),
    GENERAL("General", 9),
    MARSHAL("Marshal", 10),
	FLAG("Flag", 11);

    private final String name;
    private final int rank;

    PieceType(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }
    public int getRank() {
        return rank;
    }
    
    // -1 means loses
    // 1 means wins
    // 0 means draw both pieces die/ removed from the game
    public int compareWith(PieceType other) {
    	if(other == FLAG) {
    		return 1;
    	}
    	if(other == BOMB) {
    		if (this == MINER) {
    			return 1;
    		}
    		else {
    			return -1;
    		}
    	}
    	
    	if(this == MARSHAL) {
    		if (other == MARSHAL) {
    			return 0;
    		}
    		else {
    			return 1;	
    		}
    	}
    	
    	if(this == SPY) {
    		if (other == MARSHAL ) {
    			return 1;
    		}
    	}
  
    	if (this.rank>other.rank) {
    		return 1;
    	}
    	else if (this.rank == other.rank) {
    		return 0;
    	}
    	return -1;
    }
}
