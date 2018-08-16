package com.codecool.projectkifli.service;

import com.codecool.projectkifli.dto.UserCredentialsDto;
import com.codecool.projectkifli.dto.UserDto;
import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    //Principal principal = SecurityContextHolder.getContext().getAuthentication();


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> getAll() {
        logger.debug("Finding all users");
        List<User> users = userRepository.findAll();
        logger.trace("Found {} users in total, creating dtos", users.size());
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(new UserDto(user)));
        return userDtos;
    }

    public User get(String username) {
        logger.debug("Finding user {}", username);
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public User add(String username, String email, String password, String confirmPassword, String firstName, String lastName) {
        if (!password.equals(confirmPassword) ||
                username.equals(null) || username == "" ||
                email.equals(null) || email == "" ||
                password.equals(null) || password == "" ||
                confirmPassword.equals(null) || confirmPassword == "" ||
                firstName.equals(null) || firstName == "" ||
                lastName.equals(null) || lastName == "") {
            throw new IllegalArgumentException();
        }
        userDetailsManager.createUser(new org.springframework.security.core.userdetails.User(
                username,
                passwordEncoder.encode(password),
                AuthorityUtils.createAuthorityList("USER_ROLE")));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(""));
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    public UserCredentialsDto get(Integer id) {
        logger.debug("Finding user {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            logger.error("Did not find user {}", id);
            return null;
        }
        return new UserCredentialsDto(user);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public void changePassword(String username, String oldPassword, String newPassword, String confirmationPassword) {
        logger.debug("Changing password of user {}", username);
        if (!passwordEncoder.matches(oldPassword, getOldPassword(username))) {
            logger.error("Old password is incorrect!");
            throw new IllegalArgumentException();
        }
        if (!newPassword.equals(confirmationPassword)) {
            logger.error("New password and confirmation password don't match!");
            throw new IllegalArgumentException();
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        userDetailsManager.changePassword(oldPassword, encodedNewPassword);
        logger.info("Changed password of user {}", username);
    }

    private String getOldPassword(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            logger.error("Did not find user {}", username);
            return null;
        }
        return user.getPassword();
    }

    public UserCredentialsDto updateUser(UserCredentialsDto newUser, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            logger.error("Did not find user {}", username);
            return null;
        }
        if (!newUser.getId().equals(user.getId())) {
            logger.error("Id mismatch, users can only update their own profile");
            return null;
        }
        user.setEmail(newUser.getEmail());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setCredentials(newUser.getCredentials());

        User save = userRepository.save(user);
        logger.info("Updated profile of user {}", username);
        return new UserCredentialsDto(save);
    }
}
