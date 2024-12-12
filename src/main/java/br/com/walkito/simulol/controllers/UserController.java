package br.com.walkito.simulol.controllers;

import br.com.walkito.simulol.models.user.LoginDTO;
import br.com.walkito.simulol.models.user.User;
import br.com.walkito.simulol.services.UserService;
import br.com.walkito.simulol.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PostMapping(path = "/login")
    public ApiResponse doLogin(@RequestBody LoginDTO loginDTO){
        return userService.doLogin(loginDTO);
    }
}
