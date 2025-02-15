package org.example.App.org.example.App;

import org.example.Contoller.MenuController;
import util.DI.DI;

import java.io.IOException;
import java.text.ParseException;
public class HotelManager {
    public static void main(String[] args) throws ParseException, IOException, IllegalAccessException {
        DI di = DI.getInstance();
        MenuController menuController = di.create(MenuController.class);
        menuController.run();
    }
}
