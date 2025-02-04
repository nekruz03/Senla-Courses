package org.example.Action;

import annotation.OwnInject;
import dao.model.Room;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChekOutAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
    public ChekOutAction() throws IllegalAccessException {}
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to check out guest:\n"));
        String sqlDelete = "UPDATE guest_record SET status = 'FINISHED' WHERE room_number = ? ";
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

