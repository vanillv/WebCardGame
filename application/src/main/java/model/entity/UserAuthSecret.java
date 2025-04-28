package model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table
@NoArgsConstructor
public class UserAuthSecret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private String secret;
    @Column(name = "secret_creation_time", nullable = false)
    private LocalDateTime secretCreationTime;
    public UserAuthSecret(User user, String code) {
        this.user = user;
        secret = code;
        secretCreationTime = LocalDateTime.now();
    }
}
