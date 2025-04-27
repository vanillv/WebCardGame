package model.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserAuthResultDto {
    private String authSecret;
    private Long userId;
    private String userName;
    private LocalDateTime secretExpiration;
}
