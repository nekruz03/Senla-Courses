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
import java.util.List;
import java.util.stream.Collectors;

public class ExportGuestsList implements IAction {
    private RoomManager roomManager;
    private ConsoleView consoleView = new ConsoleView();

    public ExportGuestsList(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        List<Room> rooms = roomManager.getRooms().values().stream()
                .filter(room -> room.getGuestName()!=null)
                .collect(Collectors.toList());
        String filePath = "src/main/java/org/example/data/exports/guestListExport";
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, false))) {
            String[] header = {"ID", "Room Number","Name", "Surname", "Passport","Type", "DateOfOccupation", "DateOfEviction"};
            writer.writeNext(header);
            for (Room room : rooms) {
                String[] roomData = {
                        String.valueOf(room.getId()),
                        String.valueOf(room.getRoomNumber()),
                        String.valueOf(room.getGuestName()),
                        String.valueOf(room.getGuestSurname()),
                        String.valueOf(room.getPassportNumber()),
                        room.getRoomType().toString(),
                        String.valueOf(room.getDateOfOccupation()),
                        String.valueOf(room.getDateOfEviction())
                };
                writer.writeNext(roomData);
            }
            writer.flush();
        } catch (IOException e) {
            consoleView.print("Error writing to file: " + e.getMessage());
        }
    }
    }

