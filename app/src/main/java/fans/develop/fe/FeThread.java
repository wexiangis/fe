package fans.develop.fe;

import android.os.Handler;

/*
    批量延迟UI操作
 */
public class FeThread extends Thread{

    private Runnable[] runnables;
    private Handler handler;
    private int[] delay;

    public FeThread(int[] delay, Runnable ... runnables){
        handler = new Handler();
        this.delay = delay;
        this.runnables = new Runnable[runnables.length];
        for(int i = 0; i < runnables.length && i < delay.length; i++)
            this.runnables[i] = runnables[i];
    }

    @Override
    public void run(){
        for(int i = 0; i < runnables.length; i++)
            handler.postDelayed(this.runnables[i], delay[i]);
    }
}
