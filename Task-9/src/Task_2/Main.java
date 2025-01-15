package Task_2;

public class Main {
    public static void main(String[] args) {
        Object monitor = new Object();
        MyThread thread1 = new MyThread("First Thread", monitor);
        MyThread thread2 = new MyThread("Second Thread", monitor);
        thread1.start();
        thread2.start();

    }
}
