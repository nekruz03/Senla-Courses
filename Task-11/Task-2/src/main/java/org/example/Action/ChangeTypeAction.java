package org.example.Action;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import org.example.Enum.RoomType;
import util.Auxiliary;
import util.IAction;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangeTypeAction implements IAction {

    @OwnInject
    private ConsoleView consoleView ;
    public ChangeTypeAction() throws IllegalAccessException {}
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to changeType:\n"));
        String sqlDelete =  "DELETE FROM room WHERE room_number = ?";
        try (Connection connection = ConnectionManager.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        consoleView.SuccessfulNotification();
    }
}
