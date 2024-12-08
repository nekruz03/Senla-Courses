package App;

import controller.Controller;
import model.RoomManager;
import view.ConsoleView;

import java.text.ParseException;
import java.util.Scanner;

public class HotelManager {
    public static void main(String[] args) throws ParseException {
        ConsoleView consoleView = new ConsoleView();
        RoomManager roomManager = new RoomManager();
        Controller controller = new Controller(consoleView, roomManager);
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of available services:\n" +
                "1.  Add room\n" +
                "2.  Change prise\n"+
                "3.  Change status\n" +
                "4.  Change type\n" +
                "5.  Check in guest\n" +
                "6.  Check out guest\n" +
                "7.  Display  list of available additional services\n" +
                "8.  Add additional services to the room\n" +
                "9.  Display rooms, (sorted by price) , (sorted by capacity), (sorted by number of stars)\n" +
                "10. Display list of guests, (sorted by Name), (sorted by Date Of Eviction)\n" +
                "11. Display, available numbers, (sorted by price), (sorted by capacity), (sorted by number of stars)\n" +
                "12. Display, number of free roms\n" +
                "13. Display, number of guests\n" +
                "14. Display, List of rooms that will be free on a specific date in the future\n" +
                "15. Calculation of the amount that the guest must pay\n" +
                "16. Display, the last 3 guests of the room and their dates of stay\n" +
                "17.Display,additional services by prise\n" +
                "18. Delete room\n" +
                "19. Exit");
        while (true){
            System.out.println("Enter your command number - ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1: controller.addRoom(); break;
                case 2: controller.changePrice(); break;
                case 3: controller.changeStatus(); break;
                case 4: controller.changeType(); break;
                case 5: controller.chekIn();break;
                case 6: controller.chekOut(); break;
                case 7: controller.displeyServiceTypes(); break;
                case 8: controller.addingAdditionalServices(); break;
                case 9: controller.dispeyListofRooms(); break;
                case 10: controller.dispeyListofGuests(); break;
                case 11: controller.displeyListOfAvailableRooms();break;
                case 12: controller.numberOfFreeRooms();break;
                case 13: controller.numberOfGests();break;
                case 14: controller.getListOfVailableRoomsInDate();break;
                case 15: controller.priseCalculation();break;
                case 16: controller.viewTheLastThreeGuests();break;
                case 17: controller.sortedServiceTypesByPrise();break;
                case 18:controller.deleteRoom(); break;
                case 19: return;
            }
        }
    }
}
