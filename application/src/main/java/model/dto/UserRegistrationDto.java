package model.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String name;
    private String login;
    private String password;
}
