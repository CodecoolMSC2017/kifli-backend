package com.codecool.projectkifli.dto;

import com.codecool.projectkifli.model.User;

public class UserDto {

    private Integer id;
    private String accountName;
    private String email;
    private String firstName;
    private String lastName;
    private String type;

    public UserDto(User user) {
        this.id = user.getId();
        this.accountName = user.getAccountName();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.type = user.getType();
    }

    public Integer getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getType() {
        return type;
    }
}
