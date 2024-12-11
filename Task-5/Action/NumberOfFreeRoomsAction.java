package Action;

import model.Room;
import model.RoomManager;
import viev.ConsoleView;
import util.IAction;

import java.text.ParseException;
import java.util.List;

public class NumberOfFreeRoomsAction implements IAction {
    RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();
    public NumberOfFreeRoomsAction(RoomManager roomManager){
        this.roomManager = roomManager;
    }
    @Override
    public void execute() {
        List<Room> rooms = roomManager.getAllRooms();
        long count = rooms.stream()
                .filter(room -> !room.isOccupied())
                .count();
        consoleView.display(count);
    }
}
