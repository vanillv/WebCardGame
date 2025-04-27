package utils;

import model.entity.UserSessionInstance;
import model.enums.PlayerStatus;

public class ActionCardHandler {
    public void applyDouble(UserSessionInstance owner) {
        owner.setPoints(owner.getPoints() * 2);
    }
    public void applySlowEffect(UserSessionInstance target) {
        target.setStatus(PlayerStatus.Slowed);
        target.setStatusDuration(2);
    }
    public void applyBlock(UserSessionInstance target) {
        target.setStatus(PlayerStatus.Blocked);
        target.setStatusDuration(1);
    }
    public void applySwap(UserSessionInstance owner, UserSessionInstance target) {
        final int targetPoints = target.getPoints();
        target.setPoints(owner.getPoints());
        owner.setPoints(targetPoints);
    }
    public void applySteal(UserSessionInstance owner, UserSessionInstance target, int value) {
        if (target.getPoints() < value) {
            value = target.getPoints();
        }
        target.setPoints(target.getPoints() - value);
        owner.setPoints(owner.getPoints() + value);
    }
    public void applyDefense(UserSessionInstance owner) {
        owner.setStatus(PlayerStatus.Defended);
        owner.setStatusDuration(2);
    }
    //could do a single method with switch/case instead, but i wanted to split into different methods for readability
}
