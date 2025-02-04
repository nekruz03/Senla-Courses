package org.example.Action;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class NumberOfFreeRoomsAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;

    public NumberOfFreeRoomsAction() throws IllegalAccessException {
    }

    @Override
    public void execute() {
        String freeRoomsQuery = "SELECT ARRAY_AGG(room_number) AS free_rooms, COUNT(*) AS free_count " +
                "FROM room " +
                "WHERE room_number NOT IN (SELECT room_number FROM guest_record WHERE status = 'ACTIVE')";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(freeRoomsQuery);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                String freeRooms = resultSet.getString("free_rooms");
                int freeCount = resultSet.getInt("free_count");
                if (freeRooms == null) {
                    consoleView.print("Available rooms: 0");
                    consoleView.print("Number of available rooms: 0");
                } else {
                    consoleView.print("Available rooms: " + freeRooms);
                    consoleView.print("Number of available rooms: " + freeCount);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
