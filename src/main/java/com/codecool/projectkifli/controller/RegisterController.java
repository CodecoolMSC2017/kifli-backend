package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody Map<String, String> map) {
        String username = map.get("userName");
        String email = map.get("email");
        String password = map.get("password");
        String confirmPassword = map.get("confirmPassword");
        String firstName = map.get("firstName");
        String lastName = map.get("lastName");
        userService.add(username, email, password, confirmPassword, firstName, lastName);
    }
}
