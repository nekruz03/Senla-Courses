package org.example.Action;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetListOfVailableRoomsInDateAction implements IAction {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @OwnInject
    private ConsoleView consoleView;
    @Override
    public void execute() throws ParseException {
        String input = consoleView.getInput("Enter Date in the format dd.MM.yyyy:\n");

        Date date = dateFormat.parse(input);

        String sqlRequest = "SELECT DISTINCT r.room_number\n" +
                "FROM room r\n" +
                "LEFT JOIN guest_record gr ON r.room_number = gr.room_number\n" +
                "WHERE gr.room_number IS NULL OR ? > gr.date_of_eviction;\n";

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            statement.setDate(1, new java.sql.Date(date.getTime()));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("On the specified date, there are no available rooms.");
                } else {
                    System.out.println("Available rooms:");
                    while (resultSet.next()) {
                        int roomNumber = resultSet.getInt("room_number");
                        if (roomNumber >= 1) {System.out.println(roomNumber);}

                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
