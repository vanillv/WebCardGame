package repository;

import model.entity.UserAuthSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthSecretRepository extends JpaRepository<UserAuthSecret, Long> {
    Optional<UserAuthSecret> findBySecret(String secret);
}
