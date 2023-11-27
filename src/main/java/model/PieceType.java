package model;

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
}
