package com.ysbd.beijing;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.pgyersdk.crash.PgyCrashManager;
import com.tencent.smtt.sdk.QbSdk;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lcjing on 2018/8/27.
 */

public class App extends LitePalApplication {

    private static Context context;
    public static int tabCode = -1;
    public static String userId;



    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //腾讯浏览服务
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.e("腾讯浏览服务插件", "" + b);
            }
        };
        QbSdk.initX5Environment(getApplicationContext(), cb);
        QbSdk.setDownloadWithoutWifi(true);
        PgyCrashManager.register(this);
    }


    public static void catchE(Exception e) {
        PgyCrashManager.reportCaughtException(context, e);
    }

    public static Context getContext() {
        return context;
    }


    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }


    private String getAppName(int pID) {
        String processName = null;
        android.app.ActivityManager am = (android.app.ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            android.app.ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
