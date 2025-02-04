package org.example.Action;
import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import org.example.Enum.RoomStatus;
import org.example.Enum.RoomType;
import util.Auxiliary;
import util.IAction;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddRoomAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to addRoom:\n"));
        int choseRoomStatus = Integer.parseInt(consoleView.getInput("Chose room status: SERVICED(1), REPAIRABLE(2):\n"));
        RoomStatus roomStatus = Auxiliary.getRoomStatusByNumber(choseRoomStatus);
        int choseRoomType = Integer.parseInt(consoleView.getInput("Chose room type: STANDARD(1),DELUXE(2), VIP(3):\n"));
        RoomType roomType = Auxiliary.getRoomTypeByNumber(choseRoomType);
        int capacity = Integer.parseInt(consoleView.getInput("Enter capacity:\n"));
        int numberOfStars = Integer.parseInt(consoleView.getInput("Enter number of stars:\n"));
        double price = Double.parseDouble(consoleView.getInput("Enter price:\n"));
        String sql = "INSERT INTO room(room_number, room_status, room_type, capacity, number_of_stars, prise) VALUES (?,?,?,?, ?,?::text::money)";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.setString(2, String.valueOf(roomStatus));
            preparedStatement.setString(3, String.valueOf(roomType));
            preparedStatement.setInt(4, capacity);
            preparedStatement.setInt(5, numberOfStars);
            preparedStatement.setDouble(6, price);
            preparedStatement.executeUpdate();
            consoleView.SuccessfulNotification();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        consoleView.SuccessfulNotification();
    }
}