package br.com.walkito.simulol.controllers;

import br.com.walkito.simulol.models.gameSession.GameSessionRequest;
import br.com.walkito.simulol.services.GameSessionService;
import br.com.walkito.simulol.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "gameSession")
public class GameSessionController {
    @Autowired
    GameSessionService gameSessionService;

    @PostMapping(path = "/create")
    public ApiResponse createGameSession(@RequestBody GameSessionRequest gameSessionRequest){
        return gameSessionService.createGameSession(gameSessionRequest);
    }
}
