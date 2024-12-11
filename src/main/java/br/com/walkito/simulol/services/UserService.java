package br.com.walkito.simulol.services;

import br.com.walkito.simulol.models.user.User;
import br.com.walkito.simulol.models.user.UserRepository;
import br.com.walkito.simulol.utils.ApiResponse;
import br.com.walkito.simulol.utils.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ApiResponse createUser(User user) {
        DefaultResponse response = new DefaultResponse();

        try {
            User createdUser = userRepository.save(user);

            response.setHttpStatusCode(HttpStatus.CREATED);
            response.setMessage("Usuário criado com sucesso!");
            response.setObject(createdUser);
        } catch (DuplicateKeyException e){
            response.setMessage("E-mail ou nome de usuário já estão em uso");
            response.setObject(null);
            response.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            response.setMessage("Erro interno do sistema");
            response.setObject(null);
            response.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ApiResponse(response);
    }
}
