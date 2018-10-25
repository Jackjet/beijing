package com.ysbd.beijing.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ysbd.beijing.BaseActivity;
import com.ysbd.beijing.BaseFragment;
import com.ysbd.beijing.R;
import com.ysbd.beijing.bean.ActorsBean;
import com.ysbd.beijing.fileEidter.FileReaderActivity;
import com.ysbd.beijing.ui.bean.MenuBean;
import com.ysbd.beijing.ui.fragment.form.BaseFormFragment;
import com.ysbd.beijing.ui.fragment.form.JieyuzijinfawenFragment;
import com.ysbd.beijing.ui.fragment.form.JuneichuanwenFragment;
import com.ysbd.beijing.ui.fragment.form.JuneichuanwenFragment1;
import com.ysbd.beijing.ui.fragment.form.ShizhuanwenFragment;
import com.ysbd.beijing.ui.fragment.form.YibanfawenFragment;
import com.ysbd.beijing.ui.fragment.form.ZhibiaowenFragment;
import com.ysbd.beijing.ui.fragment.form.ZhubanwenFragment;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.FileUtils;
import com.ysbd.beijing.utils.SpUtils;
import com.ysbd.beijing.utils.ToastUtil;
import com.ysbd.beijing.utils.UriUtils;
import com.ysbd.beijing.utils.WebServiceUtils;
import com.ysbd.beijing.utils.selectPhoto.ImageShowActivity;
import com.ysbd.beijing.view.BottomDialog;
import com.ysbd.beijing.view.MenuDialog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FormActivity extends BaseActivity {
    BaseFormFragment formFragment;
    private String guid;
    private String type;
    private String actor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileIdBean fileIdBean = new FileIdBean();
        guid = getIntent().getStringExtra("instanceguid");
        if (guid == null || guid.length() < 1) {
            guid = "{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}";
        }
        actor = getIntent().getStringExtra("actor");
        if (actor == null) {
            actor = "todo";
        }

        type = getIntent().getStringExtra("type");
        fileIdBean.instanceguid = guid;
        String jsonData = new Gson().toJson(fileIdBean);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);
        if (!("todo".equals(actor)||"待办".equals(actor))) {
            findViewById(R.id.tv_send).setVisibility(View.GONE);
        }
        switch (type) {
            case "主办文":
                formFragment = ZhubanwenFragment.getInstance(jsonData, actor);
                break;
            case "局内传文":
                formFragment = JuneichuanwenFragment1.getInstance(guid, actor);
                break;
            case "市转文":
                formFragment = ShizhuanwenFragment.getInstance(guid, actor);
                break;
            case "指标文":
                formFragment = ZhibiaowenFragment.getInstance(guid, actor);
                break;
            case "一般发文":
                formFragment = YibanfawenFragment.getInstance(guid, actor);
                break;
            case "结余资金发文":
                formFragment = JieyuzijinfawenFragment.getInstance(guid, actor);
                break;
            case "结余资金":
                formFragment = JieyuzijinfawenFragment.getInstance(guid, actor);
                break;
            default:
                formFragment = ZhubanwenFragment.getInstance(jsonData, actor);
        }


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 20102);
//            } else {
////                        showDownloadDialog();
////                downLoad("{0A2FF324-FFFF-FFFF-CBC8-42F900000021}");
//            }
//        }
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, formFragment).commit();
    }

    public List<MenuBean> menuBeans = new ArrayList<>();


    public void addMenus(List<MenuBean> menus) {
        if (menus != null) {
            for (int i = 0; i < menus.size(); i++) {
                if (!menus.get(i).getName().contains("起草") ) {//&& !menus.get(i).getName().contains("协办")
                    if (menus.get(i).getName().equals("办结")) {
                        menus.get(i).setName("完成");
                    }
                    menuBeans.add(menus.get(i));
                }
            }

        }
        menuDialog = MenuDialog.getInstance(menuBeans);
    }

    private List<ActorsBean> actors = null;

    private boolean returnBack=false;


    public void setActors(List<ActorsBean> actors) {
        this.actors = actors;
        if (actors!=null&&actors.size()>0) {
            // 1.待办 2.已阅 3.暂存 4.办结
            switch (actors.get(0).getProecssActor().getHandelStatus()) {
                case 1:
                    if (("diong".equals(actor)||"在办".equals(actor))) {

                        returnBack=true;
                        handler.sendEmptyMessage(4);
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
    }

    MenuDialog menuDialog;


    @OnClick({R.id.rlBack, R.id.tv_send, R.id.tv_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlBack:
                finish();
                break;
            case R.id.tv_send:
                if (returnBack) {
                    rBack();
                }else
                if (formFragment.canSend()) {
                    menuDialog = MenuDialog.getInstance(menuBeans);
                    menuDialog.setClick(new MenuDialog.Click() {
                        @Override
                        public void click(MenuBean menuBean) {
                            Intent intent = new Intent(FormActivity.this, SelectPersonActivity.class);
                            intent.putExtra("actionId", menuBean.getActionguid());//"{ED869C4A-D8BA-4308-8F9B-7B43534C583D}"
                            intent.putExtra("id", guid);
                            startActivityForResult(intent, 1);
                        }
                    });
                    menuDialog.show(getSupportFragmentManager());
                }

                break;
            case R.id.tv_history:
//                showDialog();
                if (actors != null) {
                    Intent intent = new Intent(this, HistoryActivity.class);
                    intent.putExtra("actors", (Serializable) actors);
                    startActivity(intent);
                }
                break;

        }
    }
//    7cd7db16c48a5fa039ab300c79d46bc5000000//

    private void rBack(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String data=WebServiceUtils.getInstance().sendInstance(guid,
                        "{WORKFLOW-ACTI-ON00-0000-TAKEBACK0000}", SpUtils.getInstance().getUserId());
                handler.obtainMessage(3,data).sendToTarget();
            }
        }.start();
    }


    public static class FileIdBean {
        String instanceguid;
        String userid = "7cd7db16c48a5fa039ab300c79d46bc5000000";

        public String getInstanceguid() {
            return instanceguid;
        }

        public void setInstanceguid(String instanceguid) {
            this.instanceguid = instanceguid;
        }
    }

    //    {0A2FF324-FFFF-FFFF-CBC8-42F900000021}
    private void downLoad(String attachmentId) {
        RequestBody formBody = new FormBody.Builder()
                .add("attachmentrow_guid", attachmentId)
                .build();
        final Request request = new Request.Builder()
                .url(Constants.LOAD_FILE)
                .post(formBody)
                .build();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                String file = FileUtils.getInstance().makeDir().getPath() + File.separator + "新建文本文档.txt";
                try {
                    FileUtils.getInstance().saveToFile(file, inputStream);
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(FormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(FormActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 20104);
                        } else {
//                            lookFile();
                        }
                    }
                case 3:
                    String m3=msg.obj.toString();

                    if (m3.contains("成功")) {
                        finish();
                        ToastUtil.show("发送成功",FormActivity.this);
                    }else {
                        ToastUtil.show(m3,FormActivity.this);
                    }
                case 4:
                    TextView tvSend= findViewById(R.id.tv_send);
                    tvSend.setText("收回");
                    tvSend.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };


    public void showDialog() {
        BottomDialog bottomDialog = new BottomDialog(this, R.layout.dialog_select_camera,
                new int[]{R.id.dialogSelectCamera_camera, R.id.dialogSelectCamera_album, R.id.dialogSelectCamera_cancel});
        bottomDialog.show();
        bottomDialog.setOnBottomMenuItemClickListener(new BottomDialog.OnBottomMenuItemClickListener() {
            @Override
            public void onBottomMenuItemClick(BottomDialog dialog, View view) {
                switch (view.getId()) {
                    case R.id.dialogSelectCamera_camera:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(FormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(FormActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 20103);
                            } else {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("*/*");
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                startActivityForResult(intent, 10102);
                            }
                        } else {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("*/*");
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            startActivityForResult(intent, 10102);
                        }

                        break;
                    case R.id.dialogSelectCamera_album:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(FormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(FormActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 20101);
                            } else {
                                openAlbum();
                            }
                        } else {
                            openAlbum();
                        }
                        break;
                    case R.id.dialogSelectCamera_cancel:

                        break;
                }
            }
        });

    }

    public void openAlbum() {
//        isShow = false;
        Intent intent = new Intent(this, ImageShowActivity.class);
        intent.putExtra("isSingle", true);
        startActivityForResult(intent, 10101);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10101:
                if (resultCode == Activity.RESULT_OK) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    View inflate = LayoutInflater.from(this).inflate(R.layout.office_dialog_file_info, null);
                    final EditText content = inflate.findViewById(R.id.dialog_file_content);
                    alertDialog.setView(inflate);
                    alertDialog.show();
                    inflate.findViewById(R.id.dialog_file_quxiao).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                        }
                    });
                    inflate.findViewById(R.id.dialog_file_queding).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> paths = data.getStringArrayListExtra("data");
                            if (paths != null && paths.size() != 0) {
                                File file = new File(paths.get(0));

                                upFile(file);

                            }
                            alertDialog.cancel();
                        }
                    });
                }
                break;
            case 10102:
                if (resultCode == Activity.RESULT_OK) {
                    final Uri uri = data.getData();
                    final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    View inflate = LayoutInflater.from(this).inflate(R.layout.office_dialog_file_info, null);
                    final EditText content = inflate.findViewById(R.id.dialog_file_content);
                    alertDialog.setView(inflate);
                    alertDialog.show();
                    inflate.findViewById(R.id.dialog_file_quxiao).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                        }
                    });
                    inflate.findViewById(R.id.dialog_file_queding).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (uri != null) {
                                File file = new File(UriUtils.getPath(FormActivity.this, uri));

                                alertDialog.cancel();
                            } else {
                                ToastUtil.show("上传文件失败，请重试！", FormActivity.this);
                            }
                        }
                    });
                }
                break;
            case 1:
                if (resultCode == 1) {
                    ToastUtil.show("发送成功", this);
                    setResult(1);
                    finish();
                } else if (resultCode == 2) {
                    ToastUtil.show("发送失败", this);
                }
                break;
        }
    }


    private void upFile(final File file) {
        new Thread() {
            @Override
            public void run() {
                super.run();
//                WebServiceUtils.getInstance().updateFile(guid,"",file.getName(),file.getAbsolutePath());
            }
        }.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 0) {
            switch (requestCode) {
                case 20101://录像
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openAlbum();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (!shouldShowRequestPermissionRationale(permissions[0])) {
                                ToastUtil.show("请手动打开“读写手机内存”的权限", FormActivity.this);
                            } else {
                                ToastUtil.show("请手动打开“读写手机内存”的权限", FormActivity.this);
                            }
                        }
                    }
                    break;
                case 20102://录像
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        showDownloadDialog();
//                        downLoad("");
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (!shouldShowRequestPermissionRationale(permissions[0])) {
                                ToastUtil.show("请手动打开“读写手机内存”的权限", FormActivity.this);
                            } else {
                                ToastUtil.show("请手动打开“读写手机内存”的权限", FormActivity.this);
                            }
                        }
                    }
                    break;
                case 20103:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent, 10102);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (!shouldShowRequestPermissionRationale(permissions[0])) {
                                ToastUtil.show("请手动打开“读写手机内存”的权限", FormActivity.this);
                            } else {
                                ToastUtil.show("请手动打开“读写手机内存”的权限", FormActivity.this);
                            }
                        }
                    }
                    break;
                case 20104:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        lookFile();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (!shouldShowRequestPermissionRationale(permissions[0])) {
                                ToastUtil.show("请手动打开“读写手机内存”的权限", FormActivity.this);
                            } else {
                                ToastUtil.show("请手动打开“读写手机内存”的权限", FormActivity.this);
                            }
                        }
                    }
                    break;


            }
        }
    }

    public void lookFile() {
        Log.e("====","===");
//        String filePath = FileUtils.getInstance().makeDir().getPath() + File.separator + list.get(pos).getName();
//        Intent intent=new Intent(getContext(), FileReaderActivity.class);
//        intent.putExtra("filePath",filePath);
//        intent.putExtra("fileName",list.get(pos).getName());
//        intent.putExtra("firstOpen",firstOpen);
//        list.get(pos).setExists(false);
//        adapter.notifyDataSetChanged();
//        startActivity(intent);
//        firstOpen=false;
    }


}
