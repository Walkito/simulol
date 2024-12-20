package br.com.walkito.simulol.controllers;

import br.com.walkito.simulol.models.gameSession.GameSessionRequest;
import br.com.walkito.simulol.services.GameSessionService;
import br.com.walkito.simulol.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "gameSession")
public class GameSessionController {
    @Autowired
    GameSessionService gameSessionService;

    @GetMapping(path = "/gameSessions")
    public ApiResponse getGameSessions(@RequestParam(name = "id") String userId){
        return gameSessionService.getGameSessions(userId);
    }

    @PostMapping(path = "/create")
    public ApiResponse createGameSession(@RequestBody GameSessionRequest gameSessionRequest){
        return gameSessionService.createGameSession(gameSessionRequest);
    }

    @DeleteMapping(path = "/delete")
    public ApiResponse deleteGameSession(@RequestParam(name = "userId") String userId,
                                         @RequestParam(name = "gameSessionId") int gameSessionId){
        return gameSessionService.deleteGameSession(userId, gameSessionId);
    }
}
