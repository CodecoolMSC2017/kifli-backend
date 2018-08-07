package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.UserCredentialsDto;
import com.codecool.projectkifli.dto.UserDto;
import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<UserDto> getAll() {
        logger.trace("Get all users");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserCredentialsDto get(@PathVariable("id") Integer id) {
        logger.trace("Get user {}", id);
        return userService.get(id);
    }

    @PostMapping("")
    public User add(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String email = map.get("email");
        String password = map.get("password");
        String confirmPassword = map.get("confirmPassword");
        String firstName = map.get("firstName");
        String lastName = map.get("lastName");
        return userService.add(username, email, password, confirmPassword, firstName, lastName);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody Map<String, String> map) {
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        String confirmationPassword = map.get("confirmationPassword");
        System.out.println(oldPassword+ newPassword+ confirmationPassword);
        userService.changePassword(oldPassword, newPassword, confirmationPassword);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }

    @GetMapping(
            value = "/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserCredentialsDto getLoggedInUser(Principal principal) {
        logger.trace("Get current user");
        if (principal == null) {
            logger.debug("Not logged in, returning null");
            return null;
        }
        User user = userService.get(principal.getName());
        if (user == null) {
            logger.error("Did not find user {}", principal.getName());
            return null;
        }
        logger.trace("Creating and returning dto");
        return new UserCredentialsDto(user);
    }
}
