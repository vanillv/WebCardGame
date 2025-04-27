package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import repository.UserAuthSecretRepository;
import repository.UserRepository;
import service.AuthorizationService;
import utils.AuthSecretProvider;

@RestController
public class AuthorizationController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserAuthSecretRepository authSecretRepo;
    private AuthSecretProvider secretProvider;
    private AuthorizationService authService = new AuthorizationService(userRepo, authSecretRepo, secretProvider);

}
