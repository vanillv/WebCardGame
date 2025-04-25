package model.entity;

import jakarta.persistence.Entity;

import java.util.List;
@Entity
public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    private int id;
    private String name;
    private String login;
    private String password;
    private int points;
    private List<Session> sessionList;
    private long sessionsWon;
}
