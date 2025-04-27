package service;

import lombok.AllArgsConstructor;
import model.dto.UserRegistrationDto;
import model.entity.User;
import org.springframework.stereotype.Service;
import repository.UserAuthSecretRepository;
import repository.UserRepository;
import utils.AuthSecretProvider;

@Service
@AllArgsConstructor
public class RegistrationService {
    UserRepository userRepo;
    UserAuthSecretRepository authSecretRepo;
    AuthSecretProvider secretProvider;
    public void registerNewUser(UserRegistrationDto dto){
        User user = new User();
        user.setName(dto.getName());
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        user.setSecretCode(secretProvider.updateSecret(user));
        userRepo.saveAndFlush(user);
    }
}
