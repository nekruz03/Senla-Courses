package org.example.Action;

import annotation.OwnInject;
import model.Room;
import model.RoomManager;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

import java.util.ArrayList;
import java.util.List;

public class ViewTheLastThreeGuestsAction implements IAction {
    @OwnInject
    RoomManager roomManager;
    @OwnInject
    private ConsoleView consoleView;
    public ViewTheLastThreeGuestsAction() throws IllegalAccessException {
        DI.infectDependencies(this);
    }
    @Override
    public void execute() {
        List currentGuests = roomManager.getLastThreeGuests();
        List<Room> lastThreeGuests = new ArrayList<>();
        for (int i = currentGuests.size() - 1; i >= 0 && lastThreeGuests.size() < 3; i--) {
            lastThreeGuests.add((Room) currentGuests.get(i));
        }
        consoleView.printList(lastThreeGuests);
    }
}
