package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.UserCredentialsDto;
import com.codecool.projectkifli.dto.UserDto;
import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Iterable<UserDto> getAll() {
        List<UserDto> userDtos = new ArrayList<>();
        userService.getAll().forEach(user -> userDtos.add(new UserDto(user)));
        return userDtos;
    }

    @GetMapping("/{id}")
    public UserCredentialsDto get(@PathVariable("id") Integer id) {
        User user = userService.get(id).orElse(null);
        if (user == null) {
            return null;
        }
        return new UserCredentialsDto(user);
    }

    @PostMapping("")
    public User add(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String email = map.get("email");
        String password = map.get("password");
        String confirmationPassword = map.get("confirmationPassword");
        return userService.add(username, email, password, confirmationPassword);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody Map<String, String> map) {
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        String confirmationPassword = map.get("confirmationPassword");
        userService.changePassword(oldPassword, newPassword, confirmationPassword);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }
}
