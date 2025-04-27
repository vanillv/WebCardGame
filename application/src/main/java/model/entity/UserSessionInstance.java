package model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.enums.PlayerStatus;
@Entity
@Table
@Data
@AllArgsConstructor
public class UserSessionInstance {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private String name;
    @Column
    private Session session;
    private int points;
    private PlayerStatus status;
    private int statusDuration = 0;
}
