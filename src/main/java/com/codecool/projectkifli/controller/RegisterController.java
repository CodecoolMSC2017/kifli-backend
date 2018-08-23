package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.service.EmailService;
import com.codecool.projectkifli.exception.InvalidInputException;
import com.codecool.projectkifli.service.UserService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public void register(@RequestBody Map<String, String> map, HttpServletResponse response) {
        String username = map.get("userName");
        String email = map.get("email");
        String password = map.get("password");
        String confirmPassword = map.get("confirmPassword");
        String firstName = map.get("firstName");
        String lastName = map.get("lastName");
        try {
            userService.add(username, email, password, confirmPassword, firstName, lastName);
        //  emailService.simpleMessage(email, username);
        } catch (InvalidInputException e) {
            response.setHeader("errorMessage", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.warn("Unable to register: {}", e.getMessage());
        } catch (NotFoundException e) {
            response.setHeader("errorMessage", e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            logger.warn("Unable to register: {}", e.getMessage());
        }
    }
}
