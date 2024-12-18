package org.example.Action;

import org.example.Enum.ServiceType;
import util.IAction;
import viev.ConsoleView;

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
