package Action;

import Enum.ServiceType;
import model.Room;
import model.RoomManager;
import util.IAction;
import viev.ConsoleView;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PriseCalculationAction implements IAction {
    private ConsoleView consoleView = new ConsoleView();
    RoomManager roomManager;
    public PriseCalculationAction(RoomManager roomManager) {
        this.roomManager=roomManager;
    }
    @Override
    public void execute() throws ParseException {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to calculate prise:\n"));
        double ans = 0;
        Room room = roomManager.getRooms().get(roomNumber);
        LocalDate dateOfOccupation = room.getDateOfOccupation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateOfEviction = room.getDateOfEviction().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<ServiceType> serviceTypes = room.getServiceTypes();
        double sum = serviceTypes.stream()
                .mapToDouble(ServiceType::getPrise)
                .sum();
        int colDays = (int) ChronoUnit.DAYS.between(dateOfOccupation, dateOfEviction);
        ans = (room.getPrise() + sum)* colDays;
        consoleView.display(ans);
    }
}
