package Action;

import Enum.RoomType;
import model.RoomManager;
import viev.ConsoleView;
import util.IAction;

public class ChangeTypeAction implements IAction {
    RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();
    public ChangeTypeAction(RoomManager roomManager) {
        this.roomManager = roomManager;
    }
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to changeType:\n"));
        int choseRoomType = Integer.parseInt(consoleView.getInput("Chose new room type: STANDARD(1),DELUXE(2), VIP(3):\n"));
        RoomType roomType = getRoomTypeByNumber(choseRoomType);
        roomManager.getRooms().get(roomNumber).setRoomType(roomType);
        consoleView.SuccessfulNotification();

    }
    public RoomType getRoomTypeByNumber(int n ){
        if (n == 1){ return RoomType.STANDARD;}
        else if (n == 2){ return RoomType.DELUXE;}
        else if (n == 3){ return RoomType.VIP;}
        return null;
    }

}
