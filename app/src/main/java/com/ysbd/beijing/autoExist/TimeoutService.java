package com.ysbd.beijing.autoExist;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by lcjing on 2018/8/29.
 */

public class TimeoutService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    boolean isrun = true;

    @Override
    public void onCreate() {
//        LogUtils.e("BindService-->onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        LogUtils.e("BindService-->onStartCommand()");
        forceApplicationExit();
        return super.onStartCommand(intent, flags, startId);

    }

    private void forceApplicationExit()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityListUtil.getInstence().cleanActivityList();
                stopSelf();
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isrun = false;
    }

}