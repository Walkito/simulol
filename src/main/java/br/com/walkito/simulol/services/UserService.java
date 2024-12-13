package br.com.walkito.simulol.services;

import br.com.walkito.simulol.models.ErroMessages;
import br.com.walkito.simulol.models.role.Role;
import br.com.walkito.simulol.models.user.LoginDTO;
import br.com.walkito.simulol.models.user.User;
import br.com.walkito.simulol.models.user.UserRepository;
import br.com.walkito.simulol.services.security.TokenService;
import br.com.walkito.simulol.utils.ApiResponse;
import br.com.walkito.simulol.utils.DefaultResponse;
import br.com.walkito.simulol.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public ApiResponse createUser(User user) {
        DefaultResponse response = new DefaultResponse();

        try {
            if (!Utils.emailValidation(user.getEmail())) {
                response.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage(ErroMessages.EMAIL_INVALID.getMensagemErro());
                response.setObject(user.getEmail());
            } else {
                String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
                User newUser = new User(user.getUsername(), encryptedPassword, user.getEmail());

                User createdUser = userRepository.save(newUser);

                response.setHttpStatusCode(HttpStatus.CREATED.value());
                response.setMessage("Usuário criado com sucesso!");
                response.setObject(createdUser);
            }
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            response.setMessage("E-mail ou nome de usuário já estão em uso");
            response.setObject(null);
            response.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(ErroMessages.INTERNAL_SERVER_ERROR.getMensagemErro());
            response.setObject(null);
            response.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return new ApiResponse(response);
    }

    public ApiResponse doLogin(LoginDTO loginDTO) {
        DefaultResponse response = new DefaultResponse();

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            response.setHttpStatusCode(HttpStatus.OK.value());
            response.setObject(token);
            response.setMessage("Login realizado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(ErroMessages.INTERNAL_SERVER_ERROR.getMensagemErro());
            response.setObject(null);
            response.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return new ApiResponse(response);
    }
}
