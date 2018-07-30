package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.UserDto;
import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @GetMapping("")
    public UserDto get(Principal principal) {
        User user = userService.get(principal.getName()).orElse(null);
        if (user == null) {
            logger.debug("something happened");
            return null;
        }
        logger.debug("User: " + user.getUsername() + " has logged in successfully");
        return user.toDto();
    }

    @DeleteMapping("")
    public void delete(HttpSession session) {
        logger.debug("user has logged out,");
        session.invalidate();
    }
}
