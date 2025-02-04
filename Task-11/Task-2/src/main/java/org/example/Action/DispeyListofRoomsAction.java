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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DispeyListofRoomsAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;

    @Override
    public void execute() {
        String query = "SELECT r.room_number, r.room_status, r.room_type, r.capacity, " +
                "r.number_of_stars, r.prise, " +
                "COALESCE(string_agg(s.name, ', '), 'No services') AS services " +
                "FROM room r " +
                "LEFT JOIN room_service rs ON r.room_number = rs.room_number " +
                "LEFT JOIN service_type s ON rs.service_type_id = s.id " +
                "GROUP BY r.room_number, r.room_status, r.room_type, r.capacity, r.number_of_stars, r.prise";

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
                String services = resultSet.getString("services"); // Получаем строку с сервисами

                System.out.println("Room Number: " + roomNumber +
                        ", Status: " + roomStatus +
                        ", Type: " + roomType +
                        ", Capacity: " + capacity +
                        ", Stars: " + numberOfStars +
                        ", Price: " + price +
                        ", Services: " + services);
            }

            consoleView.SuccessfulNotification();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
