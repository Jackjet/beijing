package com.ysbd.beijing.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ysbd.beijing.App;

/**
 * Created by lcjing on 2018/9/3.
 */

public class SpUtils {
    private SharedPreferences sp;
    private static SpUtils spUtils;

    private SpUtils() {
        sp = App.getContext().getSharedPreferences(Constants.SP, Context.MODE_PRIVATE);
    }

    public static SpUtils getInstance() {
        if (spUtils == null) {
            spUtils = new SpUtils();
        }
        return spUtils;
    }

    public boolean getCommentEditable(String commentId) {
        return sp.getBoolean(commentId, true);
    }

    public void setCommentEditable(String commentId, boolean editable) {
        sp.edit().putBoolean(commentId, editable).apply();
    }

    public boolean getAddressVisiable() {
        return sp.getBoolean("addressVisiable", false);
    }


    public void setAddressVisiable(boolean editable) {
        sp.edit().putBoolean("addressVisiable", editable).apply();
    }

    public void setScreenWidth(int screenWidth) {
        sp.edit().putInt("screenWidth", screenWidth).apply();
    }

    public int getScreenWidth() {
        return sp.getInt("screenWidth", 360);
    }


    public String getIP() {
//        return "http://172.10.48.92:9998/risenetoabjcz";
        return sp.getString("ip", "http://10.123.27.194:9910");
    }//http://172.28.68.48:9910

    public void setIp(String ip) {
        sp.edit().putString("ip", ip).apply();
//        System.exit(0);
    }


    public String getUserName() {
        return sp.getString(Constants.USER_NAME, "");
    }

    public String getUserId() {
        return sp.getString(Constants.USER_ID, "");
    }

    public void setTime(long time) {
        sp.edit().putLong("caozuoTime", time).apply();
    }

    public long getTime() {
        return sp.getLong("caozuoTime", System.currentTimeMillis());
    }
}
