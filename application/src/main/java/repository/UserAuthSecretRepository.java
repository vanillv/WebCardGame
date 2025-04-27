package repository;

import model.entity.UserAuthSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthSecretRepository extends JpaRepository<UserAuthSecret, Long> {
}
