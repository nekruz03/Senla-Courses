package dao.model.connection;

import annotation.ConfigProperty;
import annotation.Configurator;
import annotation.OwnInject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    @ConfigProperty(propertyName = "database.url")
    private static String url ;
    public static Connection connect() {
        try {
            Configurator.configure(ConnectionManager.class);
            System.out.println(url);
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database\"", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
