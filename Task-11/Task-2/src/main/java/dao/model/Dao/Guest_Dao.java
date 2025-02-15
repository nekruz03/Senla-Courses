package dao.model.Dao;

import annotation.OwnInject;
import dao.model.GenericDao;
import dao.model.connection.ConnectionManager;
import dao.model.entity.Guest;
import dao.model.entity.Room;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class Guest_Dao implements GenericDao<Guest> {
    public static final String INSET_GUEST = "INSERT INTO guest (guest_name, guest_surname, passport_number) VALUES (?, ?, ?) RETURNING id";
    public static final String  UPDATE_GUEST ="UPDATE guest SET guest_name = ?, guest_surname = ?, passport_number = ? WHERE id = ? ";
    private static final String SELECT_GUEST_BY_ID = "SELECT * FROM guest WHERE id = ?";
    public static final String  SELECT_ALL_GUESTS ="SELECT * FROM guest";
    public static final String DELETE_GUEST_BY_ID ="DELETE FROM guest WHERE id = ?";
    @OwnInject
    ConsoleView consoleView;
    public int save(Guest guestEntity) {
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSET_GUEST)) {
            preparedStatement.setString(1, guestEntity.getGuest_name());
            preparedStatement.setString(2, guestEntity.getGuest_surname());
            preparedStatement.setString(3, guestEntity.getPassport_number());
            ResultSet guestResult = preparedStatement.executeQuery();
            if (guestResult.next()) {
                int guestId = guestResult.getInt("id");
                return guestId;
            } else {
                consoleView.display("Error: Failed to register guest.");
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Guest entity) {
        Guest existingGuest = findById(entity.getId());
        if (existingGuest == null) {
            throw new RuntimeException("Gues not found with id: " + existingGuest.getId());
        }
        if (entity.getGuest_name() != null)
            existingGuest.setGuest_name(entity.getGuest_name());

        if (entity.getGuest_name() != null)
            existingGuest.setGuest_surname(entity.getGuest_surname());

        if (entity.getPassport_number() != null)
            existingGuest.setPassport_number(entity.getPassport_number());

        try (PreparedStatement preparedStatement = ConnectionManager.connect().prepareStatement(UPDATE_GUEST)) {
            preparedStatement.setString(1, existingGuest.getGuest_name());
            preparedStatement.setString(2, existingGuest.getGuest_name());
            preparedStatement.setString(3, existingGuest.getPassport_number());
            preparedStatement.setInt(4, existingGuest.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Guest entity) {
        try (PreparedStatement preparedStatement = ConnectionManager.connect().prepareStatement(DELETE_GUEST_BY_ID)) {

            preparedStatement.setInt(1, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Guest findById(int id) {
        try (PreparedStatement preparedStatement = ConnectionManager.connect().prepareStatement(SELECT_GUEST_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int guestId = resultSet.getInt("id");
                String name = resultSet.getString("guest_name");
                String surname = resultSet.getString("guest_surname");
                String passportNumber = resultSet.getString("passport_number");
                return new Guest(guestId, name, surname, passportNumber);
            } else {
                System.out.println("Guest with id " + id + " not found.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Guest> findAll() {
        List<Guest> guests = new ArrayList<>();
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GUESTS)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String guestName = resultSet.getString("guest_name");
                String  guestSurname = resultSet.getString("guest_surname");
                String passportNumber = resultSet.getString("passport_number");
                guests.add(new Guest(id , guestName, guestSurname, passportNumber));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return guests;
    }
    public Guest getGuestFromConsole() {
        String guestName = consoleView.getInput("Enter guest name:\n");
        String guestSurname = consoleView.getInput("Enter guest surname:\n");
        String passportNumber = consoleView.getInput("Enter passport number:\n");
        return new Guest(guestName, guestSurname, passportNumber);
    }
}
