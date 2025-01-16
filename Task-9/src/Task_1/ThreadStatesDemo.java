package Task_1;

public class ThreadStatesDemo {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread thread = new Thread(() -> {
            try {
                synchronized (lock) {
                    lock.wait(); // Переход в состояние WAITING
                }

                Thread.sleep(1000); // Переход в состояние TIMED_WAITING
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        // NEW
        System.out.println("State after creation (NEW): " + thread.getState());
        // Запускаем поток
        thread.start();
        Thread.sleep(100); // Даем потоку возможность перейти в RUNNABLE
        // RUNNABLE
        System.out.println("State after start (RUNNABLE): " + thread.getState());
        // Блокируем монитор, чтобы поток перешел в состояние BLOCKED
        Thread blockingThread = new Thread(() -> {
            synchronized (lock) {
                System.out.println("State when BLOCKED: " + thread.getState());
            }
        });
        synchronized (lock) {
            blockingThread.start();
            Thread.sleep(100); // Даем потоку время войти в состояние BLOCKED
            // Отправляем основной поток в WAITING
            lock.notify();
        }
        Thread.sleep(100); // Даем время для перехода в TIMED_WAITING
        // TIMED_WAITING
        System.out.println("State when TIMED_WAITING: " + thread.getState());
        // Ждем завершения потока
        thread.join();
        // TERMINATED
        System.out.println("Final state (TERMINATED): " + thread.getState());
    }
}
