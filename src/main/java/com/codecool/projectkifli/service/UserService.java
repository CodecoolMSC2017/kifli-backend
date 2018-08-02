package com.codecool.projectkifli.service;

import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> get(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User add(String username, String email, String password, String confirmPassword, String firstName, String lastName) {
        System.out.println(password);
        System.out.println(confirmPassword);
        System.out.println(username);
        System.out.println(email);
        System.out.println(firstName);
        System.out.println(lastName);
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

    public Optional<User> get(Integer id) {
        return userRepository.findById(id);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public void changePassword(String oldPassword, String newPassword, String confirmationPassword) {
        if (!newPassword.equals(confirmationPassword)) {
            throw new IllegalArgumentException();
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        userDetailsManager.changePassword(oldPassword, encodedNewPassword);
    }
}
