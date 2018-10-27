package com.ysbd.beijing.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class CheckPermissionsUtil {
    public static final String TAG = "CheckPermissionsUtil";

    Context mContext;

    public CheckPermissionsUtil(Context mContext) {
        this.mContext = mContext;
    }

    private String[] needPermissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_SETTINGS
    };

    private boolean checkPermission(String... needPermissions) {
        for (String permission : needPermissions) {
            if (ActivityCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPermissions(Context context, String... needPermissions) {
        for (String permission : needPermissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void requestPermission(Activity activity, int code, String... needPermissions) {
        ActivityCompat.requestPermissions(activity, needPermissions, code);
        Log.i(TAG, "request Permission...");
    }

    //判断是否获取所有的权限
    public static boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        int i = 0;
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                i++;
            }
        }
        if (i > 0) {
            return false;
        } else
            return true;
    }


}
