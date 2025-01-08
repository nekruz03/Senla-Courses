package org.example.Action;

import annotation.OwnInject;
import org.example.Enum.ServiceType;
import model.RoomManager;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

import java.util.Comparator;
import java.util.List;

public class SortedServiceTypesByPriseAction implements IAction {
    @OwnInject
    private ConsoleView consoleView ;
    @OwnInject
    RoomManager roomManager;
    public SortedServiceTypesByPriseAction() throws IllegalAccessException {
        DI.infectDependencies(this);
    }
    @Override
    public void execute()  {
        List<ServiceType> serviceTypes = ServiceType.getServiceTypeList();
        serviceTypes.sort(Comparator.comparingDouble(ServiceType::getPrise));
        consoleView.display(serviceTypes);
    }
}
