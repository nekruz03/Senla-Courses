package Task_2;

public class MyThread extends Thread {
    private  Object monitor;
    String name;
        public MyThread(String name, Object monitor) {
            this.name = name;
            this.monitor = monitor;
        }
        public  void run() {
            synchronized (monitor) {
                for (int i = 0; i < 10; i++) {
                    System.out.println(name + "  " + i );
                    monitor.notify();
                    try {
                        if (i < 9) monitor.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
}
