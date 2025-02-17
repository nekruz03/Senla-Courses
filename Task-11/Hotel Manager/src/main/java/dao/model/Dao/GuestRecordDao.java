package dao.model.Dao;

import annotation.OwnInject;
import dao.model.GenericDao;
import dao.model.connection.ConnectionManager;
import dao.model.entity.GuestRecord;
import dao.model.entity.Room;
import viev.ConsoleView;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GuestRecordDao implements GenericDao<GuestRecord> {
    public static final String INSERT_GUEST_RECORD = "INSERT INTO guest_record (room_number, guest_id, date_of_occupation, date_of_eviction, status,lived_in_room) VALUES (?, ?, ?, ?, 'ACTIVE',?) RETURNING room_number";
    public static final String DELETE_GUEST_ROCORD = "UPDATE guest_record SET status = 'FINISHED' WHERE room_number = ? ";
    public  static final String SELECT_GUEST_RECORD_BY_ID = "SELECT * FROM guest_record WHERE room_number = ?";
    public static final String UPDATE_GUEST_RECORD = "UPDATE guest_record SET room_number = ?, guest_id = ?, date_of_occupation = ?, date_of_eviction = ?, status = ? WHERE id = ? ";
    public  static  final String SELECT_ALL_GUEST_RECORDS = "SELECT * FROM guest_record";

    public int save(GuestRecord entity) {
        Connection connection  = ConnectionManager.connect();
        try (PreparedStatement insertGuestRecordStmt = connection.prepareStatement(INSERT_GUEST_RECORD)) {
            insertGuestRecordStmt.setInt(1, entity.getRoomNumber());
            insertGuestRecordStmt.setInt(2, entity.getGuestId());
            insertGuestRecordStmt.setDate(3, new java.sql.Date(entity.getDateOfOccupation().getTime()));
            insertGuestRecordStmt.setDate(4, new java.sql.Date(entity.getDateOfEviction().getTime()));
            insertGuestRecordStmt.setInt(5, entity.getRoomNumber());
            ResultSet guestResult = insertGuestRecordStmt.executeQuery();
            if (guestResult.next()) {
                int guestId = guestResult.getInt("room_number");
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
    public void update(GuestRecord entity) {
        GuestRecord existingRecord = findById(entity.getId());

        if (entity.getRoomNumber() != 0) {
            existingRecord.setRoomNumber(entity.getRoomNumber());
        }
        if (entity.getGuestId() != 0) {
            existingRecord.setGuestId(entity.getGuestId());
        }
        if (entity.getDateOfOccupation() != null) {
            existingRecord.setDateOfOccupation(entity.getDateOfOccupation());
        }
        if (entity.getDateOfEviction() != null) {
            existingRecord.setDateOfEviction(entity.getDateOfEviction());
        }
        if (entity.getStatus() != null) {
            existingRecord.setStatus(entity.getStatus());
        }
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GUEST_RECORD)) {
            preparedStatement.setInt(1, existingRecord.getRoomNumber());
            preparedStatement.setInt(2, existingRecord.getGuestId());
            preparedStatement.setDate(3, new java.sql.Date(existingRecord.getDateOfOccupation().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(existingRecord.getDateOfEviction().getTime()));
            preparedStatement.setString(5, existingRecord.getStatus());
            preparedStatement.setInt(6, existingRecord.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(GuestRecord entity) {

    }
    @Override
    public GuestRecord findById(int id) {
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GUEST_RECORD_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                int guestId = resultSet.getInt("guest_id");
                java.util.Date dateOfOccupation = resultSet.getDate("date_of_occupation");
                java.util.Date dateOfEviction = resultSet.getDate("date_of_eviction");
                String status = resultSet.getString("status");
                return new GuestRecord(roomNumber, guestId, dateOfOccupation, dateOfEviction, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<GuestRecord> findAll() {
        List<GuestRecord> guestRecords = new ArrayList<>();

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_GUEST_RECORDS);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guestRecords;
    }

    @OwnInject
    ConsoleView consoleView;
    public GuestRecord getGuestRecordFromConsole (int id) throws ParseException {
        int roomNumber = Integer.parseInt(consoleView.getInput("Enter room number you want to check in guest:\n"));
        int guestId = id;
        String dateInput = consoleView.getInput("Enter check-in date in the format dd.MM.yyyy:\n");
        String dateOutput = consoleView.getInput("Enter check-out date in the format dd.MM.yyyy:\n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date parsedDateInput = dateFormat.parse(dateInput);
        java.util.Date parsedDateOutput = dateFormat.parse(dateOutput);
        String status = "ACTIVE";
        return new GuestRecord(roomNumber,guestId,parsedDateInput,parsedDateOutput,status);
    }

    public void deleteGuestRecord(Room room) {
        Connection connection  = ConnectionManager.connect();
        try (PreparedStatement insertGuestRecordStmt = connection.prepareStatement(DELETE_GUEST_ROCORD)) {
            insertGuestRecordStmt.setInt(1, room.getRoomNumber());
            insertGuestRecordStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  void exportGuestRecordsToCSV(String filePath) {
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_GUEST_RECORDS);
             ResultSet resultSet = preparedStatement.executeQuery();
             FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.append("room_number,guest_id,date_of_occupation,date_of_eviction,status\n");
            boolean hasData = false;
            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                int guestId = resultSet.getInt("guest_id");
                java.sql.Date dateOfOccupation = resultSet.getDate("date_of_occupation");
                java.sql.Date dateOfEviction = resultSet.getDate("date_of_eviction");
                String status = resultSet.getString("status");
                String dateOccupationString = (dateOfOccupation != null) ? dateOfOccupation.toString() : "";
                String dateEvictionString = (dateOfEviction != null) ? dateOfEviction.toString() : "";
                fileWriter.append(String.format("%d,%d,%s,%s,%s\n",
                        roomNumber,
                        guestId,
                        dateOccupationString,
                        dateEvictionString,
                        status != null ? status : ""));

                hasData = true;
            }
            if (hasData) {
                System.out.println("Data successfully exported to file: " + filePath);
            } else {
                System.out.println("No data found to export.");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error when exporting data: " + e.getMessage());
        }
    }
}
