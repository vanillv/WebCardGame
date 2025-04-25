package model.entity;

import model.card.Card;
import model.enums.TurnStatus;

public class Turn {
    private User player;
    private Card action;
    private TurnStatus status;
    private boolean checkWinCondition() {
        int pointsToWin = 30;
        return player.getPoints() >= pointsToWin;
    }
    public Turn(User player, Card card) {
        this.player = player;
        action = card;
        status = TurnStatus.Started;
    }
    public Turn(User player) {
        this.player = player;
        status = TurnStatus.Skipped;
    }
    public void endTurn() {
        status = TurnStatus.Finished;
    }
}
