package Task_3_4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Buffer {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;
    public Buffer(int capacity) {
        this.capacity = capacity;
    }
    public  synchronized void produce() throws InterruptedException {
        while (queue.size() == capacity){
            wait();
        }
        int value = new Random().nextInt(20);
        queue.add(value);
        System.out.println("Produced: " + value);
        notify();
    }
    public synchronized void consume() throws InterruptedException {
        while (queue.isEmpty()){
            wait();
        }
        int value = queue.poll();
        System.out.println("Consumed: " + value);
        notify();
    }
}
