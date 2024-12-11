package App;

import Contoller.MenuController;
import model.RoomManager;
import viev.ConsoleView;
import util.Builder;
import util.Navigator;

import java.text.ParseException;

public class HotelManager {
    public static void main(String[] args) throws ParseException {
        RoomManager roomManager = new RoomManager();
        Builder builder = new Builder();
        Navigator navigator = new Navigator();
        MenuController menuController = new MenuController(roomManager,navigator,builder);
        menuController.run();
    }
}
