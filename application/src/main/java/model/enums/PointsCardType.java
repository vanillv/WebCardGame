package model.enums;

public enum PointsCardType {
    Low(1),
    Medium(3),
    High(5);
    int points = 0;
    PointsCardType(int value) {
        points = value;
    }
    public int getPoints() {
        return points;
    }
}
