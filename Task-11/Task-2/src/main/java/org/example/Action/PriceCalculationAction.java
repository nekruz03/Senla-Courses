package org.example.Action;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import util.IAction;
import viev.ConsoleView;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PriceCalculationAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;

    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Enter room number for price calculation:\n"));
        double totalCost = 0;

        String roomQuery = """
            SELECT r.prise, gr.date_of_occupation, gr.date_of_eviction
            FROM room r
            LEFT JOIN guest_record gr ON r.room_number = gr.room_number
            WHERE r.room_number = ?;
        """;

        String servicesQuery = """
            SELECT st.prise FROM service_type st
            JOIN room_service rs ON st.id = rs.service_type_id
            WHERE rs.room_number = ?;
        """;

        try (Connection connection = ConnectionManager.connect()) {
            connection.setAutoCommit(false); // Включаем транзакцию

            try (PreparedStatement roomStatement = connection.prepareStatement(roomQuery);
                 PreparedStatement servicesStatement = connection.prepareStatement(servicesQuery)) {

                roomStatement.setInt(1, roomNumber);
                ResultSet roomResult = roomStatement.executeQuery();

                if (roomResult.next()) {
                    double roomPrice = roomResult.getDouble("prise");
                    Date occupationDateSql = roomResult.getDate("date_of_occupation");
                    Date evictionDateSql = roomResult.getDate("date_of_eviction");

                    if (occupationDateSql == null || evictionDateSql == null) {
                        consoleView.display("No guest record found for this room.");
                        return;
                    }

                    LocalDate occupationDate = occupationDateSql.toLocalDate();
                    LocalDate evictionDate = evictionDateSql.toLocalDate();
                    int stayDays = (int) ChronoUnit.DAYS.between(occupationDate, evictionDate);

                    servicesStatement.setInt(1, roomNumber);
                    ResultSet servicesResult = servicesStatement.executeQuery();

                    double serviceTotal = 0;
                    while (servicesResult.next()) {
                        serviceTotal += servicesResult.getDouble("prise");
                    }

                    totalCost = (roomPrice + serviceTotal) * stayDays;
                    connection.commit();
                    consoleView.display("Total price: $" + totalCost);
                } else {
                    consoleView.display("Room not found.");
                }

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database connection error: " + e.getMessage(), e);
        }
    }
}
