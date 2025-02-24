import Model.*;
import servise.Bouquet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bouquet bouquet = new Bouquet();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! Let's create a bouquet: \nIf you want to add a flower to the bouquet, write the quantity.\nIf you don't want to add, write '0' ");

        // Используем ArrayList вместо массива
        List<Flower> flowers = new ArrayList<>();
        flowers.add(new Rose());
        flowers.add(new Gvozdika());
        flowers.add(new Orchidea());
        flowers.add(new Siren());
        flowers.add(new Tulpan());

        // Устанавливаем доступное количество
        flowers.get(0).setAvailableQuantity(60);
        flowers.get(1).setAvailableQuantity(34);
        flowers.get(2).setAvailableQuantity(22);
        flowers.get(3).setAvailableQuantity(14);
        flowers.get(4).setAvailableQuantity(5);

        int count = 0;
        for (Flower flower : flowers) {
            System.out.println("Let's add " + flower + " in bouquet? Write the quantity: ");
            count = scanner.nextInt();
            while (count > flower.getAvailableQuantity()) {
                System.out.println("Available only - " + flower.getAvailableQuantity() + ". Please try again, re-enter: ");
                count = scanner.nextInt();
            }
            bouquet.addFlower(flower, count);
        }

        System.out.println(bouquet.getHistory());
        System.out.println("Price: " + bouquet.colculatePrise());
    }
}
