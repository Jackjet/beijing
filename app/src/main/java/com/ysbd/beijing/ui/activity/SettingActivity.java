package com.ysbd.beijing.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ysbd.beijing.BaseActivity;
import com.ysbd.beijing.R;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.LoginUtils;
import com.ysbd.beijing.utils.MD5;
import com.ysbd.beijing.utils.ToastUtil;
import com.ysbd.beijing.utils.WebServiceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.fl_back)
    FrameLayout flBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_original)
    EditText etOriginal;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_reset)
    Button btReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        tvName.setText(sp.getString(Constants.USER_NAME,""));
    }

    @OnClick({R.id.fl_back, R.id.bt_login, R.id.bt_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_back:
                finish();
                break;
            case R.id.bt_login:
                check();
                break;
            case R.id.bt_reset:
                etCode.setText("");
                etOriginal.setText("");
                etPassword.setText("");
                break;
        }
    }
    String newPass;
    private void check(){
//        etOriginal.getText().toString().equals()
        String original=etOriginal.getText().toString();
        if (!new MD5().getMD5ofStr(original).equals(sp.getString(Constants.USER_PASS,new MD5().getMD5ofStr("123456")))) {
            ToastUtil.show("密码输入错误",this);
            return;
        }

        newPass=etPassword.getText().toString();
        String rePass=etCode.getText().toString();
        String msg=LoginUtils.validPwd(newPass);
        if (msg.length()>1) {
            ToastUtil.show(msg,this);
        }else if(rePass.equals(newPass)){
            reset(newPass);
        }else {
            ToastUtil.show("两次输入的新密码不一致",this);
        }

    }

    private void reset(String pass){
       final String  pass1=new MD5().getMD5ofStr(pass);
        final String name=sp.getString(Constants.USER_NAME,"");
        new Thread(){
            @Override
            public void run() {
                super.run();
                String res=WebServiceUtils.getInstance().resetPass(name,pass1);
                if (res.contains("成功")) {
                    handler.sendEmptyMessage(1);
                    sp.edit().putString(Constants.USER_PASS,pass1).apply();
                }else {
                    handler.sendEmptyMessage(2);
                }
            }
        }.start();


    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    ToastUtil.show("修改成功！",SettingActivity.this);
                    finish();
                    break;
                case 2:
                    ToastUtil.show("修改失败！",SettingActivity.this);
                    break;
            }
        }
    };
}
