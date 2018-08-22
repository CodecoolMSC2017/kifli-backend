package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.UserCredentialsDto;
import com.codecool.projectkifli.dto.UserDto;
import com.codecool.projectkifli.exception.ForbiddenException;
import com.codecool.projectkifli.exception.InvalidInputException;
import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.service.UserService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public UserCredentialsDto get(@PathVariable("id") Integer id, HttpServletResponse response) {
        logger.trace("Get user {}", id);
        try {
            return userService.get(id);
        } catch (NotFoundException e) {
            response.setHeader("errorMessage", e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            logger.warn("User {} not found", id);
            return null;
        }
    }

    @PostMapping("")
    public User add(@RequestBody Map<String, String> map, HttpServletResponse response) {
        String username = map.get("username");
        String email = map.get("email");
        String password = map.get("password");
        String confirmPassword = map.get("confirmPassword");
        String firstName = map.get("firstName");
        String lastName = map.get("lastName");
        try {
            return userService.add(username, email, password, confirmPassword, firstName, lastName);
        } catch (InvalidInputException e) {
            response.setHeader("errorMessage", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.warn("Invalid input: {}", e.getMessage());
            return null;
        } catch (NotFoundException e) {
            response.setHeader("errorMessage", e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            logger.warn("Error adding new user: {}", e.getMessage());
            return null;
        }
    }

    @PostMapping(
            value = "/change-password",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void changePassword(@RequestBody Map<String, String> map, Principal principal, HttpServletResponse response) {
        logger.trace("Change password of user {}", principal.getName());
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        String confirmationPassword = map.get("confirmationPassword");
        try {
            userService.changePassword(principal.getName(), oldPassword, newPassword, confirmationPassword);
        } catch (InvalidInputException e) {
            response.setHeader("errorMessage", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.warn("Can't change password: {}", e.getMessage());
        } catch (NotFoundException e) {
            response.setHeader("errorMessage", e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            logger.warn("Can't change password: {}", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        logger.trace("Delete user {}", id);
        userService.delete(id);
    }

    @GetMapping(
            value = "/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserCredentialsDto getLoggedInUser(Principal principal, HttpServletResponse response, HttpSession session) {
        logger.trace("Get current user");
        if (principal == null) {
            logger.debug("Not logged in, returning null");
            return null;
        }
        try {
            User user = userService.get(principal.getName());
            return new UserCredentialsDto(user);
        } catch (NotFoundException e) {
            session.invalidate();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            logger.warn(e.getMessage());
            return null;
        }
    }

    @PutMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserCredentialsDto updateUser(@RequestBody UserCredentialsDto user, Principal principal, HttpServletResponse response) {
        logger.trace("Put by {}", principal.getName());
        try {
            return userService.updateUser(user, principal.getName());
        } catch (NotFoundException e) {
            response.setHeader("errorMessage", e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            logger.warn("Can't update user: {}", e.getMessage());
        } catch (ForbiddenException e) {
            response.setHeader("errorMessage", e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            logger.warn("Can't update user: {}", e.getMessage());
        }
        return null;
    }
}
