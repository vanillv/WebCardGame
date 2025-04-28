import model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import repository.SessionRepository;
import repository.TurnRepository;
import repository.UserRepository;
import service.GameSessionService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class GameSessionServiceIntegrationTest {
    @InjectMocks
    GameSessionService service;
    @Mock
    UserRepository userRepo;
    @Mock
    SessionRepository sessionRepo;
    @Mock
    TurnRepository turnRepo;
    @Test
    void shouldCreateNewSessionWithHost() {
        User host = new User();
        host.setId(1L);
        when(userRepo.saveAndFlush(any(User.class))).thenReturn(host);
        when(userRepo.existsByLogin(anyString())).thenReturn(true);
        Long sessionCode = service.initSession(host.getId());
        Assertions.assertNotNull(sessionCode);
    }
}