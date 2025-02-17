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
    public  static  final String NUMBER_OF_GUESTS_IN_ROOM = "SELECT guest.id AS guest_id, guest.guest_name " +
            "FROM guest " +
            "JOIN guest_record ON guest.id = guest_record.guest_id " +
            "WHERE guest_record.status = 'ACTIVE' AND guest_record.room_number = ?";

    public  static  final String ROOM_QUERY = """
            SELECT r.price, gr.date_of_occupation, gr.date_of_eviction
            FROM room r
            LEFT JOIN guest_record gr ON r.room_number = gr.room_number
            WHERE r.room_number = ?;
        """;

    public  static final String SERVICES_QUERY = """
            SELECT st.price FROM service_type st
            JOIN room_service rs ON st.id = rs.service_type_id
            WHERE rs.room_number = ?;
        """;


    @Override
    public int save(Room roomEntity) {
        try (PreparedStatement preparedStatement = ConnectionManager.connect().prepareStatement(SAVE_ROOM)) {
            preparedStatement.setInt(1, roomEntity.getRoomNumber());
            preparedStatement.setString(2, roomEntity.getRoomStatus());
            preparedStatement.setString(3, roomEntity.getRoomType());
            preparedStatement.setInt(4, roomEntity.getCapacity());
            preparedStatement.setInt(5, roomEntity.getNumberOfStars());
            preparedStatement.setDouble(6, roomEntity.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomEntity.getRoomNumber();
    }

    @Override
    public void update(Room roomEntity) {
        Room existingRoom = findById(roomEntity.getRoomNumber());
        if (existingRoom == null) {
            throw new RuntimeException("Room not found with number: " + roomEntity.getRoomNumber());
        }

        if (roomEntity.getRoomStatus() != null)
            existingRoom.setRoomStatus(roomEntity.getRoomStatus());

        if (roomEntity.getRoomType() != null)
            existingRoom.setRoomType(roomEntity.getRoomType());

        if (roomEntity.getCapacity() != null)
            existingRoom.setCapacity(roomEntity.getCapacity());

        if (roomEntity.getNumberOfStars() != null)
            existingRoom.setNumberOfStars(roomEntity.getNumberOfStars());

        if (roomEntity.getPrice() != null)
            existingRoom.setPrice(roomEntity.getPrice());

        try (PreparedStatement preparedStatement = ConnectionManager.connect().prepareStatement(UPDATE_ROOM)) {
            preparedStatement.setString(1, existingRoom.getRoomStatus());
            preparedStatement.setString(2, existingRoom.getRoomType());
            preparedStatement.setInt(3, existingRoom.getCapacity());
            preparedStatement.setInt(4, existingRoom.getNumberOfStars());
            preparedStatement.setDouble(5, existingRoom.getPrice());
            preparedStatement.setInt(6, existingRoom.getRoomNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Room roomEntity) {
        try (Connection connection = ConnectionManager.connect()) {
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

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Room findById(int id) {
        try (PreparedStatement preparedStatement = ConnectionManager.connect().prepareStatement(GET_ROOM_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int roomNumber = resultSet.getInt("room_number");
            String roomStatus = resultSet.getString("room_status");
            String roomType = resultSet.getString("room_type");
            int capacity = resultSet.getInt("capacity");
            int numberOfStars = resultSet.getInt("number_of_stars");
            double price = resultSet.getDouble("price");

            return new Room(roomNumber, roomStatus, roomType, capacity, numberOfStars, price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = ConnectionManager.connect();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    public List<Room> getRoomsSortedByNumber() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(SELECT_LIST_OF_ROOMS_SORTED_BY_NUMBER)) {

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    public List<Room> getRoomsSortedByPrice() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(SELECT_LIST_OF_ROOMS_SORTED_BY_PRICE)) {

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    public void addServicesInToRoom() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to add Service:\n"));
        String serviceInput = consoleView.getInput("Enter the ID of the service you want to add to the room (separated by spaces): ");
        String[] serviceIds = serviceInput.trim().split("\\s+");
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(InsertInToRoomServices)) {
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
        } catch (SQLException e) {
            throw new RuntimeException("Error while adding services to room", e);
        }
    }

    public double calculate() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Enter room number for price calculation:\n"));
        double totalCost = 0;
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement roomStatement = connection.prepareStatement(ROOM_QUERY);
             PreparedStatement servicesStatement = connection.prepareStatement(SERVICES_QUERY)) {

            roomStatement.setInt(1, roomNumber);
            ResultSet roomResult = roomStatement.executeQuery();

            if (roomResult.next()) {
                double roomPrice = roomResult.getDouble("price");
                Date occupationDateSql = roomResult.getDate("date_of_occupation");
                Date evictionDateSql = roomResult.getDate("date_of_eviction");

                if (occupationDateSql == null || evictionDateSql == null) {
                    consoleView.display("No guest record found for this room.");
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

            } else {
                consoleView.display("Room not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error while calculating total cost: " + e.getMessage(), e);
        }
        return totalCost;
    }

    public  void numberOfGuestsInRoom(){
        int roomNumber = Integer.parseInt(consoleView.getInput("Enter the room number to see the number of guests:\n"));

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(NUMBER_OF_GUESTS_IN_ROOM)) {

            statement.setInt(1, roomNumber);
            ResultSet resultSet = statement.executeQuery();

            int guestCount = 0;

            while (resultSet.next()) {
                int guestId = resultSet.getInt("guest_id");
                String guestName = resultSet.getString("guest_name");

                System.out.println("Guest ID: " + guestId + ", Name: " + guestName);
                guestCount++;
            }

            System.out.println("Total number of active guests in room " + roomNumber + " - " + guestCount);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  Room getRoomFromConsole(){
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
