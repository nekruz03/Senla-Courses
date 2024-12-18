package org.example.Action;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import model.Room;
import model.RoomManager;
import org.example.Enum.RoomStatus;
import org.example.Enum.RoomType;
import util.IAction;
import viev.ConsoleView;

import java.io.FileReader;
import java.io.IOException;

public class ImportRoomsFromFileAction implements IAction {
    private RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();
    public ImportRoomsFromFileAction(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public void execute() throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReaderBuilder(new FileReader("src/main/java/org/example/data/imports/rooms_import.csv"))
                .withSkipLines(1)
                .build();

        String[] lineInArray;
        while ((lineInArray = csvReader.readNext()) != null) {
            int roomNumber = Integer.parseInt(lineInArray[0]);
            RoomType roomType = RoomType.valueOf(lineInArray[1].trim());
            RoomStatus roomStatus = RoomStatus.valueOf(lineInArray[2].trim());
            double price = Double.parseDouble(lineInArray[3].trim());
            int capacity = Integer.parseInt(lineInArray[4].trim());
            int numberOfStars = Integer.parseInt(lineInArray[5].trim());
            Room room = new Room(roomNumber, roomType, roomStatus, price, capacity, numberOfStars);
            roomManager.addMapRoom(roomNumber, room);
        }
        consoleView.SuccessfulNotification();
        csvReader.close();
    }
}
