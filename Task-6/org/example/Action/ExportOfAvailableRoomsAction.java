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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class ExportOfAvailableRoomsAction implements IAction {
    private RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();

    public ExportOfAvailableRoomsAction(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        List<Room> rooms = roomManager.getRooms().values().stream()
                .filter(room -> !room.isOccupied())
                .collect(Collectors.toList());
        String filePath = "src/main/java/org/example/data/exports/availableRooms.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, false))) {
            String[] header = {"ID", "Room Number", "Room Status", "Price", "Room Type", "Capacity", "Number of Stars"};
            writer.writeNext(header);
            for (Room room : rooms) {
                String[] roomData = {
                        String.valueOf(room.getId()),
                        String.valueOf(room.getRoomNumber()),
                        room.getRoomStatus().toString(),
                        String.valueOf(room.getPrise()),
                        room.getRoomType().toString(),
                        String.valueOf(room.getCapasity()),
                        String.valueOf(room.getNumberOfStars())
                };
                writer.writeNext(roomData);
            }
            writer.flush();
        } catch (IOException e) {
            consoleView.print("Error writing to file: " + e.getMessage());
        }
    }
}