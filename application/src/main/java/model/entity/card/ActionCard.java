package model.entity.card;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.ActionType;
@Entity
@DiscriminatorValue("ACTION")
@Data
@NoArgsConstructor
public class ActionCard extends Card {
    private ActionType type;
    private int value = type.getValue();
    public ActionCard(ActionType type) {
        this.type = type;
    }
}
