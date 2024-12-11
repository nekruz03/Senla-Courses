package Action;

import Enum.RoomStatus;
import model.RoomManager;
import viev.ConsoleView;
import util.IAction;

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
        RoomStatus roomStatus = getRoomStatusByNumber(choseRoomStatus);
        roomManager.getRooms().get(roomNumber).setRoomStatus(roomStatus);
        consoleView.SuccessfulNotification();
    }
    public RoomStatus getRoomStatusByNumber(int n ){
        if (n == 1){ return RoomStatus.SERVICED;}
        else if (n == 2) return RoomStatus.REPAIRABLE;
        return null;
    }
}
