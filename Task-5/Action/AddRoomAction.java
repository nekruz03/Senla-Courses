package Action;

import Enum.RoomStatus;
import Enum.RoomType;
import model.Room;
import model.RoomManager;
import viev.ConsoleView;
import util.IAction;

public class AddRoomAction implements IAction {
    private RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();

    public AddRoomAction(RoomManager roomManager) {
        this.roomManager = roomManager;
    }
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to addRoom:\n"));
        int choseRoomType = Integer.parseInt(consoleView.getInput("Chose room type: STANDARD(1),DELUXE(2), VIP(3):\n"));
        RoomType roomType = getRoomTypeByNumber(choseRoomType);
        int choseRoomStatus = Integer.parseInt(consoleView.getInput("Chose room status: SERVICED(1), REPAIRABLE(2):\n"));
        RoomStatus roomStatus = getRoomStatusByNumber(choseRoomStatus);
        double price = Double.parseDouble(consoleView.getInput("Enter price:\n"));
        int capacity = Integer.parseInt(consoleView.getInput("Enter capacity:\n"));
        int numberOfStars = Integer.parseInt(consoleView.getInput("Enter number of stars:\n"));
        Room room = new Room(roomNumber, roomType, roomStatus, price, capacity, numberOfStars);
        roomManager.addMapRoom(roomNumber, room);
        consoleView.SuccessfulNotification();
    }
    public RoomType getRoomTypeByNumber(int n ){
        if (n == 1){ return RoomType.STANDARD;}
        else if (n == 2){ return RoomType.DELUXE;}
        else if (n == 3){ return RoomType.VIP;}
        return null;
    }
    public RoomStatus getRoomStatusByNumber(int n ){
        if (n == 1){ return RoomStatus.SERVICED;}
        else if (n == 2) return RoomStatus.REPAIRABLE;
        return null;
    }

}
