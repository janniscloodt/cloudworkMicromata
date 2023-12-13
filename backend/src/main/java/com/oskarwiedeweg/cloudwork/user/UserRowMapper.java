package com.oskarwiedeweg.cloudwork.user;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    private final boolean prefixed;

    public UserRowMapper() {
        this(false);
    }

    public UserRowMapper(boolean prefixed) {
        this.prefixed = prefixed;
    }

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                isThere(resultSet, column("id")) ? resultSet.getLong(column("id")) : null,
                isThere(resultSet, column("name")) ? resultSet.getString(column("name")) : null,
                isThere(resultSet, column("email")) ? resultSet.getString(column("email")) : null,
                isThere(resultSet, column("password")) ? resultSet.getString(column("password")) : null,
                isThere(resultSet, column("created_at")) ? resultSet.getDate(column("created_at")).toLocalDate() : null
        );
    }

    private String column(String name) {
        return (prefixed ? "user_" : "") + name;
    }

    private boolean isThere(ResultSet rs, String column){
        try{
            rs.findColumn(column);
            return true;
        } catch (SQLException ignored){
        }

        return false;
    }

}
