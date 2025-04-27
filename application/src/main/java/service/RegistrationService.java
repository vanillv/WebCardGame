package service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import model.dto.request.UserRegistrationDto;
import model.entity.User;
import model.entity.UserAuthSecret;
import org.springframework.stereotype.Service;
import repository.UserAuthSecretRepository;
import repository.UserRepository;
import utils.AuthSecretProvider;

@Service
@AllArgsConstructor
@Transactional
public class RegistrationService {
    UserRepository userRepo;
    UserAuthSecretRepository authSecretRepo;
    AuthSecretProvider secretProvider;
    public User registerNewUser(UserRegistrationDto dto){
        if (dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        UserAuthSecret secret = secretProvider.updateSecret(user);
        user.setSecretCode(secret);
        userRepo.saveAndFlush(user);
        authSecretRepo.saveAndFlush(secret);
        return user;
    }
}
