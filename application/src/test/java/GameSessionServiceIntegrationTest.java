import WebCardGame.application.Application;
import model.entity.Session;
import model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import repository.SessionRepository;
import repository.UserRepository;
import service.GameSessionService;
import utils.ActionCardHandler;
import utils.DeckCreator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({GameSessionService.class, DeckCreator.class, ActionCardHandler.class})
class GameSessionServiceIntegrationTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private GameSessionService service;

    @Test
    void shouldCreateNewSessionWithHost() {
        User host = new User();
        host.setLogin("host");
        host.setName("hostName");
        userRepo.save(host);
        Long sessionId = service.initSession(host.getId());
        assertNotNull(sessionId);
        Session session = sessionRepo.findById(sessionId)
                .orElseThrow();
        assertEquals(1, session.getPlayerList().size());
        assertEquals(host.getId(), session.getPlayerList().get(0).getUser().getId());
    }
}