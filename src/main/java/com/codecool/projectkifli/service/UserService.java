package com.codecool.projectkifli.service;

import com.codecool.projectkifli.dto.UserCredentialsDto;
import com.codecool.projectkifli.dto.UserDto;
import com.codecool.projectkifli.exception.ForbiddenException;
import com.codecool.projectkifli.exception.InvalidInputException;
import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.model.VerificationNumber;
import com.codecool.projectkifli.repository.UserRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service
public class UserService {

    HttpTransport transport = new NetHttpTransport();
    JsonFactory jsonFactory = new JacksonFactory();

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

    public UserCredentialsDto get(Integer id) throws NotFoundException {
        logger.debug("Finding user {} by id", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserCredentialsDto(user);
    }

    public User get(String username) throws NotFoundException {
        logger.debug("Finding user {} by username", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Transactional
    public User add(
            String username,
            String email,
            String password,
            String confirmPassword,
            String firstName,
            String lastName) throws InvalidInputException, NotFoundException {
        if (username == null || username.equals("")) {
            throw new InvalidInputException("Username can't be empty!");
        }
        if (email == null || email.equals("")) {
            throw new InvalidInputException("Email can't be empty!");
        }
        if (password == null || password.equals("")) {
            throw new InvalidInputException("Password can't be empty!");
        }
        if (confirmPassword == null || confirmPassword.equals("")) {
            throw new InvalidInputException("Confirmation password can't be empty!");
        }
        if (firstName == null || firstName.equals("")) {
            throw new InvalidInputException("First name can't be empty!");
        }
        if (lastName == null || lastName.equals("")) {
            throw new InvalidInputException("Last name can't be empty!");
        }
        if (!password.equals(confirmPassword)) {
            throw new InvalidInputException("Password and confirmation password do not match!");
        }
        userDetailsManager.createUser(new org.springframework.security.core.userdetails.User(
                username,
                passwordEncoder.encode(password),
                AuthorityUtils.createAuthorityList("USER_ROLE")));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found!"));
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(true);
        user.setVerificationNumber(new VerificationNumber(user.getId()));
        System.out.println(user.getVerificationNumber().toString());
        return user;
    }

    @Transactional
    private User googleAdd(
            String username,
            String email,
            String firstName,
            String lastName
    ) throws InvalidInputException, NotFoundException {
        if (username == null || username.equals("")) {
            throw new InvalidInputException("Username can't be empty!");
        }
        if (email == null || email.equals("")) {
            throw new InvalidInputException("Email can't be empty!");
        }
        if (firstName == null || firstName.equals("")) {
            throw new InvalidInputException("First name can't be empty!");
        }
        if (lastName == null || lastName.equals("")) {
            throw new InvalidInputException("Last name can't be empty!");
        }
        logger.trace("Creating new user");
        userDetailsManager.createUser(new org.springframework.security.core.userdetails.User(
                username,
                "",
                AuthorityUtils.createAuthorityList("USER_ROLE")));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found hmmmmmm!"));
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(true);
        //user.setVerificationNumber(new VerificationNumber(username));
        userRepository.save(user);
        return user;
    }

    public void delete(Integer id) {
        logger.debug("Deleting user {}", id);
        userRepository.deleteById(id);
    }

    public void changePassword(String username, String oldPassword, String newPassword, String confirmationPassword) throws InvalidInputException, NotFoundException {
        logger.debug("Changing password of user {}", username);
        if (!passwordEncoder.matches(oldPassword, getOldPassword(username))) {
            throw new InvalidInputException("Old password is incorrect!");
        }
        if (!newPassword.equals(confirmationPassword)) {
            throw new InvalidInputException("New password and confirmation password don't match!");
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        userDetailsManager.changePassword(oldPassword, encodedNewPassword);
        logger.info("Changed password of user {}", username);
    }

    private String getOldPassword(String username) throws NotFoundException {
        User user = get(username);
        return user.getPassword();
    }

    public UserCredentialsDto updateUser(UserCredentialsDto newUser, String username) throws NotFoundException, ForbiddenException {
        User user = get(username);
        if (!newUser.getId().equals(user.getId())) {
            throw new ForbiddenException("You can only update your own profile!");
        }
        user.setEmail(newUser.getEmail());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setCredentials(newUser.getCredentials());

        User save = userRepository.save(user);
        logger.info("Updated profile of user {}", username);
        return new UserCredentialsDto(save);
    }

    private boolean isEmailExists(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null;
    }

    private User getUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    private User getGoogleAuthenticatedUser(String email, String firstName, String lastName) throws NotFoundException, InvalidInputException {
        if (!isEmailExists(email)) {
            logger.debug("Email does not exists yet");
            return googleAdd(email, email, firstName, lastName);
        }
        logger.debug("Email exists, getting user by it");
        return getUserByEmail(email);
    }

    public UserCredentialsDto getUserByToken(String idToken) throws GeneralSecurityException, IOException, NotFoundException, InvalidInputException {
        GoogleIdToken token = verifyToken(idToken);
        GoogleIdToken.Payload payload = token.getPayload();

        String email = payload.getEmail();
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        logger.debug("Authenticating user of email {}", email);
        User user = getGoogleAuthenticatedUser(email, familyName, givenName);
        logger.trace("Got user, authenticating now");
        List<GrantedAuthority> roles = user.getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null, roles);

        SecurityContextHolder.getContext().setAuthentication(auth);
        logger.trace("Set authentication, returning user");
        return new UserCredentialsDto(user);
    }

    private GoogleIdToken verifyToken(String idToken) throws GeneralSecurityException, IOException {
        logger.trace("Verifying token");
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList("222157294364-s0r226g6ue92bp6n78iaqcthjbohi3pp.apps.googleusercontent.com"))
                .build();

        GoogleIdToken token = verifier.verify(idToken);
        if (token == null) {
            logger.debug("Token is null");
            throw new IOException();
        }
        return token;
    }
}
