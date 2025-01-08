package org.example.Contoller;

import annotation.OwnInject;
import model.Room;
import model.RoomManager;
import org.example.Action.ExportRoomsAction;
import util.*;
import util.DI.DI;
import util.serialization.RoomManagerSerialization;
import viev.ConsoleView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class MenuController {
    @OwnInject
    private Builder builder;
    @OwnInject
    private Navigator navigator;
    @OwnInject
    private RoomManager roomManager;
    @OwnInject
    private ExportRoomsAction exportRoomsAction;
    List<Room> currentGuests = new ArrayList<>();
    @OwnInject
    ConsoleView consoleView;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public MenuController() throws IllegalAccessException {
        DI.infectDependencies(this);
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
                    exportRoomsAction.execute();
                }
                if (choice == 0) {
                    System.out.println("Exiting...");
                    RoomManagerSerialization.saveState(roomManager);
                    break;
                } else if (choice > 0 && choice <= currentMenu.getItems().size()) {
                    MenuItem selectedItem = currentMenu.getItems().get(choice - 1);
                    if (selectedItem.getAction() != null) {
                        if (choice == 4 ){
                            if (roomManager.getRoomStatusChangeEnabled())  selectedItem.getAction().execute();
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
