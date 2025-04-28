package model.entity;

import jakarta.persistence.*;
import lombok.Data;
import model.entity.card.Card;
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
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Card> deck = new ArrayList<>();
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSessionInstance> playerList;
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("timestamp ASC")
    private List<Turn> turns = new ArrayList<>();

    private List<UserSessionInstance> playerTurnOrder;
    private int currPlayerIndex = 0;
    @Column
    private SessionStatus status;
    @Column
    private boolean available;
    private int cardOrder = 63;
    public Session() {
        id = RandomGenerator.getDefault().nextInt(100000, 10000000);
        status = SessionStatus.WaitForPlayers;
        playerList = new ArrayList<>();
        turns = new ArrayList<>();
        available = true;
    }
    public boolean addPlayer(User player) {
        if (!(playerList.size() < 4) || !available) return false;
        UserSessionInstance playerInstance = new UserSessionInstance(player, player.getName(), this, PlayerStatus.Waiting);
        playerList.add(playerInstance);
        return true;
    }
    public void deletePlayer(User player) {
        playerList.removeIf(u -> u.getUser().equals(player));
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
    public void nextTurn(UserSessionInstance currPlayer) {
       currPlayerIndex = (currPlayerIndex + 1) % playerTurnOrder.size();
       int statusDuration = currPlayer.getStatusDuration();
       if (statusDuration > 0) {
           currPlayer.setStatusDuration(statusDuration - 1);
       } else {
           currPlayer.setStatus(PlayerStatus.Playing);
       }
    }
    public UserSessionInstance getCurrentPlayer() {
        return playerTurnOrder.get(currPlayerIndex);
    }
    public Turn processPlayerTurn(Long playerId) {
        UserSessionInstance player = getCurrentPlayer();
        if (!player.getUser().getId().equals(playerId)) return null;
        Card card = drawCard();
        Turn turn = new Turn(player, card);
        turns.add(turn);
        nextTurn(player);
        return turn;
    }
}
