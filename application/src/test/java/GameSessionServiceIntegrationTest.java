import model.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import repository.SessionRepository;
import repository.TurnRepository;
import repository.UserRepository;
import repository.UserSessionInstanceRepository;
import service.GameSessionService;
import utils.ActionCardHandler;
import utils.DeckCreator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

//@SpringBootTest(classes = Application.class)
@DataJpaTest()
class GameSessionServiceIntegrationTest {

    @Autowired
    private SessionRepository sessionRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private TurnRepository turnRepo;
    Mock mock;

    @Test
    void shouldCreateNewSessionWithHost() {
        User host = new User();
        host.setLogin("host");
        host.setName("hostName");
        userRepo.saveAndFlush(host);
        GameSessionService service = new GameSessionService(
            sessionRepo, userRepo, turnRepo, 
            mock(UserSessionInstanceRepository.class),
            mock(ActionCardHandler.class),
            mock(DeckCreator.class)
        );
        Long sessionCode = service.initSession(host.getId());
        assertNotNull(sessionCode);
        assertEquals(host, sessionRepo.getReferenceById(host.getId()));
    }
}