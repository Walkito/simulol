package br.com.walkito.simulol.services;

import br.com.walkito.simulol.models.enums.ErroMessages;
import br.com.walkito.simulol.models.gameSession.GameSession;
import br.com.walkito.simulol.models.team.Team;
import br.com.walkito.simulol.models.team.TeamRequest;
import br.com.walkito.simulol.models.user.UserRepository;
import br.com.walkito.simulol.utils.ApiResponse;
import br.com.walkito.simulol.utils.DefaultResponse;
import br.com.walkito.simulol.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper mapper;

    public ApiResponse getTeam(String idUser, int idSession, int idTeam) {
        try {
            return userRepository.findById(idUser)
                    .map(user -> {
                        Team searchedTeam = user.getGameSessions().stream()
                                .filter(session -> session.getId() == idSession)
                                .findFirst()
                                .get().getTeams().stream()
                                .filter(team -> team.getId() == idTeam)
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Time Não Encontrado"));

                        return new ApiResponse(new DefaultResponse(
                                "Time encontrado com sucesso!",
                                HttpStatus.OK.value(),
                                searchedTeam
                        ));
                    })
                    .orElseGet(() -> new ApiResponse(new DefaultResponse(
                            ErroMessages.USER_NOT_FOUND.getMensagemErro(),
                            HttpStatus.NOT_FOUND.value(),
                            null
                    )));
        } catch (Exception e) {
            return new ApiResponse(Utils.getDefaultErrorResponse(e));
        }
    }

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
                            ErroMessages.USER_NOT_FOUND.getMensagemErro(),
                            HttpStatus.NOT_FOUND.value(),
                            null
                    )));
        } catch (Exception e) {
            return new ApiResponse(Utils.getDefaultErrorResponse(e));
        }
    }

    @Transactional
    public ApiResponse editTeam(TeamRequest teamRequest) {
        try {
            return userRepository.findById(teamRequest.getIdUser())
                    .map(user -> {
                        Team editedTeam = user.getGameSessions().stream()
                                .filter(session -> session.getId() == teamRequest.getGameSession().getId())
                                .findFirst()
                                .get().getTeams().stream()
                                .filter(team -> team.getId() == teamRequest.getTeam().getId())
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Sessão de jogo não encontrada."));

                        mapper.map(teamRequest.getTeam(), editedTeam);

                        userRepository.save(user);

                        return new ApiResponse(new DefaultResponse(
                                "Equipe criada com sucesso!",
                                HttpStatus.CREATED.value(),
                                editedTeam
                        ));
                    })
                    .orElseGet(() -> new ApiResponse(new DefaultResponse(
                            ErroMessages.USER_NOT_FOUND.getMensagemErro(),
                            HttpStatus.NOT_FOUND.value(),
                            null
                    )));
        } catch (Exception e) {
            return new ApiResponse(Utils.getDefaultErrorResponse(e));
        }
    }

    @Transactional
    public ApiResponse deleteTeam(String userId, int gameSessionId, int teamId) {
        try {
            return userRepository.findById(userId).map(user -> {
                    GameSession gameSession = user.getGameSessions().stream()
                            .filter(session -> session.getId() == gameSessionId)
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Não foi possível achar a sessão"));

                    Team team = gameSession.getTeams().stream()
                            .filter(teamX -> teamX.getId() == teamId)
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Não foi possível achar o time"));

                    gameSession.getTeams().remove(team);

                    userRepository.save(user);

                    return new ApiResponse(new DefaultResponse(
                            "Time excluído com sucesso!",
                            HttpStatus.OK.value(),
                            null
                    ));
            })
                    .orElseGet(() -> new ApiResponse(new DefaultResponse(
                            ErroMessages.USER_NOT_FOUND.getMensagemErro(),
                            HttpStatus.NOT_FOUND.value(),
                            null
                    )));
        } catch (Exception e) {
            return new ApiResponse(Utils.getDefaultErrorResponse(e));
        }
    }

    private int getNewId(GameSession gameSession) {
        return gameSession.getTeams().size() + 1;
    }
}
