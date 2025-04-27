package model.entity;

import jakarta.persistence.*;
import lombok.Data;
import model.card.Card;
import model.enums.TurnStatus;

import java.time.LocalDateTime;

@Entity
@Data
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private UserSessionInstance player;

    @ManyToOne
    @JoinColumn(name = "target_player_id")
    private UserSessionInstance target;

    @Enumerated(EnumType.STRING)
    private TurnStatus status;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private LocalDateTime timestamp = LocalDateTime.now();

    public boolean isWinner() {
        return player.getPoints() >= 30;
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
        this.card = card;
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
