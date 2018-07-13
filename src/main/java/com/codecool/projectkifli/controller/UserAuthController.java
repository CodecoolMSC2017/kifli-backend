package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.UserDto;
import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UserAuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto register(@RequestBody User user) {
        user.setType("regular");
        User save = userRepository.save(user);
        return save.toDto();
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto login(@RequestBody User user) {
        String accountName = user.getAccountName();
        String password = user.getPassword();

        User dbUser = userRepository.findByAccountName(accountName);
        if (dbUser != null) {
            if (password.equals(dbUser.getPassword())) {
                // TODO: login user
                return dbUser.toDto();
            }
        }
        return null;
    }

    @PostMapping(
            value = "/logout",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void logout(@RequestBody User user) {
        // TODO: logout user
    }
}
