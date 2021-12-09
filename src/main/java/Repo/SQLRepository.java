package Repo;



import CustomExceptions.CustomExceptions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic Class that implements ICrudRepository
 * @param <T>: T can be any object
 */

public abstract class SQLRepository<T> implements ICrudRepository<T> {
    Connection connection;
    Statement statement;
    Statement secondStatement;

    public SQLRepository() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/labmap";
        String username = "root";
        String password = "961688";

        connection = DriverManager.getConnection(jdbcURL, username, password);
        statement = connection.createStatement();
        secondStatement = connection.createStatement();
    }

}
