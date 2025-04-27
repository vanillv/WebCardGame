package model.entity;

import jakarta.persistence.*;
import lombok.Data;
import model.Turn;
import model.card.Card;
import model.card.PointsCard;
import model.enums.PlayerStatus;
import model.enums.SessionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
@Entity
@Table
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sessionId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Card> deck = new ArrayList<>();
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSessionInstance> playerList;
    private List<Turn> turns;
    private List<UserSessionInstance> playerTurnOrder;
    private int currPlayerIndex = 0;
    @Column
    private SessionStatus status;
    @Column
    private String turnArchive;
    @Column
    private boolean available;
    private int cardOrder = 63;
    public Session() {
        sessionId = RandomGenerator.getDefault().nextInt(100000, 10000000);
        status = SessionStatus.WaitForPlayers;
        playerList = new ArrayList<>();
        turns = new ArrayList<>();
        available = true;
    }
    public boolean addPlayer(User player) {
        if (playerList.size() < 4 || !available) return false;
        UserSessionInstance playerInstance = new UserSessionInstance(player, player.getName(), this, 0, PlayerStatus.Waiting, 0);
        playerList.add(playerInstance);
        return true;
    }
    public void deletePlayer(User player) {
        playerList.removeIf(u -> u.getUser().equals(player));
    }
    public Card addTurn(long playerId) {
        if (playerTurnOrder.get(currPlayerIndex).getUser().getId() == playerId) {
            UserSessionInstance currPlayer = playerTurnOrder.get(currPlayerIndex);
            switch (playerTurnOrder.get(currPlayerIndex).getStatus()) {
                case Waiting -> {return null;}
                case Slowed -> {
                    Card card = drawCard();
                    if (card instanceof PointsCard) {
                        turns.add(new Turn(currPlayer, card));
                        nextTurn();
                        return card;
                    };
                }
                case Blocked ->{
                    turns.add(new Turn(currPlayer));
                    nextTurn();
                    currPlayer.setStatus(PlayerStatus.Playing);
                    return null;
                }
                default -> {
                    Card card = drawCard();
                    turns.add(new Turn(currPlayer, card));
                    nextTurn();
                    return card;
                }
            }

        }
        return null;
    }
    public boolean startGame(long hostId) {
        if (playerList.size() > 1 && available && playerList.getFirst().getUser().getId() != hostId) {
            status = SessionStatus.InProgress;
            available = false;
            for (UserSessionInstance player : playerList) {
                player.setStatus(PlayerStatus.Playing);
            }
            return true;
        }
        return false;
    }
    private Card drawCard(){
        if (cardOrder != 0) {
            Card card = deck.reversed().get(cardOrder);
            cardOrder--;
            return card;
        }
        gameEnd(playerList.get(0).getUser().getId(), false);
        return null;
    }
    public boolean gameEnd(long hostId, boolean winnerFound) {
        if ((status != SessionStatus.Finished &&
                playerList.getFirst().getUser().getId() == hostId) || winnerFound) {
            status = SessionStatus.Finished;
            playerList.clear();
            return true;
        }
        return false;
    }
    public void nextTurn() {
       currPlayerIndex = (currPlayerIndex + 1) % playerTurnOrder.size();
    }
    public String getPlayerList() {
        String result = "players:";
        for (UserSessionInstance p : playerList) {
            result = result + p.getName() + ",";
        }
        return result + ";";
    }
}
