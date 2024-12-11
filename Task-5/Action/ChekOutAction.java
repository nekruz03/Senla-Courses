package Action;

import model.Room;
import model.RoomManager;
import util.IAction;
import viev.ConsoleView;

public class ChekOutAction implements IAction {
    RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();

    public ChekOutAction(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to check out guest:\n"));
        Room room = roomManager.getRooms().get(roomNumber);
        if (room.getGuestName() != null) {
            room.setOccupied(false);
            room.setGuestName(null);
            room.setPassportNumber(null);
            room.setDateOfOccupation(null);
            room.setDateOfEviction(null);
            consoleView.SuccessfulNotification();
        }
    }
}
