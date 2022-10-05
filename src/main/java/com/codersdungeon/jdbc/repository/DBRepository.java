package com.codersdungeon.jdbc.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public abstract class DBRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DBRepository.class);
    public static Connection getConnection(){
        Properties properties = new Properties();
        try {
            try (InputStream in = DBRepository.class.getClassLoader().getResourceAsStream("database.properties")) {
                properties.load(in);
            }
            catch (IOException e) {
                LOG.error("Errore lettura file", e);
                throw e;
            }
            String dbUrl = properties.getProperty("database.url");
            String dbUser = properties.getProperty("database.username");
            String dbPass = properties.getProperty("database.password");
            try {
                return DriverManager.getConnection(dbUrl, dbUser, dbPass);
            } catch (SQLException e) {
                throw e;
            }
        }catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Object generateQuery(Connection connection, String sql, List<Object> paramsList) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            String[] operation = sql.split(" ");
            return executeQuery(operation[0], connection, statement, paramsList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    abstract Object executeQuery(String operation, Connection connection, PreparedStatement statement, List<Object> paramsList);
}
