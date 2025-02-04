package org.example.Action;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import util.Auxiliary;
import util.IAction;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangeStatusAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
    public  ChangeStatusAction() throws IllegalAccessException {}
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to Change Status:\n"));
        String choseRoomStatus = consoleView.getInput("Chose new room status: SERVICED or REPAIRABLE:\n");
        String sqlUpdate =  "UPDATE room SET room_status = ? WHERE room_number = ?";
        try (Connection connection = ConnectionManager.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, choseRoomStatus);
            preparedStatement.setInt(2,roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        consoleView.SuccessfulNotification();
    }
}
