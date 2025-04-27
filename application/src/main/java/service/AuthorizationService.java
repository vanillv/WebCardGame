package service;

import lombok.AllArgsConstructor;
import model.entity.User;
import model.entity.UserAuthSecret;
import org.springframework.stereotype.Service;
import repository.UserAuthSecretRepository;
import repository.UserRepository;
import utils.AuthSecretProvider;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthorizationService {
    UserRepository userRepo;
    UserAuthSecretRepository authSecretRepo;
    AuthSecretProvider authSecretProvider;
    public UserAuthSecret authorizeWithPassword(String login, String password) {
        User user = userRepo.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getPassword().matches(password)) {
            return updateSecret(user);
        } else throw new RuntimeException("Wrong password");

    }
    public UserAuthSecret authorizeWithSecret(String secret) throws RuntimeException {
        UserAuthSecret authSecret = authSecretRepo.findBySecret(secret)
                .filter(s -> s.getSecretCreationTime()
                        .isAfter(LocalDateTime.now().minusHours(24)))
                .orElseThrow(() -> new RuntimeException("Invalid or expired secret"));
        return updateSecret(authSecret.getUser());
    }

    public UserAuthSecret updateSecret(User user) {
        UserAuthSecret newSecret = authSecretProvider.updateSecret(user);
        authSecretRepo.saveAndFlush(newSecret);
        return newSecret;
    }
}
