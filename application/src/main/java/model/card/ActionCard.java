package model.card;

import model.entity.User;
import model.enums.ActionType;

public class ActionCard implements Card {
    public ActionType type;
    public int value;
    @Override
    public void action(User target) {
        switch (type) {
            case Block -> value = 1;
            case Slow -> value = 0;
        }
        //Card.super.action(player);
    }
}
