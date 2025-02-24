package viev;


import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    Scanner scanner = new Scanner(System.in);
    public void print(String message) {
        System.out.println(message);
    }
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    public void SuccessfulNotification(){
        System.out.println("Successfully completed!-----------------------------");
    }
    public <T> void printList(List<T> list) {
        for (T item : list) {
            System.out.println(item);
        }
    }
    public <T> void display(T t){
        System.out.println(t);
    }
}

