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

public class DispeyListofRoomsActionSortedByNumber implements IAction {
    @OwnInject
    ConsoleView consoleView;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        String query = "SELECT * FROM room order by room_number";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                String roomStatus = resultSet.getString("room_status");
                String roomType = resultSet.getString("room_type");
                int capacity = resultSet.getInt("capacity");
                int numberOfStars = resultSet.getInt("number_of_stars");
                double price = resultSet.getDouble("prise");
                System.out.println("room number: " + roomNumber + ", room status: " + roomStatus + ", roomType: " + roomType +
                        ", capacity: " + capacity + ", number_of_stars: " + numberOfStars + ", price: " + price);
            }
            consoleView.SuccessfulNotification();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
