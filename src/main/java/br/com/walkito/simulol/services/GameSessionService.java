package br.com.walkito.simulol.services;

import br.com.walkito.simulol.models.gameSession.GameSession;
import br.com.walkito.simulol.models.gameSession.GameSessionRequest;
import br.com.walkito.simulol.models.user.User;
import br.com.walkito.simulol.models.user.UserRepository;
import br.com.walkito.simulol.utils.ApiResponse;
import br.com.walkito.simulol.utils.DefaultResponse;
import br.com.walkito.simulol.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GameSessionService {
    @Autowired
    UserRepository userRepository;

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
                response.setMessage("Não foi possível criar um novo jogo. Usuário não encontrado");
                response.setObject(null);
                response.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            response = Utils.getDefaultErrorResponse(e);
        }

        return new ApiResponse(response);
    }

    private int getNewId(User user){
        return user.getGameSessions().size() + 1;
    }
}
