package Scanners;

import java.util.Scanner;

public class Scanners {
    Scanner scan = new Scanner(System.in);
    private int number = 0;
    public void scanNumber() {
        number = scan.nextInt();
    }
    public int getNumber() {
        return number;
    }

}
