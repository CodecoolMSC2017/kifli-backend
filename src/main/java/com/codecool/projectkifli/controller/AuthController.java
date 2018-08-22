package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.UserCredentialsDto;
import com.codecool.projectkifli.exception.InvalidInputException;
import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.service.UserService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @GetMapping("")
    public UserCredentialsDto get(Principal principal) {
        logger.trace("Get user {}", principal.getName());
        try {
            User user = userService.get(principal.getName());
            if(!user.getEnabled()) {
                logger.error(principal.getName() + " is not verified yet");
                return null;
            }
            logger.info("User {} has logged in successfully", user.getUsername());
            return new UserCredentialsDto(user);
        } catch (NotFoundException e) {
            logger.error("Did not find user {}", principal.getName());
            return null;
        }

    }

    @DeleteMapping("")
    public void delete(HttpSession session, Principal principal) {
        logger.info("User {} has logged out", principal.getName());
        session.invalidate();
    }

    @PostMapping("")
    public UserCredentialsDto authenticateUserByToken(@RequestBody Map<String, String> map, HttpServletResponse response) {
        try {
            System.out.println("vaaaaaaaaaaaaaalami");
            User user = userService.getUserByToken(map.get("idToken"));
            return new UserCredentialsDto(user);
        } catch (GeneralSecurityException | IOException e) {
            logger.error("Unable to authenticate user", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            logger.error("Error: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (InvalidInputException e) {
            response.setHeader("errorMessage", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.warn("Unable to register: {}", e.getMessage());
        }
        System.out.println("hmmmmmmmmm");
        return null;
    }
}
