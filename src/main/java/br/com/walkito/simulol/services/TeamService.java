package br.com.walkito.simulol.services;

import br.com.walkito.simulol.models.gameSession.GameSession;
import br.com.walkito.simulol.models.team.Team;
import br.com.walkito.simulol.models.team.TeamRequest;
import br.com.walkito.simulol.models.user.User;
import br.com.walkito.simulol.models.user.UserRepository;
import br.com.walkito.simulol.utils.ApiResponse;
import br.com.walkito.simulol.utils.DefaultResponse;
import br.com.walkito.simulol.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public ApiResponse createTeam(TeamRequest teamRequest) {
        try {
            return userRepository.findById(teamRequest.getIdUser())
                    .map(user -> {
                        GameSession gameSession = user.getGameSessions().stream()
                                .filter(session -> session.getId() == teamRequest.getGameSession().getId())
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Sessão de jogo não encontrada."));

                        Team team = teamRequest.getTeam();
                        team.setId(getNewId(gameSession));
                        gameSession.getTeams().add(team);

                        userRepository.save(user);

                        return new ApiResponse(new DefaultResponse(
                                "Equipe criada com sucesso!",
                                HttpStatus.CREATED.value(),
                                team
                        ));
                    })
                    .orElseGet(() -> new ApiResponse(new DefaultResponse(
                            "Não foi possível ahcar o jogo salvo.",
                            HttpStatus.NOT_FOUND.value(),
                            null
                    )));
        } catch (Exception e) {
            return new ApiResponse(Utils.getDefaultErrorResponse(e));
        }
    }

    private int getNewId(GameSession gameSession){
        return gameSession.getTeams().size() + 1;
    }
}
