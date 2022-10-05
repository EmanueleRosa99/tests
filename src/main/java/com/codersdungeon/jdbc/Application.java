package com.codersdungeon.jdbc;

import com.codersdungeon.jdbc.model.User;
import com.codersdungeon.jdbc.repository.DBRepository;
import com.codersdungeon.jdbc.repository.DogRepository;
import com.codersdungeon.jdbc.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
            Connection connection = DBRepository.getConnection();
            DBRepository dbRepo = new UserRepository();
            List<Object> paramsList = new ArrayList<>();
            //  paramsList.add("Aldo");
            paramsList.add(3);
            LOG.info("{}",dbRepo.generateQuery(connection, "SELECT * FROM users WHERE id_user = ?", paramsList));
            DBRepository.close(connection);
    }
}
