package model.card;

import model.entity.User;

public interface Card {
    String name = "";
    public int value = 0;
    default void action(User player) {}
}
