package model.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionOperationResultDto {
    private boolean success;
    private String message;
    private Long sessionId;
}
