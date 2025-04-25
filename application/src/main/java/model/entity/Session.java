package model.entity;

import jakarta.persistence.Entity;
import model.card.Card;
import model.enums.SessionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
@Entity
public class Session {
    private long sessionId;
    private List<Card> deck = new ArrayList<>();
    private List<User> playerList = new ArrayList<>();
    private List<Turn> turns = new ArrayList<>();
    private SessionStatus status;
    private String turnArchive;

    public Session() {
        sessionId = RandomGenerator.getDefault().nextInt(100000, 10000000);
    }
    public boolean addPlayer(User player) {
        if (playerList.size() < 4) return false;
        playerList.add(player);
        return true;
    }
    public void deletePlayer(User player) {
        playerList.remove(player);
    }
    public String getPlayerList() {
        String result = "players:";
        for (User u : playerList) {
            result = result + u.getName() + ";";
        }
        return result;
    }
    public void newTurn(User player, Card card) {

        turns.add(new Turn(player, card));
    }
}
