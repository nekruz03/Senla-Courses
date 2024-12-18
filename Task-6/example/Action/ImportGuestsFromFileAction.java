package org.example.Action;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import model.Room;
import model.RoomManager;
import org.example.Enum.RoomStatus;
import util.IAction;
import viev.ConsoleView;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ImportGuestsFromFileAction implements IAction {
    private RoomManager roomManager;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private ConsoleView consoleView = new ConsoleView();
    public ImportGuestsFromFileAction(RoomManager roomManager) {
        this.roomManager = roomManager;
    }
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        CSVReader csvReader = new CSVReaderBuilder(new FileReader("src/main/java/org/example/data/imports/guests_import.csv"))
                .withSkipLines(1)
                .build();
        String[] lineInArray;
        while ((lineInArray = csvReader.readNext()) != null) {
            int roomNumber = Integer.parseInt(lineInArray[0]);
            Room room = roomManager.getRooms().get(roomNumber);
            String guestName = lineInArray[1];
            String guestSurname = lineInArray[2];
            String passportNumber = lineInArray[3];
            String dateInput = lineInArray[4];
            Date dateOfOccupation = dateFormat.parse(dateInput);
            String date = lineInArray[5];
            Date dateOfEviction = dateFormat.parse(date);
            if (room.getGuestName() == null && !room.getIsOccupied() && room.getRoomStatus() == RoomStatus.SERVICED){
                room.setOccupied(true);
                room.setGuestName(guestName);
                room.setGuestSurname(guestSurname);
                room.setPassportNumber(passportNumber);
                room.setDateOfOccupation(dateOfOccupation);
                room.setDateOfEviction(dateOfEviction);
            }
            else {
               consoleView.print("Failed to add, " + Arrays.toString(lineInArray));
            }
    }
        consoleView.SuccessfulNotification();
}
}
