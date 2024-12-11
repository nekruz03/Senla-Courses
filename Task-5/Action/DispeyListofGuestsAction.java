package Action;

import model.Room;
import model.RoomManager;
import viev.ConsoleView;
import util.IAction;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DispeyListofGuestsAction implements IAction {
    RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();
    public DispeyListofGuestsAction(RoomManager roomManager){
        this.roomManager = roomManager;
    }
    @Override
    public void execute()  {
        int number =  Integer.parseInt(consoleView.getInput("Select the option to display list of guests (1), " +
                "display guests  - sorted by Name(2), sorted by Date Of Eviction(3)\n"));
        List<Room> rooms = roomManager.getAllRooms();
        List<Room>tmp = rooms.stream()
                .filter(room -> room.getGuestName()!=null)
                .collect(Collectors.toList());

        List<Room>sortedRooms = new ArrayList<>(rooms);
        if (number == 1){consoleView.printList(tmp);}
        else if (number == 2){ sortedRooms.sort(Comparator.comparing(Room::getGuestName));  consoleView.printList(sortedRooms);}
        else if (number == 3) { sortedRooms.sort(Comparator.comparing(Room::getDateOfEviction));  consoleView.printList(sortedRooms);}
        consoleView.SuccessfulNotification();

    }
}
