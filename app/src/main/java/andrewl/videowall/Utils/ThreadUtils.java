package andrewl.videowall.Utils;

import org.greenrobot.eventbus.EventBus;

import andrewl.videowall.DataBase.greendao.EventBusMessage;

/**
 * Created by liut1 on 8/18/16.
 */
public class ThreadUtils extends Thread{
    private volatile Thread blinker;
//    private int interval;
    public void stopNow() {
        blinker = null;
    }
    public void start(){
        blinker.start();
    }
    public ThreadUtils(final int interval){
//        this.interval = interval;
        blinker = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread thisThread = Thread.currentThread();
                int i = 0;
                while (blinker == thisThread) {
                    try {
                        if(i > 90){
                            i = 90;
                        }
                        EventBus.getDefault().post(new EventBusMessage(17,i+"%"));
                        i++;
                        thisThread.sleep(interval);
                    } catch (InterruptedException e){
                    }
                }
            }
        });
    }

}
