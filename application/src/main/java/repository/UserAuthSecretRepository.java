package repository;

import model.entity.UserAuthSecret;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthSecretRepository extends JpaRepositoryImplementation<UserAuthSecret, Long> {
    Optional<UserAuthSecret> findBySecret(String secret);
}
