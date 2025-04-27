package model.dto;

import lombok.Data;

@Data
public class ActionCardUseDto {
    long sessionId;
    long cardOwnerId;
    long targetId;
    int value;
}
