package Action;

import Enum.ServiceType;
import viev.ConsoleView;
import util.IAction;

import java.text.ParseException;
import java.util.List;

public class DispleyServiceTypesAction implements IAction {
    private ConsoleView consoleView = new ConsoleView();

    @Override
    public void execute() throws ParseException {
        List<ServiceType> serviceTypes = ServiceType.getServiceTypeList();
        consoleView.display(serviceTypes);
    }
}
