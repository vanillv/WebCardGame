package utils;

import model.entity.card.ActionCard;
import model.entity.card.Card;
import model.entity.card.PointsCard;
import model.entity.Session;
import model.enums.ActionType;
import model.enums.PointsCardType;

import java.util.*;

public class DeckCreator {
    private Random rnd = new Random();
    public void fillAndShuffleDeck(Session session) {
        List<Card> deck = createCards();
        Collections.shuffle(deck);
        session.setDeck(deck);
    }
    private List<Card> createCards() {
        int number1Cards = 16;
        int number3Cards = 13;
        int number5Cards = 7;
        int actionCards = 26;
        int rareCardAmount = 7;
        List<Card> deck = new ArrayList<>();
        int i = 0; int limit = 62; int stack = 0; int currLimit = number1Cards;
        for (; i <= limit; i++) {
            switch (stack) {
                case 0 ->{deck.add(new PointsCard(PointsCardType.Low));
                    if (i==currLimit) stack++; currLimit = currLimit + number3Cards;}
                case 1 ->{deck.add(new PointsCard(PointsCardType.Medium));
                    if (i==currLimit) stack++; currLimit = currLimit + number5Cards;}
                case 2 -> {deck.add(new PointsCard(PointsCardType.High));
                    if (i==currLimit) stack++; currLimit = currLimit + actionCards;}
                case 3 -> {
                    ActionCard card = makeCard(rareCardAmount > 0); rareCardAmount--;
                    deck.add(card);
                }
            }
        }
        return deck;
    };
    private ActionCard makeCard(boolean rare) {
        if (rare) {
            boolean DoubleOrSearch = rnd.nextBoolean();
            if (DoubleOrSearch) {
                return new ActionCard(ActionType.Swap);
            }
            return new ActionCard(ActionType.Defend);
        }
        int num = rnd.nextInt(0, 5);
        return new ActionCard(Arrays.stream(ActionType.values()).toList().get(num));
    }
}
