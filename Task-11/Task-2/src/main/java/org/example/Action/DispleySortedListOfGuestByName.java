package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class DispleySortedListOfGuestByName implements IAction {
    @OwnInject
    private ConsoleView consoleView ;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        String query = "SELECT guest.id AS guest_id, guest.guest_name,guest_record.status " +
                "FROM guest " +
                "JOIN guest_record ON guest.id = guest_record.guest_id ORDER BY guest.guest_name ASC";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            int guestCount = 0;

            while (resultSet.next()) {
                int guestId = resultSet.getInt("guest_id");
                String guestName = resultSet.getString("guest_name");
                String status = resultSet.getString("status");

                System.out.println("Guest ID: " + guestId + ", Name: " + guestName + ", Status: " + status);
                guestCount++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        consoleView.SuccessfulNotification();


    }
}
