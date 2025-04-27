package model.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerInstanceResultDto {
    private boolean success;
    private String message;
    private Long playerId;
}
