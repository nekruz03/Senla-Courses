package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
public class AddingAdditionalServicesAction implements IAction {
    @OwnInject
    DispleySortedServiceTypesByPrise displeySortedServiceTypesByPrise;
    @OwnInject
    ConsoleView consoleView;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to add Service:\n"));
        String sql = "INSERT INTO room_service (room_number, service_type_id) VALUES(?,?)";

        consoleView.print("List of available services\n");
        displeySortedServiceTypesByPrise.execute();

        String serviceInput = consoleView.getInput("Enter the ID of the service you want to add to the room (separated by spaces): ");

        String[] serviceIds = serviceInput.trim().split("\\s+");

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (String serviceId : serviceIds) {
                try {
                    int serviceTypeId = Integer.parseInt(serviceId.trim());
                    statement.setInt(1, roomNumber);
                    statement.setInt(2, serviceTypeId);
                    statement.executeUpdate();
                    consoleView.print("Added service ID " + serviceTypeId + " to room " + roomNumber);
                } catch (NumberFormatException e) {
                    consoleView.print("Invalid service ID: " + serviceId);
                }
            }
            consoleView.print("Service(s) successfully added to the room!");
        } catch (SQLException e) {
            throw new RuntimeException("Error while adding services to room", e);
        }
    }

}
