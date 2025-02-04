package org.example.Action;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DispleySortedServiceTypesByPrise implements IAction {
    @OwnInject
    private ConsoleView consoleView ;
    public DispleySortedServiceTypesByPrise() throws IllegalAccessException {}
    @Override
    public void execute() {
        int serviceTypeId = 0;
        String serviceTypeName = "";
        double price = 0;
        String query = "SELECT id, name, prise FROM service_type ORDER BY prise";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                serviceTypeId = resultSet.getInt("id");
                serviceTypeName = resultSet.getString("name");
                price =  resultSet.getDouble("prise");
                System.out.println("Service ID: " + serviceTypeId + ", Name: " + serviceTypeName + ", Price: " + price);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        consoleView.SuccessfulNotification();
    }
}
