package Action;

import model.RoomManager;
import util.IAction;
import viev.ConsoleView;

public class DeleteRoomAction implements IAction {
    RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();

    public  DeleteRoomAction(RoomManager roomManager){
        this.roomManager = roomManager;
    }
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to delete:\n"));
        roomManager.deleteRoom(roomNumber);
        consoleView.SuccessfulNotification();
    }
}
