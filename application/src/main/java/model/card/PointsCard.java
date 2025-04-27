package model.card;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.enums.PointsCardType;
@Entity
@DiscriminatorValue("POINTS")
@Data
@AllArgsConstructor
public class PointsCard extends Card {
   private PointsCardType type;
   private int value = type.getPoints();
   public PointsCard(PointsCardType type) {this.type=type;}
}
