package org.example.Action;

import org.example.Enum.ServiceType;
import model.Room;
import model.RoomManager;
import util.IAction;
import viev.ConsoleView;

import java.util.ArrayList;
import java.util.List;

public class AddingAdditionalServicesAction implements IAction {
    RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();

    public  AddingAdditionalServicesAction(RoomManager roomManager){
        this.roomManager = roomManager;
    }
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to add Services:\n"));
        Room room = roomManager.getRooms().get(roomNumber);
        consoleView.display("Please enter the number of services you want to add (Whith probe ): BREAKFAST(1), LYNCH(2), DINNER(3), CLEANING(4), SPA(5)\n");
        String input = consoleView.getInput("Enter service numbers:\n");
        String[] inputNumbers = input.split("\\s+");
        List<Integer> serviceNumbers = new ArrayList<>();
        List<ServiceType> serviceTypes = new ArrayList<>();
        for (String number : inputNumbers){
            serviceNumbers.add(Integer.parseInt(number));
        }
        if (!serviceNumbers.isEmpty()){
            for (int i = 0; i < serviceNumbers.size(); i++){
                int serviceNumber = serviceNumbers.get(i);
                if (serviceNumber == 1){serviceTypes.add(ServiceType.BREAKFAST);}
                else if (serviceNumber == 2){serviceTypes.add(ServiceType.LYNCH);}
                else if (serviceNumber == 3){serviceTypes.add(ServiceType.CLEANING);}
                else if (serviceNumber == 4){serviceTypes.add(ServiceType.SPA);}
            }
        }
        room.addServiceType(serviceTypes);
        consoleView.SuccessfulNotification();
    }
}
