package org.example.Contoller;

import annotation.Configurator;
import annotation.OwnInject;
//import dao.model.RoomManager;
//import org.example.Action..ExportRoomsAction;
import dao.model.connection.ConnectionManager;
import util.*;
//import util.serialization.RoomManagerSerialization;
import viev.ConsoleView;
import java.text.ParseException;
import java.util.*;
public class MenuController {
    @OwnInject
    private Builder builder;
    @OwnInject
    private Navigator navigator;
    @OwnInject
    ConsoleView consoleView;
    @OwnInject
    ConnectionManager connectionManager;
    public MenuController() throws IllegalAccessException {}
    public void run() {
        try {
            builder.buildMenu();
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
                    if (choice ==18) ;
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

        } catch (ParseException e) {
            System.out.println("Date format error: check that you entered the correct date");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
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
