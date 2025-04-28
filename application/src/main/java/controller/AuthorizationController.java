package controller;

import lombok.RequiredArgsConstructor;
import model.dto.request.UserAuthRequestDto;
import model.dto.request.UserSecretAuthRequest;
import model.dto.result.UserAuthResultDto;
import model.entity.UserAuthSecret;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.UserAuthSecretRepository;
import repository.UserRepository;
import service.AuthorizationService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authService;
    private final UserRepository userRepository;
    private final UserAuthSecretRepository authSecretRepo;
    @PostMapping("/login")
    public ResponseEntity<UserAuthResultDto> login(
            @RequestBody UserAuthRequestDto dto
    ) {
        try {
            UserAuthSecret secret = authService.authorizeWithPassword(
                    dto.getLogin(),
                    dto.getPassword()
            );
            return ResponseEntity.ok(mapToAuthDto(secret));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new UserAuthResultDto("Error", (long) 0,"", LocalDateTime.now()));
        }
    }
    @PostMapping("/login/secret")
    public ResponseEntity<UserAuthResultDto> loginWithSecret(
            @RequestBody UserSecretAuthRequest dto
    ) {
        try {
            UserAuthSecret secret = authService.authorizeWithSecret(dto.getSecret());
            return ResponseEntity.ok(mapToAuthDto(secret));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new UserAuthResultDto("Error", (long) 0,"", LocalDateTime.now()));
        }
    }
    private UserAuthResultDto mapToAuthDto(UserAuthSecret secret) {
        return new UserAuthResultDto(
                secret.getSecret(),
                secret.getUser().getId(),
                secret.getUser().getName(),
                LocalDateTime.now()
        );
    }
}
