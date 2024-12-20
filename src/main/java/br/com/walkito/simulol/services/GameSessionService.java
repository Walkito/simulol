package br.com.walkito.simulol.services;

import br.com.walkito.simulol.models.enums.ErroMessages;
import br.com.walkito.simulol.models.gameSession.GameSession;
import br.com.walkito.simulol.models.gameSession.GameSessionRequest;
import br.com.walkito.simulol.models.user.User;
import br.com.walkito.simulol.models.user.UserRepository;
import br.com.walkito.simulol.utils.ApiResponse;
import br.com.walkito.simulol.utils.DefaultResponse;
import br.com.walkito.simulol.utils.Utils;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GameSessionService {
    @Autowired
    UserRepository userRepository;

    public ApiResponse getGameSessions(String userId){
        DefaultResponse response = new DefaultResponse();

        try{
            Optional<User> user = userRepository.findById(userId);

            if(user.isPresent()){
                List<GameSession> gameSessions = user.get().getGameSessions();

                response.setMessage("Sessões encontradas com sucesso!");
                response.setObject(gameSessions);
                response.setHttpStatusCode(HttpStatus.OK.value());
            }
        } catch (Exception e){
            response = Utils.getDefaultErrorResponse(e);
        }

        return new ApiResponse(response);
    }

    @Transactional
    public ApiResponse createGameSession(GameSessionRequest gameSessionRequest) {
        DefaultResponse response = new DefaultResponse();

        try {
            Optional<User> user = userRepository.findById(gameSessionRequest.getIdUser());

            if (user.isPresent()){
                User findedUser = user.get();

                gameSessionRequest.getGameSession().setId(getNewId(findedUser));
                findedUser.getGameSessions().add(gameSessionRequest.getGameSession());

                userRepository.save(findedUser);

                response.setMessage("Jogo criado com sucesso!");
                response.setObject(gameSessionRequest.getGameSession());
                response.setHttpStatusCode(HttpStatus.CREATED.value());
            } else {
                response.setMessage(ErroMessages.USER_NOT_FOUND.getMensagemErro());
                response.setObject(null);
                response.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            response = Utils.getDefaultErrorResponse(e);
        }

        return new ApiResponse(response);
    }

    @Transactional
    public ApiResponse deleteGameSession(String userId, int gameSessionId){
        try{
            return userRepository.findById(userId).map( user -> {
                GameSession gameSession = user.getGameSessions()
                        .stream()
                        .filter(session -> session.getId() == gameSessionId)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Sessão de Jogo não encontrada"));

                user.getGameSessions().remove(gameSession);
                userRepository.save(user);

                return new ApiResponse(new DefaultResponse(
                        "Sessão excluída com sucesso",
                        HttpStatus.OK.value(),
                        null
                ));
            }).orElseGet(() -> new ApiResponse(new DefaultResponse(
                    ErroMessages.USER_NOT_FOUND.getMensagemErro(),
                HttpStatus.NOT_FOUND.value(),
                null
            )));
        } catch (Exception e){
            return new ApiResponse(Utils.getDefaultErrorResponse(e));
        }
    }

    private int getNewId(User user){
        return user.getGameSessions().size() + 1;
    }
}
