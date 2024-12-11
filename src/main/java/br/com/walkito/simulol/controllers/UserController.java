package br.com.walkito.simulol.controllers;

import br.com.walkito.simulol.models.user.User;
import br.com.walkito.simulol.services.UserService;
import br.com.walkito.simulol.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse createUser(@RequestBody User user){
        return userService.createUser(user);
    }
}
