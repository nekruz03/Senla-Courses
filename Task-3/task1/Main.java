package task1;

import java.util.Random;
public class Main {
    public static void main(String[] args) {
        int minValue = 100, maxValue = 999, cur = 0, sum = 0;
        int random = minValue + (int) (Math.random() *(maxValue-minValue+1));
        int number = random;
        while (number > 0){
            cur = number % 10;
            sum = sum + cur;
            number = number / 10;
        }
        System.out.println("Random number = " + random +"\n" + "Sum nubers = " + sum);
    }
}
