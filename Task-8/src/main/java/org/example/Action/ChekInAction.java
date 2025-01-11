package org.example.Action;

import annotation.OwnInject;
import model.Room;
import model.RoomManager;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChekInAction implements IAction {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    @OwnInject
    RoomManager roomManager;
    @OwnInject
    private ConsoleView consoleView ;
    public  ChekInAction() throws IllegalAccessException {}
    @Override
    public void execute() throws ParseException {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to check in guest:\n"));
        Room room = roomManager.getRooms().get(roomNumber);
        String guestName = consoleView.getInput("Enter guest name:\n");
        String guestSurname = consoleView.getInput("Enter guest surname:\n");
        String passportNumber = consoleView.getInput("Enter passport number:\n");
        String dateInput = consoleView.getInput("Enter check-in date in the format dd.MM.yyyy:\n");
        Date dateOfOccupation = dateFormat.parse(dateInput);
        String date = consoleView.getInput("Enter check-out date in the format dd.MM.yyyy:\n");
        Date dateOfEviction = dateFormat.parse(date);
        room.setOccupied(true);
        room.setGuestName(guestName);
        room.setGuestSurname(guestSurname);
        room.setPassportNumber(passportNumber);
        room.setDateOfOccupation(dateOfOccupation);
        room.setDateOfEviction(dateOfEviction);
        roomManager.addMapRoom(roomNumber,room);
        room.addGuestToHistory(guestName,guestSurname,passportNumber,dateOfOccupation,dateOfEviction);
        consoleView.SuccessfulNotification();
    }
}
