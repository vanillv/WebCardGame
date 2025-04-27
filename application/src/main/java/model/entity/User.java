package model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Table
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private long sessionsWon = 0;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserAuthSecret secretCode;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSessionInstance> sessionInstances;

}
