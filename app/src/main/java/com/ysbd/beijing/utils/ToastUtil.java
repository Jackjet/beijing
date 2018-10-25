package com.ysbd.beijing.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lcjing on 2018/8/13.
 */

public class ToastUtil {
    static Toast toast;

    public static void show(String msg,Context context){
        if (toast==null) {
            toast=Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }

}
