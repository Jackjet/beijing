package com.ysbd.beijing;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.ntko.app.RHMOApplication;
import com.ntko.app.crash.FireCrasher;
import com.ntko.app.support.internal.license.LicenseConfiguration;
import com.pgyersdk.crash.PgyCrashManager;
import com.tencent.smtt.sdk.QbSdk;

import org.litepal.LitePal;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lcjing on 2018/8/27.
 */

public class App extends RHMOApplication {

    private static Context context;
    public static int tabCode = -1;
    public static String userId;

    String OFFLINE_SERVER_CERT = "-----BEGIN PUBLIC KEY-----\n"
            + "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZsfNekbXiCCcoZSnc5KOCCvUG\n"
            + "dTaG10HGR58+0d48IJmdEagrl15TCdtS/lSXfIdcGfOyRFzyDtXH//umetEG9PS1\n"
            + "ck1p31QEDF/QU9KHddk0j1rq/idTT7/0bZL4BOJg5pAHK+ZOxpp8Fh/+3o/AFKEl\n"
            + "Gl75d1qlXYcJzN02oQIDAQAB\n"
            + "-----END PUBLIC KEY-----\n";


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
        LitePal.initialize(this);
        enableOfflineActivation();

    }

    @Override
    protected FireCrasher.AfterAppCrashed onAppCrashed(Throwable throwable) {
        Log.e("软航移动(AppCrashed)", "未知原因导致应用崩溃", throwable);
        try {

            File sdRoot = Environment.getExternalStorageDirectory();
            File log = new File(sdRoot, "我的App异常日志.log");
            if (log.exists()) {
                if (!log.delete()) {
                    log = new File(sdRoot, "我的App异常日志" + System.nanoTime() + ".log");
                }
            }

            PrintWriter writer = new PrintWriter(log);
            throwable.printStackTrace(writer);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return FireCrasher.AfterAppCrashed.IGNORE;
    }

    private void enableOfflineActivation() {
        LicenseConfiguration.enableOffline("http://10.123.27.195:8080", OFFLINE_SERVER_CERT, true);
//        LicenseConfiguration.enableOffline("http://172.10.48.92:8080", OFFLINE_SERVER_CERT, true);
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
