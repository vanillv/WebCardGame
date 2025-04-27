package repository;

import model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User, Long> {
    @Query("SELECT u FROM User u WHERE u.login = :login")
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
}
