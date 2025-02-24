package dao.model.Dao;

import annotation.OwnInject;
import dao.model.GenericDao;
import dao.model.connection.ConnectionManager;
import dao.model.entity.Room;
import util.Auxiliary;
import viev.ConsoleView;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RoomDao implements GenericDao<Room> {
    @OwnInject
    ConsoleView consoleView;
    private  Connection connect = ConnectionManager.connect() ;
    public static final String SAVE_ROOM = "INSERT INTO room(room_number, room_status, room_type, capacity, number_of_stars, price) VALUES (?,?,?,?, ?,?::text::money )";
    public static final String UPDATE_ROOM = "UPDATE room SET  room_status = ?, room_type = ?, capacity = ?, number_of_stars = ?, price = ?::text::money WHERE room_number = ?";
    public static final String UPDATE_GUEST_RECORD = "UPDATE guest_record SET room_number = NULL, status ='ACTIVE'  WHERE room_number = ?";
    public static final String DELETE_ROOM_SERVICES_FOR_DEL_ROOM = "DELETE FROM room_service WHERE room_number = ?";
    public static final String DELETE_ROOM = "DELETE FROM room WHERE room_number = ?";
    public static final String GET_ROOM_BY_ID = "SELECT * FROM room where room_number = ?";
    public static final String SELECT_LIST_OF_ROOMS = "SELECT * FROM room";
    public static final String SELECT_LIST_OF_ROOMS_SORTED_BY_NUMBER = "SELECT * FROM room order by room_number";
    public static final String SELECT_LIST_OF_ROOMS_SORTED_BY_PRICE = "SELECT * FROM room order by price";
    public static final String InsertInToRoomServices = "INSERT INTO room_service (room_number, service_type_id) VALUES(?,?)";
    public static final String NUMBER_OF_GUESTS_IN_ROOM = "SELECT guest.id AS guest_id, guest.guest_name " +
            "FROM guest " +
            "JOIN guest_record ON guest.id = guest_record.guest_id " +
            "WHERE guest_record.status = 'ACTIVE' AND guest_record.room_number = ?";

    public static final String ROOM_QUERY = """
            SELECT r.price, gr.date_of_occupation, gr.date_of_eviction
            FROM room r
            LEFT JOIN guest_record gr ON r.room_number = gr.room_number
            WHERE r.room_number = ?;
        """;

    public static final String SERVICES_QUERY = """
            SELECT st.price FROM service_type st
            JOIN room_service rs ON st.id = rs.service_type_id
            WHERE rs.room_number = ?;
        """;



    @Override
    public int save(Room roomEntity) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(SAVE_ROOM)) {
            preparedStatement.setInt(1, roomEntity.getRoomNumber());
            preparedStatement.setString(2, roomEntity.getRoomStatus());
            preparedStatement.setString(3, roomEntity.getRoomType());
            preparedStatement.setInt(4, roomEntity.getCapacity());
            preparedStatement.setInt(5, roomEntity.getNumberOfStars());
            preparedStatement.setDouble(6, roomEntity.getPrice());

            int resultSet = preparedStatement.executeUpdate();
            if (resultSet == 0) throw new SQLException("Something went wrong");
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving room", e);
        }
        return roomEntity.getRoomNumber();
    }

    @Override
    public void update(Room roomEntity) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_ROOM)) {
            preparedStatement.setString(1, roomEntity.getRoomStatus());
            preparedStatement.setString(2, roomEntity.getRoomType());
            preparedStatement.setInt(3, roomEntity.getCapacity());
            preparedStatement.setInt(4, roomEntity.getNumberOfStars());
            preparedStatement.setDouble(5, roomEntity.getPrice());
            preparedStatement.setInt(6, roomEntity.getRoomNumber());
            int affectedRows =  preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating  room failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating room", e);
        }
    }


    @Override
    public void delete(Room roomEntity) {
        try (Connection connection = connect) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GUEST_RECORD)) {
                preparedStatement.setInt(1, roomEntity.getRoomNumber());
                preparedStatement.executeUpdate();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROOM_SERVICES_FOR_DEL_ROOM)) {
                preparedStatement.setInt(1, roomEntity.getRoomNumber());
                preparedStatement.executeUpdate();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROOM)) {
                preparedStatement.setInt(1, roomEntity.getRoomNumber());
                int affectedRows  = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Room deletion failed, no rows affected for room number: " + roomEntity.getRoomNumber());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting room", e);
        }
    }

    @Override
    public Room findById(int id) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(GET_ROOM_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                String roomStatus = resultSet.getString("room_status");
                String roomType = resultSet.getString("room_type");
                int capacity = resultSet.getInt("capacity");
                int numberOfStars = resultSet.getInt("number_of_stars");
                double price = resultSet.getDouble("price");

                return new Room(roomNumber, roomStatus, roomType, capacity, numberOfStars, price);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding room by ID", e);
        }
    }



    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = connect;
             PreparedStatement statement = connection.prepareStatement(SELECT_LIST_OF_ROOMS)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                String roomStatus = resultSet.getString("room_status");
                String roomType = resultSet.getString("room_type");
                int capacity = resultSet.getInt("capacity");
                int numberOfStars = resultSet.getInt("number_of_stars");
                double price = resultSet.getDouble("price");
                rooms.add(new Room(roomNumber, roomStatus, roomType, capacity, numberOfStars, price));
            }
            if (rooms.size() == 0) { throw new SQLException("Rooms not found"); }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving all rooms", e);
        }
        return rooms;
    }

    public List<Room> getRoomsSortedByNumber() {
        return getRoomsSorted(SELECT_LIST_OF_ROOMS_SORTED_BY_NUMBER);
    }

    public List<Room> getRoomsSortedByPrice() {
        return getRoomsSorted(SELECT_LIST_OF_ROOMS_SORTED_BY_PRICE);
    }

    private List<Room> getRoomsSorted(String query) {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = connect;
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                String roomStatus = resultSet.getString("room_status");
                String roomType = resultSet.getString("room_type");
                int capacity = resultSet.getInt("capacity");
                int numberOfStars = resultSet.getInt("number_of_stars");
                double price = resultSet.getDouble("price");
                rooms.add(new Room(roomNumber, roomStatus, roomType, capacity, numberOfStars, price));
            }
            if (rooms.size() == 0) {throw new SQLException("Rooms not found"); }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving sorted rooms", e);
        }
        return rooms;
    }

    public void addServicesInToRoom() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Enter room number, you want to add Service:\n"));
        String serviceInput = consoleView.getInput("Enter the ID of the service you want to add to the room (separated by spaces): ");
        String[] serviceIds = serviceInput.trim().split("\\s+");

        try (PreparedStatement statement = connect.prepareStatement(InsertInToRoomServices)) {

            for (String serviceId : serviceIds) {
                try {
                    int serviceTypeId = Integer.parseInt(serviceId.trim());
                    statement.setInt(1, roomNumber);
                    statement.setInt(2, serviceTypeId);
                    statement.executeUpdate();
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid service ID", e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while adding services to room", e);
        }
    }

    public double calculate() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Enter room number for price calculation:\n"));
        double totalCost = 0;
        try (PreparedStatement roomStatement = connect.prepareStatement(ROOM_QUERY);
             PreparedStatement servicesStatement = connect.prepareStatement(SERVICES_QUERY)) {

            roomStatement.setInt(1, roomNumber);
            ResultSet roomResult = roomStatement.executeQuery();

            if (!roomResult.next()) throw new SQLException("Room not found");
            double roomPrice = roomResult.getDouble("price");
            Date occupationDateSql = roomResult.getDate("date_of_occupation");
            Date evictionDateSql = roomResult.getDate("date_of_eviction");

            if (occupationDateSql == null || evictionDateSql == null) {
                return 0;
            }
            LocalDate occupationDate = occupationDateSql.toLocalDate();
            LocalDate evictionDate = evictionDateSql.toLocalDate();
            int stayDays = (int) ChronoUnit.DAYS.between(occupationDate, evictionDate);

            servicesStatement.setInt(1, roomNumber);
            ResultSet servicesResult = servicesStatement.executeQuery();

            double serviceTotal = 0;
            while (servicesResult.next()) {
                serviceTotal += servicesResult.getDouble("price");
            }

            totalCost = (roomPrice + serviceTotal) * stayDays;

        } catch (SQLException e) {
            throw new RuntimeException("Database error while calculating total cost", e);
        }
        return totalCost;
    }

    public int  numberOfGuestsInRoom() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Enter the room number to see the number of guests:\n"));

        try (PreparedStatement statement = connect.prepareStatement(NUMBER_OF_GUESTS_IN_ROOM)) {

            statement.setInt(1, roomNumber);
            ResultSet resultSet = statement.executeQuery();

            int guestCount = 0;
            while (resultSet.next()) {
                guestCount++;
            }
            return guestCount;
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching guests in room", e);
        }
    }

    public Room getRoomFromConsole() {
        String roomNumberInput = consoleView.getInput("Enter room number (or press Enter to skip):\n");
        Integer roomNumber = roomNumberInput.isEmpty() ? null : Integer.parseInt(roomNumberInput);
        String choseRoomStatusInput = consoleView.getInput("Choose room status: SERVICED(1), REPAIRABLE(2) (or press Enter to skip):\n");
        String roomStatus = choseRoomStatusInput.isEmpty() ? null : String.valueOf(Auxiliary.getRoomStatusByNumber(Integer.parseInt(choseRoomStatusInput)));
        String choseRoomTypeInput = consoleView.getInput("Choose room type: STANDARD(1), DELUXE(2), VIP(3) (or press Enter to skip):\n");
        String roomType = choseRoomTypeInput.isEmpty() ? null : String.valueOf(Auxiliary.getRoomTypeByNumber(Integer.parseInt(choseRoomTypeInput)));
        String capacityInput = consoleView.getInput("Enter capacity (or press Enter to skip):\n");
        Integer capacity = capacityInput.isEmpty() ? null : Integer.parseInt(capacityInput);
        String numberOfStarsInput = consoleView.getInput("Enter number of stars (or press Enter to skip):\n");
        Integer numberOfStars = numberOfStarsInput.isEmpty() ? null : Integer.parseInt(numberOfStarsInput);
        String priceInput = consoleView.getInput("Enter price (or press Enter to skip):\n");
        Double price = priceInput.isEmpty() ? null : Double.parseDouble(priceInput);

        return new Room(roomNumber, roomStatus, roomType, capacity, numberOfStars, price);
    }
}
