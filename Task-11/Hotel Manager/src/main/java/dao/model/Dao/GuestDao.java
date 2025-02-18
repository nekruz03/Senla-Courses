package dao.model.Dao;

import annotation.OwnInject;
import dao.model.GenericDao;
import dao.model.connection.ConnectionManager;
import dao.model.entity.Guest;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestDao implements GenericDao<Guest> {

    @OwnInject
    private ConnectionManager connectionManager;

    @OwnInject
    private ConsoleView consoleView;

    private static final String INSERT_GUEST = "INSERT INTO guest (guest_name, guest_surname, passport_number) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_GUEST = "UPDATE guest SET guest_name = ?, guest_surname = ?, passport_number = ? WHERE id = ?";
    private static final String SELECT_GUEST_BY_ID = "SELECT * FROM guest WHERE id = ?";
    private static final String SELECT_ALL_GUESTS = "SELECT * FROM guest";
    private static final String DELETE_GUEST_BY_ID = "DELETE FROM guest WHERE id = ?";

    @Override
    public int save(Guest guestEntity) {
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GUEST)) {

            preparedStatement.setString(1, guestEntity.getGuest_name());
            preparedStatement.setString(2, guestEntity.getGuest_surname());
            preparedStatement.setString(3, guestEntity.getPassport_number());

            ResultSet guestResult = preparedStatement.executeQuery();
            if (guestResult.next()) {
                return guestResult.getInt("id");
            } else {
                throw new SQLException("Error while saving guest: " + guestEntity.getGuest_name());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while saving guest", e);
        }
    }

    @Override
    public void update(Guest entity) {

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GUEST)) {

            preparedStatement.setString(1, entity.getGuest_name());
            preparedStatement.setString(2, entity.getGuest_surname());
            preparedStatement.setString(3, entity.getPassport_number());
            preparedStatement.setInt(4, entity.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating guest failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating guest with id: " + entity.getId(), e);
        }
    }

    @Override
    public void delete(Guest entity) {
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GUEST_BY_ID)) {

            preparedStatement.setInt(1, entity.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting guest failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting guest with id: " + entity.getId(), e);
        }
    }

    @Override
    public Guest findById(int id) {
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GUEST_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
                String guestName = "";
                String guestSurname = "";
                String passportNumber = "";
            if (resultSet.next()) {
                resultSet.getInt("id");
                guestName = resultSet.getString("guest_name");
                        guestSurname = resultSet.getString("guest_surname");
                        resultSet.getString("passport_number");
            } else {
                return null;
            }
            return new Guest(id, guestName,guestSurname,passportNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving guest with id: " + id, e);
        }
    }

    @Override
    public List<Guest> findAll() {
        List<Guest> guests = new ArrayList<>();
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GUESTS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                guests.add(new Guest(
                        resultSet.getInt("id"),
                        resultSet.getString("guest_name"),
                        resultSet.getString("guest_surname"),
                        resultSet.getString("passport_number")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving guests", e);
        }
        return guests;
    }

    public Guest getGuestFromConsole() {
        String guestName = consoleView.getInput("Enter guest name:");
        String guestSurname = consoleView.getInput("Enter guest surname:");
        String passportNumber = consoleView.getInput("Enter passport number:");
        return new Guest(guestName, guestSurname, passportNumber);
    }
}
