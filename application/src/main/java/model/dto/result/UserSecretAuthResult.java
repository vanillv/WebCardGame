package model.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserSecretAuthResult {
    private String newSecret;
    private Long userId;
    private String userName;
    private LocalDateTime newSecretExpiration;
}
