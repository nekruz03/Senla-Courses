package org.example.Contoller;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;
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
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    public void run() {
        logger.info("MenuController is starting...");
        try {
            builder.buildMenu();
            navigator.setCurrentMenu(builder.getRootMenu());
            while (true) {
                Menu currentMenu = navigator.getCurrentMenu();
                if (currentMenu == null) {
                    logger.warn("No menu available. Exiting...");
                    System.out.println("No menu available. Exiting...");
                    break;
                }
                displayMenu(currentMenu);
                int choice = getUserChoice();
                if (choice == 0) {
                    logger.info("Exiting...");
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
                    logger.warn("Invalid choice: {}. Please try again.", choice);
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (ParseException e) {
            logger.error("Date format error: check that you entered the correct date", e);
            System.out.println("Date format error: check that you entered the correct date");
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage(), e);
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayMenu(Menu menu) {
        logger.info("Displaying menu: {}", menu.getTitle());
        System.out.println("\n" + menu.getTitle());
        for (int i = 0; i < menu.getItems().size(); i++) {
            System.out.println((i + 1) + ". " + menu.getItems().get(i).getTitle());
        }
        System.out.println("0. Exit");
        logger.info("Exited Displaying menu");
    }

    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }



}
