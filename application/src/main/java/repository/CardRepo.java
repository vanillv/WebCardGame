package repository;

import model.card.Card;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface CardRepo extends JpaRepositoryImplementation<Card, Long> {
}
