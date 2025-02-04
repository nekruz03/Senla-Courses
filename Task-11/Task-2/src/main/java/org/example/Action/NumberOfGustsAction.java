package org.example.Action;

import annotation.OwnInject;
import dao.model.Room;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NumberOfGustsAction implements IAction {
    @OwnInject
    private ConsoleView consoleView ;
    @OwnInject
    public NumberOfGustsAction() throws IllegalAccessException {}
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Enter the room number to see the number of guests:\n"));

        String query = "SELECT guest.id AS guest_id, guest.guest_name " +
                "FROM guest " +
                "JOIN guest_record ON guest.id = guest_record.guest_id " +
                "WHERE guest_record.status = 'ACTIVE' AND guest_record.room_number = ?";

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, roomNumber);
            ResultSet resultSet = statement.executeQuery();

            int guestCount = 0;

            while (resultSet.next()) {
                int guestId = resultSet.getInt("guest_id");
                String guestName = resultSet.getString("guest_name");

                System.out.println("Guest ID: " + guestId + ", Name: " + guestName);
                guestCount++;
            }

            System.out.println("Total number of active guests in room " + roomNumber + " - " + guestCount);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
