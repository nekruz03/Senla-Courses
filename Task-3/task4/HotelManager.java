import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotelManager {
    public static void main(String[] args) {
        System.out.println("Hello, welcome!");
        RoomManager roomManager = new RoomManager();
        Command command = new Command(roomManager);
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Please select action:");
            System.out.println("1. Add Room");
            System.out.println("2. Change Room Price");
            System.out.println("3. Change Room Type");
            System.out.println("4. Delete Room");
            System.out.println("5. Change Room Status");
            System.out.println("6. Display Rooms");
            System.out.println("7. Check-in Guest");
            System.out.println("8. Check-out Guest");
            System.out.println("9. Exit");
            int choice = input.nextInt();

            switch (choice) {
                case 1 -> command.addRoom();
                case 2 -> command.changePrice();
                case 3 -> command.changeType();
                case 4 -> command.deleteRoom();
                case 5 -> command.changeRoomStatus();
                case 6 -> command.display();
                case 7 -> command.chekIn();
                case 8 -> command.checkOut();
                case 9 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}