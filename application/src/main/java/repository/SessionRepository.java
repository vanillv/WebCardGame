package repository;

import model.entity.Session;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepositoryImplementation<Session, Long> {
}
