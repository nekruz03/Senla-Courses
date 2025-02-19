package dao.model.Dao;

import annotation.OwnInject;
import dao.model.GenericDao;
import dao.model.connection.ConnectionManager;
import dao.model.entity.GuestRecord;
import dao.model.entity.Room;
import viev.ConsoleView;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GuestRecordDao implements GenericDao<GuestRecord> {

    @OwnInject
    private ConsoleView consoleView;
    Connection connection =ConnectionManager.connect() ;
    public static final String INSERT_GUEST_RECORD = "INSERT INTO guest_record (room_number, guest_id, date_of_occupation, date_of_eviction, status,lived_in_room) VALUES (?, ?, ?, ?, 'ACTIVE',?) RETURNING room_number";
    public static final String DELETE_GUEST_ROCORD = "UPDATE guest_record SET status = 'FINISHED' WHERE room_number = ? ";
    public static final String SELECT_GUEST_RECORD_BY_ID = "SELECT * FROM guest_record WHERE room_number = ?";
    public static final String UPDATE_GUEST_RECORD = "UPDATE guest_record SET room_number = ?, guest_id = ?, date_of_occupation = ?, date_of_eviction = ?, status = ? WHERE id = ? ";
    public static final String SELECT_ALL_GUEST_RECORDS = "SELECT * FROM guest_record";


    @Override
    public int save(GuestRecord entity) {
        try (PreparedStatement insertGuestRecordStmt = connection.prepareStatement(INSERT_GUEST_RECORD)) {
            insertGuestRecordStmt.setInt(1, entity.getRoomNumber());
            insertGuestRecordStmt.setInt(2, entity.getGuestId());
            insertGuestRecordStmt.setDate(3, new Date(entity.getDateOfOccupation().getTime()));
            insertGuestRecordStmt.setDate(4, new Date(entity.getDateOfEviction().getTime()));
            insertGuestRecordStmt.setInt(5, entity.getRoomNumber());

            ResultSet guestResult = insertGuestRecordStmt.executeQuery();
            if (guestResult.next()) {
                return guestResult.getInt("room_number");
            } else {
                throw new SQLException("Failed to register guest. No result returned from the database.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving guest record", e);
        }
    }

    @Override
    public void update(GuestRecord entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GUEST_RECORD)) {
            preparedStatement.setInt(1, entity.getRoomNumber());
            preparedStatement.setInt(2, entity.getGuestId());
            preparedStatement.setDate(3, new Date(entity.getDateOfOccupation().getTime()));
            preparedStatement.setDate(4, new Date(entity.getDateOfEviction().getTime()));
            preparedStatement.setString(5, entity.getStatus());
            preparedStatement.setInt(6, entity.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating guest record failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating guest record with id: " + entity.getId(), e);
        }
    }

    @Override
    public void delete(GuestRecord entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GUEST_ROCORD)) {
            preparedStatement.setInt(1, entity.getRoomNumber());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting guest record failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting guest record with room number: " + entity.getRoomNumber(), e);
        }
    }

    @Override
    public GuestRecord findById(int id) {
        int roomNumber = 0;
        int guestId = 0 ;
        java.util.Date dateOfOccupation = null ;
        java.util.Date dateOfEviction  =null ;
        String status = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GUEST_RECORD_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw  new SQLException("Failed to find guest record with id: " + id);
            }
                 roomNumber = resultSet.getInt("room_number");
                 guestId = resultSet.getInt("guest_id");
                 dateOfOccupation = resultSet.getDate("date_of_occupation");
                 dateOfEviction = resultSet.getDate("date_of_eviction");
                 status = resultSet.getString("status");

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving guest record with id: " + id, e);
        }
        return new GuestRecord(roomNumber, guestId, dateOfOccupation, dateOfEviction, status);
    }

    @Override
    public List<GuestRecord> findAll() {
        List<GuestRecord> guestRecords = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_GUEST_RECORDS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                int guestId = resultSet.getInt("guest_id");
                java.util.Date dateOfOccupation = resultSet.getDate("date_of_occupation");
                java.util.Date dateOfEviction = resultSet.getDate("date_of_eviction");
                String status = resultSet.getString("status");
                GuestRecord guestRecord = new GuestRecord(roomNumber, guestId, dateOfOccupation, dateOfEviction, status);
                guestRecords.add(guestRecord);
            }
            if (guestRecords.size() == 0) {throw new SQLException("Failed to find all guest records.");}
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all guest records", e);
        }
        return guestRecords;
    }

    public GuestRecord getGuestRecordFromConsole(int id) throws ParseException {
        int roomNumber = Integer.parseInt(consoleView.getInput("Enter room number you want to check in guest:\n"));
        int guestId = id;
        String dateInput = consoleView.getInput("Enter check-in date in the format dd.MM.yyyy:\n");
        String dateOutput = consoleView.getInput("Enter check-out date in the format dd.MM.yyyy:\n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date parsedDateInput = dateFormat.parse(dateInput);
        java.util.Date parsedDateOutput = dateFormat.parse(dateOutput);
        String status = "ACTIVE";
        return new GuestRecord(roomNumber, guestId, parsedDateInput, parsedDateOutput, status);
    }

    public void deleteGuestRecord(Room room) {
        try (PreparedStatement insertGuestRecordStmt = connection.prepareStatement(DELETE_GUEST_ROCORD)) {
            insertGuestRecordStmt.setInt(1, room.getRoomNumber());
            int affectedRows = insertGuestRecordStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to delete guest record for room: " + room.getRoomNumber());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting guest record for room: " + room.getRoomNumber(), e);
        }
    }



}
