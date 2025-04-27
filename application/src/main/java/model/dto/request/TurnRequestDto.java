package model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TurnRequestDto {
    long sessionId;
    long cardOwnerId;
    long targetId;
    int value;
}
