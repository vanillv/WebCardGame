package model;

import model.card.Card;
import model.entity.UserSessionInstance;
import model.enums.TurnStatus;

public class Turn {
    private UserSessionInstance player;
    private UserSessionInstance target;
    private Card action;
    private TurnStatus status;
    private boolean isWinner() {
        int pointsToWin = 30;
        return player.getPoints() >= pointsToWin;
    }
    private void checkWinCondition() {
        if (isWinner()) {
            player.getUser().setSessionsWon(player.getUser().getSessionsWon() + 1);
            endTurn();
            player.getSession().gameEnd(0, true);
        }
    }
    public Turn(UserSessionInstance player, Card card) {
        this.player = player;
        target = player;
        action = card;
        status = TurnStatus.Started;
    }
    public Turn(UserSessionInstance player) {
        this.player = player;
        status = TurnStatus.Skipped;
    }
    public void cardActivate(UserSessionInstance target) {
        this.target = target;
        checkWinCondition();
        endTurn();
    }
    public void endTurn() {
        status = TurnStatus.Finished;
    }
}
