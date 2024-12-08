package controller;
import model.Room;
import model.RoomManager;
import view.ConsoleView;
import Enum.RoomStatus;
import Enum.RoomType;
import Enum.ServiceType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    List<Room> currentGuests  = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private ConsoleView consoleView;
    private RoomManager roomManager;
    public Controller(ConsoleView consoleView,RoomManager roomManager) {
        this.consoleView = consoleView;
        this.roomManager = roomManager;
    }
    public void addRoom( ) {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to addRoom:\n"));
        int choseRoomType = Integer.parseInt(consoleView.getInput("Chose room type: STANDARD(1),DELUXE(2), VIP(3):\n"));
        RoomType roomType = getRoomTypeByNumber(choseRoomType);
        int choseRoomStatus = Integer.parseInt(consoleView.getInput("Chose room status: SERVICED(1), REPAIRABLE(2):\n"));
        RoomStatus roomStatus = getRoomStatusByNumber(choseRoomStatus);
        double price = Double.parseDouble(consoleView.getInput("Enter price:\n"));
        int capacity = Integer.parseInt(consoleView.getInput("Enter capacity:\n"));
        int numberOfStars = Integer.parseInt(consoleView.getInput("Enter number of stars:\n"));
        Room room = new Room(roomNumber, roomType, roomStatus, price, capacity, numberOfStars);
        roomManager.addMapRoom(roomNumber, room);
        consoleView.SuccessfulNotification();
    }
    public void changePrice() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to changePrice:\n"));
        double price = Double.parseDouble(consoleView.getInput("Enter new price:\n"));
        roomManager.getRooms().get(roomNumber).setPrise(price);
        consoleView.SuccessfulNotification();
    }
    public void changeType(){
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to changeType:\n"));
        int choseRoomType = Integer.parseInt(consoleView.getInput("Chose new room type: STANDARD(1),DELUXE(2), VIP(3):\n"));
        RoomType roomType = getRoomTypeByNumber(choseRoomType);
        roomManager.getRooms().get(roomNumber).setRoomType(roomType);
        consoleView.SuccessfulNotification();
    }
    public void deleteRoom() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to delete:\n"));
        roomManager.deleteRoom(roomNumber);
        consoleView.SuccessfulNotification();
    }
    public void changeStatus() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to changeStatus:\n"));
        int choseRoomStatus = Integer.parseInt(consoleView.getInput("Chose new room status: SERVICED(1), REPAIRABLE(2):\n"));
        RoomStatus roomStatus = getRoomStatusByNumber(choseRoomStatus);
        roomManager.getRooms().get(roomNumber).setRoomStatus(roomStatus);
        consoleView.SuccessfulNotification();
    }
    public void chekIn() throws ParseException {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to check in guest:\n"));
        Room room = roomManager.getRooms().get(roomNumber);
        String guestName = consoleView.getInput("Enter guest name:\n");
        String passportNumber = consoleView.getInput("Enter passport number:\n");
        String dateInput = consoleView.getInput("Enter check-in date in the format dd.MM.yyyy:\n");
        Date dateOfOccupation = dateFormat.parse(dateInput);
        String date = consoleView.getInput("Enter check-out date in the format dd.MM.yyyy:\n");
        Date dateOfEviction = dateFormat.parse(date);
        room.setOccupied(true);
        room.setGuestName(guestName);
        room.setPassportNumber(passportNumber);
        room.setDateOfOccupation(dateOfOccupation);
        room.setDateOfEviction(dateOfEviction);
        currentGuests.add(room);
        consoleView.SuccessfulNotification();
    }
    public void chekOut() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to check out guest:\n"));
        Room room = roomManager.getRooms().get(roomNumber);
        if (room.getGuestName() != null){
            room.setOccupied(false);
            room.setGuestName(null);
            room.setPassportNumber(null);
            room.setDateOfOccupation(null);
            room.setDateOfEviction(null);
            currentGuests.remove(room);
            consoleView.SuccessfulNotification();
        }
    }

    public void displeyListOfAvailableRooms(){
        int number =  Integer.parseInt(consoleView.getInput("Select the option to display, available numbers (1), " +
                "display available numbers - sorted by price (2), sorted by capacity(3), sorted by number of stars(4)\n"));
        List<Room> rooms = roomManager.getRooms().values().stream()
                .filter(room -> !room.isOccupied())
                .collect(Collectors.toList());
        List<Room> sortedRooms = new ArrayList<>(rooms);
        if (number == 1){consoleView.printList(rooms);}
        else if (number == 2){
            sortedRooms.sort(Comparator.comparingDouble(Room::getPrise));
            consoleView.printList(sortedRooms);
        }
        else if (number == 3){
            sortedRooms.sort(Comparator.comparingInt(Room::getCapasity));
            consoleView.printList(sortedRooms);
        }
        else if (number == 4){
            sortedRooms.sort(Comparator.comparingInt(Room::getNumberOfStars));
            consoleView.printList(sortedRooms);
        }
        consoleView.SuccessfulNotification();
    }
    public void dispeyListofRooms(){
        int number =  Integer.parseInt(consoleView.getInput("Select the option to display  rooms (1), " +
                "display  rooms - sorted by price (2), sorted by capacity(3), sorted by number of stars(4)\n"));

        List<Room>rooms = roomManager.getAllRooms();
        List<Room> sortedRooms = new ArrayList<>(rooms);
        if (number == 1){consoleView.printList(rooms);}
        else if (number == 2){
            sortedRooms.sort(Comparator.comparingDouble(Room::getPrise));
            consoleView.printList(sortedRooms);
        }
        else if (number == 3){
            sortedRooms.sort(Comparator.comparingInt(Room::getCapasity));
            consoleView.printList(sortedRooms);
        }
        else if (number == 4){
            sortedRooms.sort(Comparator.comparingInt(Room::getNumberOfStars));
            consoleView.printList(sortedRooms);
        }
        consoleView.SuccessfulNotification();
    }
    public void dispeyListofGuests(){
        int number =  Integer.parseInt(consoleView.getInput("Select the option to display list of guests (1), " +
                "display guests  - sorted by Name(2), sorted by Date Of Eviction(3)\n"));
        List<Room>rooms = roomManager.getAllRooms();
        List<Room>tmp = rooms.stream()
                .filter(room -> room.getGuestName()!=null)
                .collect(Collectors.toList());

        List<Room>sortedRooms = new ArrayList<>(rooms);
        if (number == 1){consoleView.printList(tmp);}
        else if (number == 2){ sortedRooms.sort(Comparator.comparing(Room::getGuestName));  consoleView.printList(sortedRooms);}
        else if (number == 3) { sortedRooms.sort(Comparator.comparing(Room::getDateOfEviction));  consoleView.printList(sortedRooms);}
        consoleView.SuccessfulNotification();
    }
    public void numberOfFreeRooms(){
        List<Room> rooms = roomManager.getAllRooms();
        long count = rooms.stream()
                .filter(room -> !room.isOccupied())
                .count();
        consoleView.display(count);
    }
    public void numberOfGests(){
        List<Room>rooms = roomManager.getAllRooms();
        long col = rooms.stream()
                .filter(x -> x.getGuestName() != null)
                .count();

        consoleView.display(col);
    }
    public void getListOfVailableRoomsInDate() throws ParseException {
        String input = consoleView.getInput("Enter  Date in the format dd.MM.yyyy:\n");
        Date date = dateFormat.parse(input);
        List<Room> ans = new ArrayList<>();
        List<Room> rooms = roomManager.getAllRooms();
        for (Room room : rooms){
            if (!room.isOccupied()){
                if (date.before(room.getDateOfOccupation())) ans.add(room);
                else {
                    if (date.after(room.getDateOfEviction()) || date.compareTo(room.getDateOfEviction()) == 0) ans.add(room);
                }
            }
            if (room.isOccupied()) ans.add(room);
        }
        consoleView.printList(ans);
    }
    public void priseCalculation(){
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to calculate prise:\n"));
        double ans = 0;
        Room room = roomManager.getRooms().get(roomNumber);
        LocalDate dateOfOccupation = room.getDateOfOccupation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateOfEviction = room.getDateOfEviction().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<ServiceType> serviceTypes = room.getServiceTypes();
        double sum = serviceTypes.stream()
                .mapToDouble(ServiceType::getPrise)
                .sum();
        int colDays = (int) ChronoUnit.DAYS.between(dateOfOccupation, dateOfEviction);
        ans = (room.getPrise() + sum)* colDays;
        consoleView.display(ans);
    }
    public void viewTheLastThreeGuests(){
        List<Room> lastThreeGuests = new ArrayList<>();
        for (int i = currentGuests.size() - 1; i >= 0 && lastThreeGuests.size() < 3; i--) {
            lastThreeGuests.add(currentGuests.get(i));
        }
        consoleView.printList(lastThreeGuests);
    }
    public RoomType getRoomTypeByNumber(int n ){
        if (n == 1){ return RoomType.STANDARD;}
        else if (n == 2){ return RoomType.DELUXE;}
        else if (n == 3){ return RoomType.VIP;}
        return null;
    }
    public RoomStatus getRoomStatusByNumber(int n ){
        if (n == 1){ return RoomStatus.SERVICED;}
        else if (n == 2) return RoomStatus.REPAIRABLE;
        return null;
    }
    public void addingAdditionalServices(){
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to add Services:\n"));
        Room room = roomManager.getRooms().get(roomNumber);
        consoleView.display("Please enter the number of services you want to add (Whith probe ): BREAKFAST(1), LYNCH(2), DINNER(3), CLEANING(4), SPA(5)\n");
        String input = consoleView.getInput("Enter service numbers:\n");
        String[] inputNumbers = input.split("\\s+");
        List<Integer> serviceNumbers = new ArrayList<>();
        List<ServiceType> serviceTypes = new ArrayList<>();
        for (String number : inputNumbers){
            serviceNumbers.add(Integer.parseInt(number));
        }
        if (!serviceNumbers.isEmpty()){
            for (int i = 0; i < serviceNumbers.size(); i++){
                int serviceNumber = serviceNumbers.get(i);
                if (serviceNumber == 1){serviceTypes.add(ServiceType.BREAKFAST);}
                else if (serviceNumber == 2){serviceTypes.add(ServiceType.LYNCH);}
                else if (serviceNumber == 3){serviceTypes.add(ServiceType.CLEANING);}
                else if (serviceNumber == 4){serviceTypes.add(ServiceType.SPA);}
            }
        }
        room.addServiceType(serviceTypes);
        consoleView.SuccessfulNotification();
    }
    public void sortedServiceTypesByPrise() {
        List<ServiceType> serviceTypes = ServiceType.getServiceTypeList();
        serviceTypes.sort(Comparator.comparingDouble(ServiceType::getPrise));
        consoleView.display(serviceTypes);
    }

    public void displeyServiceTypes(){
        List<ServiceType> serviceTypes = ServiceType.getServiceTypeList();
        consoleView.display(serviceTypes);
    }

}
