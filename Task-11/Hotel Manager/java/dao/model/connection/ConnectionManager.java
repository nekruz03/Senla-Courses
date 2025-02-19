package dao.model.connection;

import annotation.ConfigProperty;
import annotation.Configurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    @ConfigProperty(propertyName = "url")
    private static String url;

    static {
        try {
            Configurator.configureStatic(ConnectionManager.class);
            System.out.println("Configured URL: " + url);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error initializing static fields", e);
        }
    }

    public static Connection connect() {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database", e);
        }
    }
}
