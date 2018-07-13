package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping(
            path = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User insertUser(@RequestBody User user) {
        user.setType("regular");
        return userRepository.save(user);
    }
}
