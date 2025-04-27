package model.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.enums.PlayerStatus;

@Data
@AllArgsConstructor
public class PlayerInfoDto {
    private Long userId;
    private String username;
    private int points;
    private PlayerStatus status;
}