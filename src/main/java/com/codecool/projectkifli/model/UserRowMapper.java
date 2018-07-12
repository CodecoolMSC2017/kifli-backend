package com.codecool.projectkifli.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("id");
        String accountName = resultSet.getString("account_name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String firsName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String type = resultSet.getString("type");
        return new User(id, accountName, email, password, firsName, lastName, type);
    }
}
