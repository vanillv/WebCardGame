package service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import model.card.Card;
import model.card.PointsCard;
import model.dto.ActionCardUseDto;
import model.entity.Session;
import model.entity.Turn;
import model.entity.User;
import model.entity.UserSessionInstance;
import org.springframework.stereotype.Service;
import repository.SessionRepository;
import repository.TurnRepository;
import repository.UserRepository;
import repository.UserSessionInstanceRepository;
import utils.ActionCardHandler;
import utils.DeckCreator;

import java.util.Random;


@Service
@AllArgsConstructor
@Transactional
public class GameSessionService {
    private SessionRepository sessionRepo;
    private UserRepository userRepo;
    private TurnRepository turnRepo;
    private UserSessionInstanceRepository userInstanceRepo;
    private ActionCardHandler actionCardHandler;
    private DeckCreator deckCreator;
    public Long initSession(long hostId) {
          User host = getUser(hostId);
          Session session = new Session();
          long hostInstanceCode = new Random().nextLong(1000000, 10000000);
          session.addPlayer(host);
          deckCreator.fillAndShuffleDeck(session);
          sessionRepo.saveAndFlush(session);
          return hostInstanceCode;
    }
    public boolean startSession(long hostId, long sessionId) {
        return getSession(sessionId).startGame(hostId);
    }
    public boolean endSession(long sessionId, long hostId) {
        Session session = getSession(sessionId);
        boolean ended = session.gameEnd(hostId, false);
        sessionRepo.saveAndFlush(session);
        return ended;
    }
    public boolean joinPlayer(long userId, long sessionId) {
        Session session = getSession(sessionId);
        boolean addedPlayer = session.addPlayer(getUser(userId));
        sessionRepo.saveAndFlush(session);
        return addedPlayer;
    }
    public boolean playerLeave(long userId, long sessionId) {
        try {
            Session session = getSession(sessionId);
            session.deletePlayer(getUser(userId));
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    public boolean useTurn(ActionCardUseDto dto) {
        try {
            Session session = getSession(dto.getSessionId());
            Card card = session.addTurn(dto.getCardOwnerId());
            UserSessionInstance player = userInstanceRepo.getReferenceById(dto.getCardOwnerId());
            if (card != null) {
                int value = card.getValue();
                Turn thisTurn =  session.getTurns().getLast();
                if (card instanceof PointsCard) {
                    player.setPoints(player.getPoints() + value);
                    thisTurn.cardActivate(player);
                    turnRepo.saveAndFlush(thisTurn);
                    return true;
                }
                UserSessionInstance target = getUserInstance(dto.getTargetId());
                switch (value) {
                    case 1 -> actionCardHandler.applyBlock(target);
                    case 2 -> actionCardHandler.applyDouble(player);
                    case 3 -> actionCardHandler.applySteal(player, target, 3);
                    case 5 -> actionCardHandler.applySwap(player, target);
                    case 10 -> actionCardHandler.applyDefense(player);
                }
                thisTurn.cardActivate(player);
                turnRepo.saveAndFlush(thisTurn);
                return true;
            }
            return false;
        }catch (Exception e) {
            return false;
        }
    }
    private User getUser(long userId) {
        return userRepo.findById(userId).orElseThrow(()->new RuntimeException("UserNotFound"));
    }
    private Session getSession(long sessionId) {
        return sessionRepo.findById(sessionId).orElseThrow(()->new RuntimeException("SessionNotFound"));
    }
    private UserSessionInstance getUserInstance(long userId) {
        return userInstanceRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("UserInstanceNotFound"));
    }
}
