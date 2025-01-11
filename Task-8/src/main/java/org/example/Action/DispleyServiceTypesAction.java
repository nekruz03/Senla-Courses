package org.example.Action;

import annotation.OwnInject;
import org.example.Enum.ServiceType;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

import java.text.ParseException;
import java.util.List;

public class DispleyServiceTypesAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
    public DispleyServiceTypesAction( ) throws IllegalAccessException {}
    @Override
    public void execute() throws ParseException {
        List<ServiceType> serviceTypes = ServiceType.getServiceTypeList();
        consoleView.display(serviceTypes);
    }
}
