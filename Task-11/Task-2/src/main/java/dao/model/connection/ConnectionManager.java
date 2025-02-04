package dao.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static Connection connect() {
        String url = "jdbc:postgresql://localhost:5432/Dz-11_2";

        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database\"", e);
        }
    }
}
