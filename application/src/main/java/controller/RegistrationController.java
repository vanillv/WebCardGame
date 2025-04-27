package controller;

import lombok.RequiredArgsConstructor;
import model.dto.request.UserRegistrationDto;
import model.dto.result.UserRegistrationResultDto;
import model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;
import service.RegistrationService;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserRegistrationResultDto> registerUser(
            @RequestBody UserRegistrationDto registrationDto
    ) {
        if (userRepository.existsByLogin(registrationDto.getLogin())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new UserRegistrationResultDto(
                            null,
                            registrationDto.getLogin(),
                            registrationDto.getName(),
                            "Login already exists"
                    ));
        }
        try {
            User user = registrationService.registerNewUser(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UserRegistrationResultDto(
                            user.getId(),
                            user.getLogin(),
                            user.getName(),
                            user.getSecretCode().getSecret()
                    ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new UserRegistrationResultDto(
                            null,
                            registrationDto.getLogin(),
                            registrationDto.getName(),
                            "Registration failed: " + e.getMessage()
                    ));
        }
    }
}
