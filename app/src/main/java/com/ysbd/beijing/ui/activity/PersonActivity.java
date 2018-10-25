package com.ysbd.beijing.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysbd.beijing.App;
import com.ysbd.beijing.R;
import com.ysbd.beijing.bean.PersonBean;
import com.ysbd.beijing.utils.WebServiceUtils;

import java.util.List;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener{

    private String userId,name,job,depart;
    private TextView tvName,tvDepart,tvPhone,tvMob,tvChuanzhen,fangjian,zubie,gangwei;
    private LinearLayout llPhone,llMob,llChuanzhen;
    private FrameLayout flBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        flBack=findViewById(R.id.fl_back);
        tvName=findViewById(R.id.tv_name);
        tvDepart=findViewById(R.id.tv_depart);
        tvPhone=findViewById(R.id.tv_phone);/////
        tvMob=findViewById(R.id.tv_mob_phone);/////
        tvChuanzhen=findViewById(R.id.tv_chuanzhen);////
        llPhone=findViewById(R.id.ll_phone);
        llMob=findViewById(R.id.ll_mob_phone);
        llChuanzhen = findViewById(R.id.ll_chuanzhen);
        fangjian = findViewById(R.id.tv_fangjian);
        zubie= findViewById(R.id.tv_zubie);
        gangwei= findViewById(R.id.tv_gangwei);

        userId=getIntent().getStringExtra("userId");
        name=getIntent().getStringExtra("name");
        job=getIntent().getStringExtra("job");
        depart=getIntent().getStringExtra("depart");
        if (job!=null&&job.length()>0) {
            name=name+"（"+job+"）";
        }
        tvName.setText(name);
        tvDepart.setText("部门："+depart);

        flBack.setOnClickListener(this);
        llPhone.setOnClickListener(this);
        llMob.setOnClickListener(this);

        getUserId();

    }

    private void getUserId(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String s=WebServiceUtils.getInstance().getUserInfo(userId);
                handler.obtainMessage(0,s).sendToTarget();
            }
        }.start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    try {
                        List<PersonBean> personBean=new Gson().fromJson(msg.obj.toString(),new TypeToken<List<PersonBean>>(){}.getType());
                        tvPhone.setText(personBean.get(0).getEMPLOYEE_PHONE());
                        tvMob.setText(personBean.get(0).getEMPLOYEE_MOBLIE());
                        tvChuanzhen.setText(personBean.get(0).getEMPLOYEE_FAX());
                        fangjian.setText(personBean.get(0).getEMPLOYEE_FJ());
                        zubie.setText(personBean.get(0).getEMPLOYEE_ZB());
                        gangwei.setText(personBean.get(0).getEMPLOYEE_GWZZ());
                    }catch (Exception e){
                        App.catchE(e);
                    }


                    break;
                case 1:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_phone:
                if (tvPhone.getText().toString().length()>2) {
                    Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvPhone.getText().toString()));
                    startActivity(dialIntent);
                }

                break;
            case R.id.ll_mob_phone:
                if (tvPhone.getText().toString().length()>2) {
                    Intent dialIntent1 =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + tvMob.getText().toString()));
                    startActivity(dialIntent1);
                }

                break;
                case R.id.fl_back:
                    finish();
                    break;
        }

    }
}
