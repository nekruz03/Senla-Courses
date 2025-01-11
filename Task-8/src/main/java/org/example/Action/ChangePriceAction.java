package org.example.Action;

import annotation.OwnInject;
import model.RoomManager;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

public class ChangePriceAction implements IAction {
    @OwnInject
    RoomManager roomManager;
    @OwnInject
    private ConsoleView consoleView ;

    public ChangePriceAction() throws IllegalAccessException {}
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to changePrice:\n"));
        double price = Double.parseDouble(consoleView.getInput("Enter new price:\n"));
        roomManager.getRooms().get(roomNumber).setPrise(price);
        consoleView.SuccessfulNotification();
    }

}
