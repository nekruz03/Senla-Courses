package dao.model.connection;

import annotation.ConfigProperty;
import annotation.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ConnectionManager.class);

    @ConfigProperty(propertyName = "url")
    private static String url;
    static {
        try {
            Configurator.configureStatic(ConnectionManager.class);
            logger.info("Configured URL: {}", url);
        } catch (IllegalAccessException e) {
            logger.error("Error initializing static fields", e);
            throw new RuntimeException("Error initializing static fields", e);
        }
    }

    public static Connection connect() {
        try {
            logger.info("Establishing a connection to the database");
            Connection connection = DriverManager.getConnection(url);
            logger.info("Connection to the database is established");
            return connection;
        } catch (SQLException e) {
            logger.error("Error connecting to database", e);
            throw new RuntimeException("Error connecting to database", e);
        }
    }
}
