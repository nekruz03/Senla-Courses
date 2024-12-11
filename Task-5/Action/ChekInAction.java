package Action;

import model.Room;
import model.RoomManager;
import viev.ConsoleView;
import util.IAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChekInAction implements IAction {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();

    public  ChekInAction(RoomManager roomManager){
        this.roomManager = roomManager;
    }
    @Override
    public void execute() throws ParseException {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to check in guest:\n"));
        Room room = roomManager.getRooms().get(roomNumber);
        String guestName = consoleView.getInput("Enter guest name:\n");
        String passportNumber = consoleView.getInput("Enter passport number:\n");
        String dateInput = consoleView.getInput("Enter check-in date in the format dd.MM.yyyy:\n");
        Date dateOfOccupation = dateFormat.parse(dateInput);
        String date = consoleView.getInput("Enter check-out date in the format dd.MM.yyyy:\n");
        Date dateOfEviction = dateFormat.parse(date);
        room.setOccupied(true);
        room.setGuestName(guestName);
        room.setPassportNumber(passportNumber);
        room.setDateOfOccupation(dateOfOccupation);
        room.setDateOfEviction(dateOfEviction);
        consoleView.SuccessfulNotification();

    }
}
