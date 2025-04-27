package service;

import lombok.AllArgsConstructor;
import model.entity.User;
import model.entity.UserAuthSecret;
import org.springframework.stereotype.Service;
import repository.UserAuthSecretRepository;
import repository.UserRepository;
import utils.AuthSecretProvider;

@Service
@AllArgsConstructor
public class AuthorizationService {
    UserRepository userRepo;
    UserAuthSecretRepository authSecretRepo;
    AuthSecretProvider authSecretProvider;
    public UserAuthSecret authorizeWithPassword(String login, String password) {
        User user = userRepo.findBy("login and password");
        return updateSecret(user);;
    }
    public UserAuthSecret authorizeWithSecret(String secret) {
        //add findBySecret method into the UserAuthSecretRepo that returns User from the secret
        User user = authSecretRepo.findBy("find by secret");
        return updateSecret(user);
    }
    private UserAuthSecret updateSecret(User user) {
        user.setSecretCode(authSecretProvider.updateSecret(user));
        return user.getSecretCode();
    }
}
