package org.example.Action;

import model.Room;
import model.RoomManager;
import util.IAction;
import viev.ConsoleView;

import java.util.ArrayList;
import java.util.List;

public class ViewTheLastThreeGuestsAction implements IAction {
    RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();

    public ViewTheLastThreeGuestsAction(RoomManager roomManager){
        this.roomManager = roomManager;
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
