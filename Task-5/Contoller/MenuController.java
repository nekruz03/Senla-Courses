package Contoller;

import model.Room;
import model.RoomManager;
import util.Builder;
import util.Menu;
import util.MenuItem;
import util.Navigator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuController {
    private Builder builder;
    private Navigator navigator;
    private RoomManager roomManager;
    List<Room> currentGuests  = new ArrayList<>();
    public MenuController(RoomManager roomManager, Navigator navigator, Builder builder) {
        this.builder = new Builder();
        this.navigator = new Navigator();
        this.roomManager = roomManager;
    }
    public void run() throws ParseException {
        builder.buildMenu(roomManager);
        navigator.setCurrentMenu(builder.getRootMenu());
        while (true) {
            Menu currentMenu = navigator.getCurrentMenu();
            if (currentMenu == null) {
                System.out.println("No menu available. Exiting...");
                break;
            }
            displayMenu(currentMenu);
            int choice = getUserChoice();
            if (choice == 0) {
                System.out.println("Exiting...");
                break;
            } else if (choice > 0 && choice <= currentMenu.getItems().size()) {
                MenuItem selectedItem = currentMenu.getItems().get(choice - 1);
                if (selectedItem.getAction() != null) {
                    selectedItem.getAction().execute();
                }
                if (selectedItem.getNextMenu() != null) {
                    navigator.navigateTo(selectedItem.getNextMenu());
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMenu(Menu menu){
        System.out.println("\n" + menu.getTitle());
        for (int i = 0; i < menu.getItems().size(); i++) {
            System.out.println((i + 1) + ". " + menu.getItems().get(i).getTitle());
        }
        System.out.println("0. Exit");
    }
    private int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }
}
