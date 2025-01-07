package org.example.App;


import annotation.Configurator;
import com.opencsv.exceptions.CsvValidationException;
import model.RoomManager;
import org.example.Contoller.MenuController;
import util.Builder;
import util.Navigator;
import util.SerializationUtil;

import java.io.IOException;
import java.text.ParseException;

public class HotelManager {
    public static void main(String[] args) throws ParseException, CsvValidationException, IOException, IllegalAccessException {
        RoomManager roomManager = new RoomManager();
        Builder builder = new Builder();
        Navigator navigator = new Navigator();
        MenuController menuController = new MenuController(roomManager, navigator, builder);
        Configurator.configure(roomManager);
        menuController.run();
    }
}
