package model.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.enums.SessionStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class SessionInfoResultDto {
    private Long sessionId;
    private Long currentPlayerId;
    private List<PlayerInfoDto> players;
    private int remainingCards;
    private SessionStatus status;
}
