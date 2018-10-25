package com.ysbd.beijing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.selectPhoto.ImageShowActivity;

/**
 * Created by lcjing on 2018/8/13.
 */

public class BaseFragment extends Fragment{
    SharedPreferences sp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp=getContext().getSharedPreferences(Constants.SP, Context.MODE_PRIVATE);
    }



    /**
     * 打开图库
     */
    public void openAlbum() {
        Intent intent = new Intent(getActivity(), ImageShowActivity.class);
        intent.putExtra("isSingle", true);
        startActivityForResult(intent, 10101);
    }

    public boolean checkAlive(){
        if (System.currentTimeMillis()-sp.getLong(Constants.LAST_LIVE_TIME,0)>Constants.LEAVE_TIME) {
            //退出所有界面  并回到登录界面
//            CHECK_ALIVE
                    getContext().sendBroadcast(new Intent(Constants.CHECK_ALIVE));
            return false;
        }
        return true;
    }

    public void setLiveTime(){
        sp.edit().putLong(Constants.LAST_LIVE_TIME,System.currentTimeMillis()).apply();
    }
}
