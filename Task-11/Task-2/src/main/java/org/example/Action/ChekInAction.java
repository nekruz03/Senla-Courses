package org.example.Action;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ChekInAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;

    @Override
    public void execute() throws ParseException {
        int roomNumber = Integer.parseInt(consoleView.getInput("Enter room number you want to check in guest:\n"));
        String guestName = consoleView.getInput("Enter guest name:\n");
        String guestSurname = consoleView.getInput("Enter guest surname:\n");
        String passportNumber = consoleView.getInput("Enter passport number:\n");
        String dateInput = consoleView.getInput("Enter check-in date in the format dd.MM.yyyy:\n");
        String dateOutput = consoleView.getInput("Enter check-out date in the format dd.MM.yyyy:\n");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date parsedDateInput = dateFormat.parse(dateInput);
        java.util.Date parsedDateOutput = dateFormat.parse(dateOutput);

        try (Connection connection = ConnectionManager.connect()) {
            connection.setAutoCommit(false);

            String roomCheckQuery = """
                SELECT r.room_status, r.capacity, 
                       (SELECT COUNT(*) FROM guest_record gr WHERE gr.room_number = r.room_number AND gr.status = 'ACTIVE') AS current_guests
                FROM room r
                WHERE r.room_number = ?;
            """;

            try (PreparedStatement roomCheckStmt = connection.prepareStatement(roomCheckQuery)) {
                roomCheckStmt.setInt(1, roomNumber);
                ResultSet roomCheckResult = roomCheckStmt.executeQuery();

                if (!roomCheckResult.next()) {
                    consoleView.display("Error: Room not found.");
                    return;
                }

                String roomStatus = roomCheckResult.getString("room_status");
                int capacity = roomCheckResult.getInt("capacity");
                int currentGuests = roomCheckResult.getInt("current_guests");

                if (!"SERVICED".equals(roomStatus)) {
                    consoleView.display("Error: Room is not serviced.");
                    return;
                }

                if (currentGuests >= capacity) {
                    consoleView.display("Error: Room is at full capacity.");
                    return;
                }
            }

            String insertGuestQuery = "INSERT INTO guest (guest_name, guest_surname, passport_number) VALUES (?, ?, ?) RETURNING id";
            int guestId;
            try (PreparedStatement insertGuestStmt = connection.prepareStatement(insertGuestQuery)) {
                insertGuestStmt.setString(1, guestName);
                insertGuestStmt.setString(2, guestSurname);
                insertGuestStmt.setString(3, passportNumber);
                ResultSet guestResult = insertGuestStmt.executeQuery();
                if (!guestResult.next()) {
                    consoleView.display("Error: Failed to register guest.");
                    return;
                }
                guestId = guestResult.getInt("id");
            }
            String insertGuestRecordQuery = """
                INSERT INTO guest_record (room_number, guest_id, date_of_occupation, date_of_eviction, status)
                VALUES (?, ?, ?, ?, 'ACTIVE');
            """;
            try (PreparedStatement insertGuestRecordStmt = connection.prepareStatement(insertGuestRecordQuery)) {
                insertGuestRecordStmt.setInt(1, roomNumber);
                insertGuestRecordStmt.setInt(2, guestId);
                insertGuestRecordStmt.setDate(3, new java.sql.Date(parsedDateInput.getTime()));
                insertGuestRecordStmt.setDate(4, new java.sql.Date(parsedDateOutput.getTime()));
                insertGuestRecordStmt.executeUpdate();
            }

            connection.commit();
            consoleView.SuccessfulNotification();

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
