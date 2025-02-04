package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

public class AddServiceType implements IAction {
    @OwnInject
    ConsoleView consoleView;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        String nameService = consoleView.getInput("Enter the name of the new service you want to add:\n");
        double price =  Double.parseDouble(consoleView.getInput("Enter price:\n"));
        String sql = "INSERT INTO service_type (name, prise) VALUES (?,?::text::money)";
        Connection connection = ConnectionManager.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nameService);
            preparedStatement.setDouble(2, price);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}