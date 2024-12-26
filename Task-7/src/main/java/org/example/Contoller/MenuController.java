package org.example.Contoller;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import model.Room;
import model.RoomManager;
import org.example.Action.ExportRoomsAction;
import org.example.Enum.RoomStatus;
import org.example.Enum.RoomType;
import util.*;
import util.serialization.RoomManagerSerialization;
import viev.ConsoleView;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class MenuController {
    private Builder builder;
    private Navigator navigator;
    private RoomManager roomManager;
    List<Room> currentGuests = new ArrayList<>();
    ConsoleView consoleView = new ConsoleView();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public MenuController(RoomManager roomManager, Navigator navigator, Builder builder) {
        this.builder = new Builder();
        this.navigator = new Navigator();
        this.roomManager = roomManager;
    }

    public void run() {
        try {
            RoomManagerSerialization.loadState(roomManager);
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
                if (choice == 6) {
                    ExportRoomsAction exportOfAvailableRoomsAction = new ExportRoomsAction(roomManager);
                    exportOfAvailableRoomsAction.execute();
                }
                if (choice == 0) {
                    System.out.println("Exiting...");
                    RoomManagerSerialization.saveState(roomManager);
                    break;
                } else if (choice > 0 && choice <= currentMenu.getItems().size()) {
                    MenuItem selectedItem = currentMenu.getItems().get(choice - 1);
                    if (selectedItem.getAction() != null) {
                        if (choice == 4 ){
                            if (ConfigLoader.isStatusEnabled())  selectedItem.getAction().execute();
                            consoleView.print("The ability to change status for rooms has been disabled.");
                        }
                        else {
                            selectedItem.getAction().execute();
                        }
                    }
                    if (selectedItem.getNextMenu() != null) {
                        navigator.navigateTo(selectedItem.getNextMenu());
                    }
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (ParseException e) {
            System.out.println("Ошибка формата даты: проверьте правильность ввода даты");
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayMenu(Menu menu) {
        System.out.println("\n" + menu.getTitle());
        for (int i = 0; i < menu.getItems().size(); i++) {
            System.out.println((i + 1) + ". " + menu.getItems().get(i).getTitle());
        }
        System.out.println("0. Exit");
    }

    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }



}
