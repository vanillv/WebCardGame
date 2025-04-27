package model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationDto {
    private String name;
    private String login;
    private String password;
}
