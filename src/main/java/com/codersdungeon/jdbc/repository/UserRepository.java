package com.codersdungeon.jdbc.repository;

import com.codersdungeon.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends DBRepository {
    @Override
    Object executeQuery(String operation, Connection connection, PreparedStatement statement, List<Object> paramsList) {
        int result;
        User user = new User();
        try {
            switch (operation.toUpperCase()) {
                case "DELETE":
                    try {
                        statement.setInt(1, (Integer) paramsList.get(0));
                        int i = statement.executeUpdate();
                        connection.commit();
                        return i == 1;
                    } catch (SQLException e) {
                        connection.rollback();
                        throw e;
                    }
                case "UPDATE":
                    try {
                        statement.setString(1, (String) paramsList.get(0));
                        statement.setString(2, (String) paramsList.get(1));
                        statement.setInt(3, (Integer) paramsList.get(2));
                        result = statement.executeUpdate();
                        connection.commit();
                        return result;
                    } catch (SQLException e) {
                        connection.rollback();
                        throw e;
                    }
                case "INSERT":
                    try {
                        statement.setString(1, (String) paramsList.get(0));
                        statement.setString(2, (String) paramsList.get(1));
                        statement.executeUpdate();
                        PreparedStatement statement1 = connection.prepareStatement("SELECT MAX(id_user) FROM users");
                        ResultSet resultSet = statement1.executeQuery();
                        if (resultSet.next()) {
                            user.setId(resultSet.getInt(1));
                        }
                        user.setName((String) paramsList.get(0));
                        user.setLastName((String) paramsList.get(1));
                        connection.commit();
                        return user;
                    } catch (SQLException e) {
                        connection.rollback();
                        throw e;
                    }
                case "SELECT":
                    try {
                        if (paramsList.size() == 0) {
                            try (ResultSet resultSet = statement.executeQuery()) {
                                List<User> userList = new ArrayList<>();
                                while (resultSet.next()) {
                                    String name = resultSet.getString("name");
                                    String lastName = resultSet.getString("lastname");
                                    int idRead = resultSet.getInt("id_user");
                                    user = new User(idRead, name, lastName);
                                    userList.add(user);
                                }
                                connection.commit();
                                return userList;
                            }
                        }
                        statement.setInt(1, (Integer) paramsList.get(0));
                        try (ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()) {
                                String name = resultSet.getString("name");
                                String lastname = resultSet.getString("lastname");
                                int idRead = resultSet.getInt("id_user");
                                user = new User(idRead, name, lastname);
                            }
                            connection.commit();
                            return user;
                        }

                    }catch (SQLException e) {
                        connection.rollback();
                        throw e;
                    }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("ciao");
        return null;
    }
}
