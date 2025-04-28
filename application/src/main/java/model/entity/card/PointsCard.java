package model.entity.card;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.enums.PointsCardType;
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("POINTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointsCard extends Card {
   private PointsCardType type;
   private int value = type.getPoints();
   public PointsCard(PointsCardType type) {this.type=type;}
}
