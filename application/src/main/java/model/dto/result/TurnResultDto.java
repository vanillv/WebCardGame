package model.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class TurnResultDto {
    private boolean success;
    private String message;
    private Long nextPlayerId;
    private Map<Long, Integer> playerPoints;
}

