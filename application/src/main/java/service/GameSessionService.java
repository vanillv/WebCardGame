package service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import model.entity.card.Card;
import model.entity.card.PointsCard;
import model.dto.request.TurnRequestDto;
import model.dto.result.PlayerInfoDto;
import model.dto.result.SessionInfoResultDto;
import model.dto.result.TurnResultDto;
import model.entity.Session;
import model.entity.Turn;
import model.entity.User;
import model.entity.UserSessionInstance;
import model.enums.SessionStatus;
import org.springframework.stereotype.Service;
import repository.SessionRepository;
import repository.TurnRepository;
import repository.UserRepository;
import repository.UserSessionInstanceRepository;
import utils.ActionCardHandler;
import utils.DeckCreator;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


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

    public SessionInfoResultDto getSessionInfo(Long sessionId) {
        Session session = getSession(sessionId);
        return new SessionInfoResultDto(
                session.getId(),
                session.getPlayerTurnOrder().get(session.getCurrPlayerIndex()).getUser().getId(),
                session.getPlayerList().stream()
                        .map(p -> new PlayerInfoDto(
                                p.getUser().getId(),
                                p.getUser().getName(),
                                p.getPoints(),
                                p.getStatus()))
                        .collect(Collectors.toList()),
                session.getDeck().size(),
                session.getStatus()
        );
    }
    public TurnResultDto processTurn(TurnRequestDto dto) {
        Session session = getSession(dto.getSessionId());
        validateTurn(session, dto.getCardOwnerId());
        Turn turn = session.processPlayerTurn(dto.getCardOwnerId());
        Card drawnCard = turn.getCard();
        if (drawnCard == null) {
            return new TurnResultDto(false, "Invalid turn", null, null);
        }
        applyCardEffects(session, drawnCard, dto);
        turn.cardActivate(getUserInstance(dto.getTargetId()));
        turnRepo.saveAndFlush(turn);
        session = sessionRepo.saveAndFlush(session);
        return buildTurnResult(session, drawnCard);
    }
    private void validateTurn(Session session, Long playerId) {
        if (session.getStatus() == SessionStatus.Finished) {
            throw new IllegalStateException("Game is already finished");
        }
        UserSessionInstance currentPlayer = session.getCurrentPlayer();
        if (!currentPlayer.getUser().getId().equals(playerId)) {
            throw new IllegalArgumentException("Not player's turn");
        }
    }
    private void applyCardEffects(Session session, Card card, TurnRequestDto dto) {
        UserSessionInstance player = getUserInstance(dto.getCardOwnerId());
        if (card instanceof PointsCard) {
            player.setPoints(player.getPoints() + card.getValue());
            userInstanceRepo.saveAndFlush(player);
            return;
        }
        UserSessionInstance target = getUserInstance(dto.getTargetId());
        applyActionCard(card, player, target);
    }
    private void applyActionCard(Card card, UserSessionInstance player, UserSessionInstance target) {
        switch (card.getValue()) {
            case 0 -> actionCardHandler.applySlowEffect(target);
            case 1 -> actionCardHandler.applyBlock(target);
            case 2 -> actionCardHandler.applyDouble(player);
            case 3 -> actionCardHandler.applySteal(player, target, 3);
            case 5 -> actionCardHandler.applySwap(player, target);
            case 10 -> actionCardHandler.applyDefense(player);
            default -> throw new IllegalArgumentException("Unknown card value");
        }
        userInstanceRepo.saveAllAndFlush(List.of(player, target));
    }

    private TurnResultDto buildTurnResult(Session session, Card card) {
        return new TurnResultDto(
                true,
                "Card " + card.getName() + " applied",
                session.getCurrentPlayer().getUser().getId(),
                session.getPlayerTurnOrder().stream()
                        .collect(Collectors.toMap(
                                p -> p.getUser().getId(),
                                UserSessionInstance::getPoints
                        ))
        );
    }
    private User getUser(long userId) {
        return userRepo.getReferenceById(userId);
    }
    private Session getSession(long sessionId) {
        return sessionRepo.getReferenceById(sessionId);
    }
    private UserSessionInstance getUserInstance(long userId) {
        return userInstanceRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("UserInstanceNotFound"));
    }
}
