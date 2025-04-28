import WebCardGame.application.Application;
import model.dto.request.UserRegistrationDto;
import model.entity.User;
import model.entity.UserAuthSecret;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import repository.UserAuthSecretRepository;
import repository.UserRepository;
import service.RegistrationService;
import utils.AuthSecretProvider;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = Application.class)
class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserAuthSecretRepository authSecretRepo;
    
    @Mock
    private AuthSecretProvider secretProvider;
    
    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void shouldRegisterNewUserSuccessfully() {
        UserRegistrationDto dto = new UserRegistrationDto("Test", "test", "password");
        User user = new User();
        user.setLogin("test");
        UserAuthSecret secret = new UserAuthSecret(user, "secret123");
        when(userRepository.existsByLogin("test")).thenReturn(false);
        when(secretProvider.updateSecret(any())).thenReturn(secret);
        registrationService.registerNewUser(dto);
        verify(userRepository, times(1)).saveAndFlush(any(User.class));
        verify(authSecretRepo, times(1)).saveAndFlush(any(UserAuthSecret.class));
    }
}