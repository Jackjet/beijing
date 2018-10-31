package com.ysbd.beijing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.ysbd.beijing.ui.activity.LoginActivity;
import com.ysbd.beijing.utils.ActivityManager;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.SpUtils;
import com.ysbd.beijing.utils.WebServiceUtils;

/**
 * Created by lcjing on 2018/8/13.
 */

public class BaseActivity extends AppCompatActivity {
    public SharedPreferences sp;
    private Handler mHandler_ = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(Constants.SP, MODE_PRIVATE);
        WebServiceUtils.getInstance().initId(this);
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下
                checkLogin();
//                mHandler_.removeCallbacks(runnable);
                break;
//            case MotionEvent.ACTION_UP:
//                startAD();
//                Log.e("=======", "ACTION_UP");
//                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
            builder.setTitle("温馨提示")
                    .setCancelable(false)
                    .setMessage("当前登录已失效，请重新登录")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setClass(BaseActivity.this, LoginActivity.class);
                            startActivity(intent);
                            ActivityManager.getInstance().finishAllActivity();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    };

    private long maxTime = 1000L * 60 * 10;//10分钟

    public void checkLogin() {
        long time = SpUtils.getInstance().getTime();
        long l = System.currentTimeMillis();
        if (l - time >= maxTime) {
            //超过规定时长
            sp.edit().putBoolean(Constants.IS_LOGIN, false).apply();
            mHandler_.post(runnable);
        } else {
            SpUtils.getInstance().setTime(System.currentTimeMillis());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }
}
