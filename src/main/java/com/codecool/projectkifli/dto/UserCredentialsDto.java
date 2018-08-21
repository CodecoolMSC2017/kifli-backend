package com.codecool.projectkifli.dto;

import com.codecool.projectkifli.model.Credentials;
import com.codecool.projectkifli.model.User;

import java.util.List;

public class UserCredentialsDto {

    private Integer id;
    private String username;
    private String email;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private List<String> authorities;
    private Credentials credentials;

    public UserCredentialsDto() {
    }

    public UserCredentialsDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.enabled = user.getEnabled();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.authorities = user.getAuthorities();
        this.credentials = user.getCredentials();
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
