package org.example.App;

import annotation.Configurator;
import model.RoomManager;
import org.example.Action.*;
import org.example.Contoller.MenuController;
import util.Builder;
import util.DI.DI;
import util.Navigator;
import util.serialization.RoomManagerSerialization;
import viev.ConsoleView;

import java.io.IOException;
import java.text.ParseException;
public class HotelManager {
    public static void main(String[] args) throws ParseException, IOException, IllegalAccessException {
        DI di = DI.getInstance();
        MenuController menuController = di.create(MenuController.class);
        menuController.run();
    }
}
