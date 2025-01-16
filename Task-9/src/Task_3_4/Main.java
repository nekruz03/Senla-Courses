package Task_3_4;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        TimePrint timePrint  = new TimePrint(15);
        producer.start();
        consumer.start();
        timePrint.setDaemon(true);
        timePrint.start();
    }
}
