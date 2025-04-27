package controller;

import lombok.RequiredArgsConstructor;
import model.dto.request.PlayerInstanceRequestDto;
import model.dto.request.SessionOperationRequestDto;
import model.dto.request.TurnRequestDto;
import model.dto.result.PlayerInstanceResultDto;
import model.dto.result.SessionInfoResultDto;
import model.dto.result.SessionOperationResultDto;
import model.dto.result.TurnResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.GameSessionService;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class SessionManagementController {
    private final GameSessionService gameService;

    @PostMapping
    public ResponseEntity<SessionOperationResultDto> createSession(@RequestBody SessionOperationRequestDto request) {
        try {
            Long hostInstanceCode = gameService.initSession(request.getHostId());
            return ResponseEntity.ok(new SessionOperationResultDto(
                    true,
                    "Session created successfully",
                    hostInstanceCode
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new SessionOperationResultDto(
                    false,
                    "Failed to create session: " + e.getMessage(),
                    null
            ));
        }
    }

    @PostMapping("/{gameId}/start")
    public ResponseEntity<SessionOperationResultDto> startSession(
            @PathVariable Long gameId,
            @RequestBody SessionOperationRequestDto request
    ) {
        try {
            boolean started = gameService.startSession(request.getHostId(), gameId);
            return ResponseEntity.ok(new SessionOperationResultDto(
                    started,
                    started ? "Game started" : "Failed to start game",
                    gameId
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new SessionOperationResultDto(
                    false,
                    "Error starting game: " + e.getMessage(),
                    gameId
            ));
        }
    }

    @PostMapping("/{gameId}/end")
    public ResponseEntity<SessionOperationResultDto> endSession(
            @PathVariable Long gameId,
            @RequestBody SessionOperationRequestDto request
    ) {
        try {
            boolean ended = gameService.endSession(gameId, request.getHostId());
            return ResponseEntity.ok(new SessionOperationResultDto(
                    ended,
                    ended ? "Game ended" : "Failed to end game",
                    gameId
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new SessionOperationResultDto(
                    false,
                    "Error ending game: " + e.getMessage(),
                    gameId
            ));
        }
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<SessionInfoResultDto> getSessionInfo(@PathVariable Long gameId) {
        try {
            // Предполагаем, что метод getSessionInfo() был дописан в сервисе
            SessionInfoResultDto sessionInfo = gameService.getSessionInfo(gameId);
            return ResponseEntity.ok(sessionInfo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{gameId}/join")
    public ResponseEntity<PlayerInstanceResultDto> joinSession(
            @PathVariable Long gameId,
            @RequestBody PlayerInstanceRequestDto requestDto
    ) {
        try {
            boolean success = gameService.joinPlayer(requestDto.getUserId(), gameId);
            return ResponseEntity.ok(new PlayerInstanceResultDto(
                    success,
                    success ? "Joined successfully" : "Failed to join",
                    requestDto.getUserId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new PlayerInstanceResultDto(
                    false,
                    "Join error: " + e.getMessage(),
                    null
            ));
        }
    }

    @PostMapping("/{gameId}/leave")
    public ResponseEntity<PlayerInstanceResultDto> leaveSession(
            @PathVariable Long gameId,
            @RequestBody PlayerInstanceRequestDto requestDto
    ) {
        try {
            boolean success = gameService.playerLeave(requestDto.getUserId(), gameId);
            return ResponseEntity.ok(new PlayerInstanceResultDto(
                    success,
                    success ? "Left successfully" : "Failed to leave",
                    requestDto.getUserId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new PlayerInstanceResultDto(
                    false,
                    "Leave error: " + e.getMessage(),
                    null
            ));
        }
    }

    @PostMapping("/{gameId}/turn")
    public ResponseEntity<TurnResultDto> makeTurn(
            @PathVariable Long gameId,
            @RequestBody TurnRequestDto request
    ) {
        try {
            request.setSessionId(gameId);
            TurnResultDto turnResult = gameService.processTurn(request);

            return ResponseEntity.ok(turnResult);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new TurnResultDto(false, e.getMessage(), null, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new TurnResultDto(false, e.getMessage(), null, null));
        }
    }
}
