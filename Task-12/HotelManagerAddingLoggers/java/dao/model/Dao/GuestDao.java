package dao.model.Dao;

import annotation.OwnInject;
import dao.model.GenericDao;
import dao.model.connection.ConnectionManager;
import dao.model.entity.Guest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestDao implements GenericDao<Guest> {

    @OwnInject
    private ConsoleView consoleView;
    Connection connection = ConnectionManager.connect() ;
    private static final String INSERT_GUEST = "INSERT INTO guest (guest_name, guest_surname, passport_number) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_GUEST = "UPDATE guest SET guest_name = ?, guest_surname = ?, passport_number = ? WHERE id = ?";
    private static final String SELECT_GUEST_BY_ID = "SELECT * FROM guest WHERE id = ?";
    private static final String SELECT_ALL_GUESTS = "SELECT * FROM guest";
    private static final String DELETE_GUEST_BY_ID = "DELETE FROM guest WHERE id = ?";
    private  static final Logger logger = LoggerFactory.getLogger(GuestDao.class);
    @Override
    public int save(Guest guestEntity) {
        logger.info("Attempting to save guest: {}", guestEntity.getGuestName());
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GUEST)) {

            preparedStatement.setString(1, guestEntity.getGuestName());
            preparedStatement.setString(2, guestEntity.getGuestSurname());
            preparedStatement.setString(3, guestEntity.getPassportNumber());

            ResultSet guestResult = preparedStatement.executeQuery();
            if (guestResult.next()) {
                int guestId = guestResult.getInt("id");
                logger.info("Guest saved successfully with ID: {}", guestId);
                return guestId;
            } else {
                logger.error("Error while saving guest: {}", guestEntity.getGuestName());
                throw new SQLException("Error while saving guest: " + guestEntity.getGuestName());
            }
        } catch (SQLException e) {
            logger.error("Database error while saving guest: {}", guestEntity.getGuestName(), e);
            throw new RuntimeException("Database error while saving guest", e);
        }
    }

    @Override
    public void update(Guest entity) {
        logger.info("Attempting to update guest: {}", entity.getId());
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GUEST)) {

            preparedStatement.setString(1, entity.getGuestName());
            preparedStatement.setString(2, entity.getGuestSurname());
            preparedStatement.setString(3, entity.getPassportNumber());
            preparedStatement.setInt(4, entity.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Updating guest failed, no rows affected for ID: {}", entity.getId());
                throw new SQLException("Updating guest failed, no rows affected.");
            }else {
                logger.info("Updating guest successfully with ID: {}", entity.getId());
            }
        } catch (SQLException e) {
            logger.error("Database error while updating guest: {}", entity.getId(), e);
            throw new RuntimeException("Error while updating guest with id: " + entity.getId(), e);
        }
        logger.info("Updating guest successfully with ID: {}", entity.getId());
    }

    @Override
    public void delete(Guest entity) {
        logger.info("Attempting to delete guest: {}", entity.getId());
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GUEST_BY_ID)) {

            preparedStatement.setInt(1, entity.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Deleting guest failed, no rows affected for ID: {}", entity.getId());
                throw new SQLException("Deleting guest failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Database error while deleting guest: {}", entity.getId(), e);
            throw new RuntimeException("Error while deleting guest with id: " + entity.getId(), e);
        }
        logger.info("Deleting guest successfully with ID: {}", entity.getId());
    }

    @Override
    public Guest findById(int id) {
        logger.info("Attempting to find guest by ID: {}", id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GUEST_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
                String guestName = "";
                String guestSurname = "";
                String passportNumber = "";
                if (!resultSet.next()) {
                    logger.error("Guest not found with ID: {}", id);
                    throw new SQLException("Guest not found");
                }
                resultSet.getInt("id");
                guestName = resultSet.getString("guest_name");
                        guestSurname = resultSet.getString("guest_surname");
                        resultSet.getString("passport_number");
            return new Guest(id, guestName,guestSurname,passportNumber);
        } catch (SQLException e) {
            logger.error("Database error while finding guest: {}", id, e);
            throw new RuntimeException("Error retrieving guest with id: " + id, e);
        }
    }
    @Override
    public List<Guest> findAll() {
        logger.info("Attempting to find guests");
        List<Guest> guests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GUESTS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                guests.add(new Guest(
                        resultSet.getInt("id"),
                        resultSet.getString("guest_name"),
                        resultSet.getString("guest_surname"),
                        resultSet.getString("passport_number")
                ));
            }
            if (guests.size() == 0) {
                logger.error("Guests not found");
                throw new SQLException("Guests not found");
            }
        } catch (SQLException e) {
            logger.error("Database error while finding guests", e);
            throw new RuntimeException("Error retrieving guests", e);
        }
        logger.info("Guests found");
        return guests;
    }
    public boolean guestExists(String passportNumber) {
        logger.info("Attempting to find guest by passport number: {}", passportNumber);
        String query = "SELECT COUNT(*) FROM guest WHERE passport_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, passportNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                logger.info("Guest found with passport number: {}", passportNumber);
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.error("Database error while guest exists", e);
            throw new RuntimeException("Error checking if guest exists", e);
        }
        logger.info("Guest does not exist");
        return false;
    }

    public Guest getGuestFromConsole() {
        logger.info("Attempting to get Guest from console");
        String guestName = consoleView.getInput("Enter guest name:");
        String guestSurname = consoleView.getInput("Enter guest surname:");
        String passportNumber = consoleView.getInput("Enter passport number:");
        Guest guestEntity = new Guest(guestName, guestSurname, passportNumber);
        logger.info("Guest created: {}", guestEntity);
        return guestEntity;
    }
}
