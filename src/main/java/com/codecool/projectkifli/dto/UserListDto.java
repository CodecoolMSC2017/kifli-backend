package com.codecool.projectkifli.dto;

import com.codecool.projectkifli.model.UserListItem;

import java.util.List;

public class UserListDto {

    private List<UserListItem> users;

    public List<UserListItem> getUsers() {
        return users;
    }

    public void setUsers(List<UserListItem> users) {
        this.users = users;
    }
}
