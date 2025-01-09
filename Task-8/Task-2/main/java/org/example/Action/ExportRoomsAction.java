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

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class ExportRoomsAction implements IAction {
    @OwnInject
    private RoomManager roomManager;
    @OwnInject
    private ConsoleView consoleView ;
    public ExportRoomsAction() throws IllegalAccessException {
        DI.infectDependencies(this);
    }
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        List<Room> rooms = roomManager.getRooms().values().stream()
                .collect(Collectors.toList());
        String filePath = "src/main/java/org/example/data/exports/Rooms.csv";
        File file = new File(filePath);

        Map<String, String[]> existingRooms = new HashMap<>();
        if (file.exists()) {
            try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
                String[] line;
                reader.readNext();
                while ((line = reader.readNext()) != null) {
                    existingRooms.put(line[0], line);
                }
            } catch (IOException e) {
                consoleView.print("Error reading file: " + e.getMessage());
            }
        }
        for (Room room : rooms) {
            String roomNumber = String.valueOf(room.getRoomNumber());
            String[] roomData = {
                    roomNumber,
                    room.getRoomStatus().toString(),
                    String.valueOf(room.getPrise()),
                    room.getRoomType().toString(),
                    String.valueOf(room.getCapasity()),
                    String.valueOf(room.getNumberOfStars())
            };

            existingRooms.put(roomNumber, roomData);
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"Room Number", "Room Status", "Price", "Room Type", "Capacity", "Number of Stars"};
            writer.writeNext(header);

            for (String[] roomData : existingRooms.values()) {
                writer.writeNext(roomData);
            }
            writer.flush();
            consoleView.print("Room list has been successfully exported to: " + filePath);
        } catch (IOException e) {
            consoleView.print("Error writing to file: " + e.getMessage());
        }
    }
}