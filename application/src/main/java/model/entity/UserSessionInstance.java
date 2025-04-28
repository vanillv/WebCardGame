package model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.PlayerStatus;
@Entity
@Table
@Data
@NoArgsConstructor
public class UserSessionInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
    private int points = 0;
    @Enumerated(EnumType.STRING)
    private PlayerStatus status;
    private int statusDuration = 0;
    public UserSessionInstance(User user, String name, Session session, PlayerStatus status) {
        this.user = user;
        this.name = name;
        this.session = session;
        this.status = status;
    }
}
