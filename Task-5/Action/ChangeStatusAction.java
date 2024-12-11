package Action;

import Enum.RoomStatus;
import model.RoomManager;
import util.Auxiliary;
import util.IAction;
import viev.ConsoleView;

public class ChangeStatusAction implements IAction {
    RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();

    public  ChangeStatusAction(RoomManager roomManager){
        this.roomManager = roomManager;
    }
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to changeStatus:\n"));
        int choseRoomStatus = Integer.parseInt(consoleView.getInput("Chose new room status: SERVICED(1), REPAIRABLE(2):\n"));
        RoomStatus roomStatus = Auxiliary.getRoomStatusByNumber(choseRoomStatus);
        roomManager.getRooms().get(roomNumber).setRoomStatus(roomStatus);
        consoleView.SuccessfulNotification();
    }
}
