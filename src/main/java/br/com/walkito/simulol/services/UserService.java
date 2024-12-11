package br.com.walkito.simulol.services;

import br.com.walkito.simulol.models.user.User;
import br.com.walkito.simulol.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity createUser(User user){
        try{

        } catch (Exception e){

        }
    }
}
