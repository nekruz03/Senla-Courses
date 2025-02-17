package org.example.App.org.example.App;

import dao.model.connection.ConnectionManager;
import org.example.Contoller.MenuController;
import util.DI.DI;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
public class HotelManager {
    public static void main(String[] args) throws ParseException, IOException, IllegalAccessException, SQLException {
        DI di = DI.getInstance();
        Connection connection = ConnectionManager.connect();
        di.registerBean(Connection.class, connection);
        MenuController menuController = di.create(MenuController.class);
        menuController.run();
    }
}
