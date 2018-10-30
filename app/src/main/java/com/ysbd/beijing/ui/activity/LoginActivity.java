package com.ysbd.beijing.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.ysbd.beijing.App;
import com.ysbd.beijing.BaseActivity;
import com.ysbd.beijing.R;
import com.ysbd.beijing.base.BaseLoginActivity;
import com.ysbd.beijing.ui.bean.LoginResBean;
import com.ysbd.beijing.utils.CodeUtils;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.LoginUtils;
import com.ysbd.beijing.utils.MD5;
import com.ysbd.beijing.utils.SpUtils;
import com.ysbd.beijing.utils.ToastUtil;
import com.ysbd.beijing.utils.WebServiceUtils;
import com.ysbd.beijing.view.LoadingDialog;

import org.json.JSONObject;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseLoginActivity {
    SharedPreferences sp;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_reset)
    Button btReset;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.ll_b)
    LinearLayout llB;

    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Configuration config = getResources().getConfiguration();
        int smallestScreenWidth = config.smallestScreenWidthDp;
        SpUtils.getInstance().setScreenWidth(smallestScreenWidth);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        llB.setVisibility(View.GONE);
        btReset.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (llB.getVisibility() == View.VISIBLE) {
                    llB.setVisibility(View.GONE);
                } else {
                    llB.setVisibility(View.VISIBLE);
                    etIp.setText(SpUtils.getInstance().getIP());
                }
                return true;
            }
        });
        sp = getSharedPreferences(Constants.SP, MODE_PRIVATE);
        boolean a = sp.getBoolean(Constants.IS_LOGIN, false);
        if (sp.getBoolean(Constants.IS_LOGIN, false)) {
//            Intent intent = new Intent(this, SelectPersonActivity.class);
//            intent.putExtra("actionId", "");//"{ED869C4A-D8BA-4308-8F9B-7B43534C583D}"
//            intent.putExtra("id", "");
////                        WebServiceUtils.getInstance().sendInstance(guid, menuBean.getActionguid(),"");
//            startActivity(intent);
            getAddressBook();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            code = CodeUtils.getInstance().createCode();

            ivCode.setImageBitmap(CodeUtils.getInstance().createBitmap(code));

//            startActivity(new Intent(this, MainActivity.class));
        }

    }

    private String compare1() {
        String a = "1.2.3a";
        String b = "1.3.3b";
        String arrayA[] = a.split("\\.");//
        String arrayB[] = b.split("\\.");
        int minLength = arrayA.length > arrayB.length ? arrayB.length : arrayA.length;
        for (int i = 0; i < minLength; i++) {
            Pattern p = Pattern.compile("[a-zA-z]");
            if (p.matcher(arrayA[i]).find()) {//含有字母的一位
                //arrayA[i]、arrayB[i]的最后一位是字母
                int charStartA = arrayA[i].length() - 1;
                int charStartB = arrayB[i].length() - 1;
                String numA = arrayA[i].substring(0, charStartA);
                String numB = arrayB[i].substring(0, charStartB);
                Integer intA = Integer.parseInt(numA);
                Integer intB = Integer.parseInt(numB);
                if (intA > intB) {
                    return "a的版本号>b的版本号";
                } else if (intA < intB) {
                    return "b的版本号>a的版本号";
                }
                //数字一样时 比较字母 先统一字母格式
                arrayA[i] = arrayA[i].toUpperCase();
                arrayB[i] = arrayB[i].toUpperCase();
                intA = Integer.valueOf(arrayA[i].charAt(charStartA));
                intB = Integer.valueOf(arrayB[i].charAt(charStartB));
                if (intA > intB) {
                    return "a的版本号>b的版本号";
                } else if (intA < intB) {
                    return "b的版本号>a的版本号";
                }
            } else {//先比较前面的数字
                Integer intA = Integer.parseInt(arrayA[i]);
                Integer intB = Integer.parseInt(arrayB[i]);
                if (intA > intB) {
                    return "a的版本号>b的版本号";
                } else if (intA < intB) {
                    return "b的版本号>a的版本号";
                }
            }
        }
        return "";
    }

    @OnClick({R.id.bt_login, R.id.bt_reset, R.id.iv_code})
    public void onViewClicked(View view) {
        setLiveTime();
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.bt_reset:
                etPassword.setText("");
                etCode.setText("");
                break;
            case R.id.iv_code:
                code = CodeUtils.getInstance().createCode();
                ivCode.setImageBitmap(CodeUtils.getInstance().createBitmap(code));
                break;
        }
    }

    LoadingDialog loadingDialog;

    private void login() {
        final String name = etName.getText().toString();
        if (name.length() < 1) {
            ToastUtil.show("请输入用户名", this);
            return;
        }
        String mCode = etCode.getText().toString();
        code = code.toUpperCase();
        mCode = mCode.toUpperCase();
        if (!mCode.equals(code)) {
            ToastUtil.show("验证码输入错误", this);
            return;
        }
        final String pass = etPassword.getText().toString();
//        pass="ab123456";
        //ab123456  初始密码
//        String msg = LoginUtils.validPwd(pass);
//        if (msg.length() > 1) {
//            ToastUtil.show(msg, this);
//        } else

        {
            final String psw = new MD5().getMD5ofStr(pass);
            loadingDialog = new LoadingDialog();
            loadingDialog.show(getSupportFragmentManager());
            final ClipboardManager clipboardManager = (ClipboardManager) App.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    String msg = WebServiceUtils.getInstance().login(name, psw,clipboardManager);
//                    {"success":"false","info":"用户不存在"}
                    handler.obtainMessage(0, msg).sendToTarget();
                    try {
                        JSONObject jsonObject = new JSONObject(msg);
                        if (jsonObject.has("success")) {
                            if (jsonObject.getBoolean("success")) {
                                LoginResBean loginResBean = new Gson().fromJson(msg, LoginResBean.class);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString(Constants.USER_ID, loginResBean.getUserid());
                                editor.putString(Constants.USER_NAME, loginResBean.getUsername());
                                editor.putString(Constants.DEPART_ID, loginResBean.getDepartmentguid());
                                editor.putString(Constants.DEPART_NAME, loginResBean.getDepartmentname());
                                editor.putString(Constants.EMPLOYEE, loginResBean.getEmployee_jobtitles());
                                editor.putString(Constants.USER_PASS, psw);
                                editor.apply();
                                getAddressBook();
                                handler.sendEmptyMessage(1);
                            } else {
                                handler.obtainMessage(2, jsonObject.getString("info")).sendToTarget();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        handler.obtainMessage(2, "登录异常，请稍后再试...").sendToTarget();
                    }
                }
            }.start();


        }
    }


    private void getAddressBook() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                WebServiceUtils.getInstance().refreshAddressBook();
            }
        }.start();

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    try {
                        loadingDialog.cancel();
//                        ToastUtil.show(msg.obj.toString(),LoginActivity.this);
                    } catch (Exception e) {
                        App.catchE(e);
                    }
                    break;
                case 1:
                    sp.edit().putBoolean(Constants.IS_LOGIN, true).apply();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    break;
                case 2:
                    ToastUtil.show("登录失败，请重新登录", LoginActivity.this);
//                    ToastUtil.show(msg.obj.toString(),LoginActivity.this);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                    break;
            }
        }
    };


    @OnClick(R.id.bt_save)
    public void onViewClicked() {
        SpUtils.getInstance().setIp(etIp.getText().toString());
    }
}
