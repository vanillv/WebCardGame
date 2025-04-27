package repository;

import model.entity.UserSessionInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionInstanceRepository extends JpaRepository<UserSessionInstance, Long> {
    Optional<UserSessionInstance> findByUser_Id(Long userId);
}
