package com.ysbd.beijing;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ysbd.beijing.autoExist.ScreenObserver;
import com.ysbd.beijing.autoExist.TimeoutService;
import com.ysbd.beijing.ui.activity.LoginActivity;
import com.ysbd.beijing.ui.activity.MainActivity;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.WebServiceUtils;

import cn.lsmya.apkupdate.VersionBeen;
import cn.lsmya.apkupdate.VersionListener;
import cn.lsmya.apkupdate.VersionUtils;

/**
 * Created by lcjing on 2018/8/13.
 */

public class BaseActivity extends AppCompatActivity{
    public SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp=getSharedPreferences(Constants.SP,MODE_PRIVATE);
        WebServiceUtils.getInstance().initId(this);
        registerReceiver(receiver,new IntentFilter(Constants.FINISH));
//        mScreenObserver = new ScreenObserver(this);
//        mScreenObserver.requestScreenStateUpdate(new ScreenObserver.ScreenStateListener() {
//            @Override
//            public void onScreenOn() {
//                if(!ScreenObserver.isApplicationBroughtToBackground(BaseActivity.this))
//                {
//                    cancelAlarmManager();
//                }
//            }
//
//            @Override
//            public void onScreenOff() {
//                if(!ScreenObserver.isApplicationBroughtToBackground(BaseActivity.this))
//                {
//                    cancelAlarmManager();
//                    setAlarmManager();
//                }
//            }
//        });
        if (checkAlive()) {

        }

    }


    public void checkVersion(){
        VersionUtils.with(this).checkVersion(new VersionListener() {
            @Override
            public void onUpdate(VersionBeen been, String response) {
                VersionUtils.with(BaseActivity.this).downApk(been.getApkUrl(),"北京财政局v"+App.getAppVersionName(BaseActivity.this)+"apk.apk");
            }

            @Override
            public void onNotUpdate() {

            }
        });
    }

    /**
     * 设置定时器管理器
     */
    private void setAlarmManager()
    {

        long numTimeout = Constants.LEAVE_TIME/2;
//        LogUtils.d("isTimeOutMode=yes,timeout="+numTimeout);
        Intent alarmIntent = new Intent(BaseActivity.this, TimeoutService.class);
        alarmIntent.putExtra("action", "timeout"); //自定义参数
        PendingIntent pi = PendingIntent.getService(BaseActivity.this, 1024, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        long triggerAtTime =  (System.currentTimeMillis()+numTimeout);
        am.set(AlarmManager.RTC_WAKEUP, triggerAtTime, pi); //设定的一次性闹钟，这里决定是否使用绝对时间
//        LogUtils.d("----->设置定时器");
    }
    /**
     * 取消定时管理器
     */
    private void cancelAlarmManager()
    {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(BaseActivity.this, TimeoutService.class);
        PendingIntent pi = PendingIntent.getService(BaseActivity.this, 1024, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        // 与上面的intent匹配（filterEquals(intent)）的闹钟会被取消
        alarmMgr.cancel(pi);
//        LogUtils.d("----->取消定时器");
    }





    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (action==null) {
                return;
            }
            switch (action){
                case Constants.FINISH:
                    finish();
                    break;
            }
        }
    };
    private  boolean activityIsActive;
    @Override
    protected void onResume() {
        super.onResume();
        checkAlive();
//        LogUtils.e("MainActivity-onResume");
        cancelAlarmManager();
        activityIsActive = true;
//        LogUtils.d("activityIsActive="+activityIsActive);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkAlive();
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkAlive();
    }

    @Override
    protected void onStop() {
        super.onStop();
        checkAlive();
        if (ScreenObserver.isApplicationBroughtToBackground(this))
        {
            cancelAlarmManager();
            setAlarmManager();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkAlive();
        unregisterReceiver(receiver);
    }

//    private ScreenObserver mScreenObserver;


    public boolean checkAlive(){
        if (System.currentTimeMillis()-sp.getLong(Constants.LAST_LIVE_TIME,0)>Constants.LEAVE_TIME) {
            //退出所有界面  并回到登录界面
//            sp.edit().putLong(Constants.LAST_LIVE_TIME,System.currentTimeMillis()).apply();
//            sendBroadcast(new Intent(Constants.FINISH));
//            sp.edit().putBoolean(Constants.IS_LOGIN,false).apply();
//            startActivity(new Intent(this, LoginActivity.class));
            return false;
        }else {
            sp.edit().putLong(Constants.LAST_LIVE_TIME,System.currentTimeMillis()).apply();

        }
        return true;
    }

    public void setLiveTime(){
        checkAlive();
        sp.edit().putLong(Constants.LAST_LIVE_TIME,System.currentTimeMillis()).apply();
    }

}
