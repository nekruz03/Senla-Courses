package Action;

import Enum.ServiceType;
import model.RoomManager;
import viev.ConsoleView;
import util.IAction;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;

public class SortedServiceTypesByPriseAction implements IAction {
    private ConsoleView consoleView = new ConsoleView();
    RoomManager roomManager;
    public SortedServiceTypesByPriseAction(RoomManager roomManager) {
        this.roomManager=roomManager;
    }
    @Override
    public void execute()  {
        List<ServiceType> serviceTypes = ServiceType.getServiceTypeList();
        serviceTypes.sort(Comparator.comparingDouble(ServiceType::getPrise));
        consoleView.display(serviceTypes);
    }
}