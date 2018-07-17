package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.UserDto;
import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
public class UserAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto register(@RequestBody User user) {
        user.setType("regular");
        User save = userRepository.save(user);
        return login(save);
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto login(@RequestBody User user) {
        String accountName = user.getAccountName();
        String password = user.getPassword();

        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(accountName, password);
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContextHolder.getContext().setAuthentication(auth);

        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        return userRepository.findByAccountName(accountName).toDto();
    }

    @GetMapping(
            value = "/logout"
    )
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
