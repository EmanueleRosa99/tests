package com.codersdungeon.jdbc.repository;

import com.codersdungeon.jdbc.model.Dog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DogRepository extends DBRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DogRepository.class);
    @Override
    Object executeQuery(String operation, Connection connection, PreparedStatement statement, List<Object> paramsList) {
        int result;
        Dog dog = new Dog();
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
                        PreparedStatement statement1 = connection.prepareStatement("SELECT MAX(id_dog) FROM dogs");
                        ResultSet resultSet = statement1.executeQuery();
                        if (resultSet.next()) {
                            dog.setId(resultSet.getInt(1));
                        }
                        dog.setNome((String) paramsList.get(0));
                        dog.setRazza((String) paramsList.get(1));
                        connection.commit();
                        return dog;
                    } catch (SQLException e) {
                        connection.rollback();
                        throw e;
                    }
                case "SELECT":
                    try {
                        if (paramsList.size() == 0) {
                            try (ResultSet resultSet = statement.executeQuery()) {
                                List<Dog> dogList = new ArrayList<>();
                                while (resultSet.next()) {
                                    String name = resultSet.getString("nome");
                                    String razza = resultSet.getString("razza");
                                    int idRead = resultSet.getInt("id_dog");
                                    dog = new Dog(idRead, name, razza);
                                    dogList.add(dog);
                                }
                                connection.commit();
                                return dogList;
                            }
                        }
                        statement.setInt(1, (Integer) paramsList.get(0));
                        try (ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()) {
                                String name = resultSet.getString("nome");
                                String razza = resultSet.getString("razza");
                                int idRead = resultSet.getInt("id_dog");
                                dog = new Dog(idRead, name, razza);
                            }
                            connection.commit();
                            return dog;
                        }

                    }catch (SQLException e) {
                        connection.rollback();
                        throw e;
                    }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}


