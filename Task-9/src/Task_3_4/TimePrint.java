package Task_3_4;

import java.util.Date;

public class TimePrint extends Thread{
    public long interval;
    public TimePrint(double interval){
        this.interval = (long) interval;
    }
    public void run(){
        while(true){
            try {
               Thread.sleep(interval * 500L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Date now = new Date();
            System.out.println("Time: " + now) ;
        }
    }
}
