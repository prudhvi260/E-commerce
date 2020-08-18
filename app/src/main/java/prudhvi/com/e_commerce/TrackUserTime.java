package prudhvi.com.e_commerce;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import prudhvi.com.e_commerce.Model.GroceryItem;

public class TrackUserTime extends Service {
    private IBinder binder=new LocalBinder();
    private int seconds=0;
    private GroceryItem item;
    private boolean shouldFinish=false;
    @Override
    public IBinder onBind(Intent intent) {
        trackTime();
        return binder;
    }
    public class LocalBinder extends Binder{
        TrackUserTime getServices(){
            return TrackUserTime.this;
        }
    }
    private void trackTime()
    {
         final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (!shouldFinish){
                    try {
                        Thread.sleep(1000);
                        seconds++;

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
         thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shouldFinish=true;
        Utils utils=new Utils(this);
        int minutes=Integer.valueOf(seconds/60);
        utils.increaseUserPoint(item,minutes*2);
    }

    public void setItem(GroceryItem item) {
        this.item = item;
    }
}
