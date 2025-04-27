package model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerInstanceRequestDto {
    private Long userId;
    private Long sessionId;
}
