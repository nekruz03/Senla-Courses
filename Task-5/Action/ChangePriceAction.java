package Action;

import model.RoomManager;
import util.IAction;
import viev.ConsoleView;

public class ChangePriceAction implements IAction {
    RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();

    public ChangePriceAction(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to changePrice:\n"));
        double price = Double.parseDouble(consoleView.getInput("Enter new price:\n"));
        roomManager.getRooms().get(roomNumber).setPrise(price);
        consoleView.SuccessfulNotification();
    }

}
