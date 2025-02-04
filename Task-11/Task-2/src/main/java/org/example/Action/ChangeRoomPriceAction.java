package org.example.Action;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangeRoomPriceAction implements IAction {
    @OwnInject
    private ConsoleView consoleView ;
    public ChangeRoomPriceAction() throws IllegalAccessException {}
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to  change price:\n"));
        double price =  Double.parseDouble(consoleView.getInput("Enter new price:\n"));
        String sqlUpdate = "UPDATE room SET prise = ?::text::money WHERE room_number = ?";
        try (Connection connection = ConnectionManager.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setDouble(1, price);
            preparedStatement.setInt(2,roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        consoleView.SuccessfulNotification();
    }

}
