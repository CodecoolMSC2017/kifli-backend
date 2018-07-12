package com.codecool.projectkifli;

import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.repositorty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userRepository.findById(id).orElse(null);
    }
}
