package repository;

import model.entity.Turn;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends JpaRepositoryImplementation<Turn, Long> {
}
