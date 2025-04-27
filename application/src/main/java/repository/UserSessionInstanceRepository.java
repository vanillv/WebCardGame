package repository;

import model.entity.UserSessionInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionInstanceRepository extends JpaRepository<UserSessionInstance, Long> {

}
