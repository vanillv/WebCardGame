package model.enums;

public enum ActionType {
    Block("Player skips the turn"),
    Steal("Steals points"),
    DoubleDown("Doubles the points"),
    Slow("Player can't use action cards"),
    Search("Player can choose a card from the deck");
    String description;
    String playerName;
    ActionType(String action) {
        description = action;
    }
}
