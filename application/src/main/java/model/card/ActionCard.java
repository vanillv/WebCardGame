package model.card;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import model.enums.ActionType;
@Entity
@DiscriminatorValue("ACTION")
@Data
public class ActionCard extends Card {
    private ActionType type;
    private int value = type.getValue();
    public ActionCard(ActionType type) {
        this.type = type;
    }
}
