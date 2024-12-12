package br.com.walkito.simulol.services;

import br.com.walkito.simulol.models.user.LoginDTO;
import br.com.walkito.simulol.models.user.User;
import br.com.walkito.simulol.models.user.UserRepository;
import br.com.walkito.simulol.utils.ApiResponse;
import br.com.walkito.simulol.utils.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    public ApiResponse createUser(User user) {
        DefaultResponse response = new DefaultResponse();

        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            User newUser = new User(user.getUsername(), encryptedPassword, user.getEmail());

            User createdUser = userRepository.save(newUser);

            response.setHttpStatusCode(HttpStatus.CREATED);
            response.setMessage("Usuário criado com sucesso!");
            response.setObject(createdUser);
        } catch (DuplicateKeyException e){
            e.printStackTrace();
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

    public ApiResponse doLogin(LoginDTO loginDTO){
        DefaultResponse response = new DefaultResponse();

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        response.setHttpStatusCode(HttpStatus.OK);
        response.setObject(auth.getPrincipal());
        response.setMessage("Usuário logado com sucesso");

        return new ApiResponse(response);
    }
}
