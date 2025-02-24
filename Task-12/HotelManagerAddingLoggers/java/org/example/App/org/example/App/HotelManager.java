package org.example.App.org.example.App;

import dao.model.connection.ConnectionManager;
import org.example.Contoller.MenuController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DI.DI;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
public class HotelManager {
    public static void main(String[] args) throws ParseException, IOException, IllegalAccessException, SQLException {
    Logger logger = LoggerFactory.getLogger(HotelManager.class);
       logger.info("Application is started");

       logger.info("Getting instance di");
       DI di = DI.getInstance();
       logger.info("Instance di received");

       logger.info("Establishing a connection to the database");
       Connection connection = ConnectionManager.connect();
       logger.info("Connection to the database is established");
       logger.info("Register connection to di ");
        di.registerBean(Connection.class, connection);
       MenuController menuController = di.create(MenuController.class);
        logger.info("MenuController instance created, starting menu...");
        menuController.run();
    }
}
