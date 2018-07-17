package com.codecool.projectkifli;

import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String accountName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userRepository.findByAccountName(accountName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        List<String> authorities = new ArrayList<>();
        authorities.add(user.getType().toUpperCase());
        return new UsernamePasswordAuthenticationToken(
                accountName,
                password,
                authorities.stream().map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList()));
    }
}
