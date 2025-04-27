package model.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationResultDto {
    private Long userId;
    private String login;
    private String name;
    private String secret;
}
