package org.example.Action;

import annotation.OwnInject;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import model.Room;
import model.RoomManager;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExportGuestsList implements IAction {
    @OwnInject
    private RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public ExportGuestsList() throws IllegalAccessException {}

    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        String filePath = "src/main/java/org/example/data/exports/guestListExport.csv";
        Map<String, String[]> existingGuests = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                existingGuests.put(line[0], line);
            }
        } catch (IOException e) {
            consoleView.print("File not found or empty, creating a new one.");
        }
        List<Room> rooms = roomManager.getRooms().values().stream()
                .filter(room -> room.getGuestName() != null)
                .collect(Collectors.toList());
        for (Room room : rooms) {
            String roomNumber = String.valueOf(room.getRoomNumber());
            String dateOfOccupation = room.getDateOfOccupation() != null
                    ? dateFormat.format(room.getDateOfOccupation())
                    : "";
            String dateOfEviction = room.getDateOfEviction() != null
                    ? dateFormat.format(room.getDateOfEviction())
                    : "";
            String[] roomData = {
                    roomNumber,
                    room.getGuestName(),
                    room.getGuestSurname(),
                    room.getPassportNumber(),
                    room.getRoomType().toString(),
                    dateOfOccupation,
                    dateOfEviction
            };
            existingGuests.put(roomNumber, roomData);
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"Room Number", "Name", "Surname", "Passport", "Type", "DateOfOccupation", "DateOfEviction"};
            writer.writeNext(header);
            for (String[] guestData : existingGuests.values()) {
                writer.writeNext(guestData);
            }
            writer.flush();
            consoleView.print("Guest list has been successfully exported to: " + filePath);
        } catch (IOException e) {
            consoleView.print("Error writing to file: " + e.getMessage());
        }
    }
}