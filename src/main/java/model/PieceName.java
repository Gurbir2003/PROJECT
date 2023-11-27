package model;

public enum PieceName {
    FLAG("Flag"),
    BOMB("Bomb"),
    SPY("Spy"),
    SCOUT("Scout"),
    MINER("Miner"),
    SERGEANT("Sergeant"),
    LIEUTENANT("Lieutenant"),
    CAPTAIN("Captain"),
    MAJOR("Major"),
    COLONEL("Colonel"),
    GENERAL("General"),
    MARSHAL("Marshal");

    private final String displayName;

    PieceName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
