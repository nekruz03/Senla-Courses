package org.example.Action;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import model.Room;
import model.RoomManager;
import util.IAction;
import viev.ConsoleView;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class ExportGuestsList implements IAction {
    private RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public ExportGuestsList(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        List<Room> rooms = roomManager.getRooms().values().stream()
                .filter(room -> room.getGuestName() != null)
                .collect(Collectors.toList());
        String filePath = "src/main/java/org/example/data/exports/guestListExport.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"Room Number", "Name", "Surname", "Passport", "Type", "DateOfOccupation", "DateOfEviction"};
            writer.writeNext(header);
            for (Room room : rooms) {
                String dateOfOccupation = room.getDateOfOccupation() != null
                        ? dateFormat.format(room.getDateOfOccupation())
                        : "";
                String dateOfEviction = room.getDateOfEviction() != null
                        ? dateFormat.format(room.getDateOfEviction())
                        : "";
                String[] roomData = {
                        String.valueOf(room.getRoomNumber()),
                        room.getGuestName(),
                        room.getGuestSurname(),
                        room.getPassportNumber(),
                        room.getRoomType().toString(),
                        dateOfOccupation,
                        dateOfEviction
                };
                writer.writeNext(roomData);
            }
            writer.flush();
            consoleView.print("Guest list has been successfully exported to: " + filePath);
        } catch (IOException e) {
            consoleView.print("Error writing to file: " + e.getMessage());
        }
    }
}
