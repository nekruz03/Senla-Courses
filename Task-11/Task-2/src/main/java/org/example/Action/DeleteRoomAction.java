package org.example.Action;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRoomAction implements IAction {

    @OwnInject
    private ConsoleView consoleView;

    public DeleteRoomAction() throws IllegalAccessException {}

    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to delete:\n"));

        String sqlUpdateGuestRecord = "UPDATE guest_record SET room_number = NULL WHERE room_number = ?";

        String sqlDeleteRoom = "DELETE FROM room WHERE room_number = ?";

        try (Connection connection = ConnectionManager.connect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateGuestRecord)) {
                preparedStatement.setInt(1, roomNumber);
                preparedStatement.executeUpdate();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteRoom)) {
                preparedStatement.setInt(1, roomNumber);
                preparedStatement.executeUpdate();
            }

            consoleView.SuccessfulNotification();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
