package model.enums;

public enum ActionType {
    Block("Player skips the turn", 1 ),
    Steal("Steals 3 points", 3),
    Swap("Swaps points of the user and target", 5),
    Slow("Player can't use action cards for 2 turns", 0),
    DoubleDown("Doubles the points", 2),
    Defend("player ignores effect of any negative action for 2 turns", 10);
    private String description;
    private int value;
    ActionType(String action, int val) {
        description = action; value = val;
    }
    public int getValue() {
        return value;
    }
    public String getDescription() {
        return description;
    }
}
