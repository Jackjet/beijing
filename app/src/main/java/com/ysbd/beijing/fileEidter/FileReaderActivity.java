package com.ysbd.beijing.fileEidter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awen.photo.FrescoImageLoader;
import com.awen.photo.photopick.controller.PhotoPagerConfig;
import com.tencent.smtt.sdk.TbsReaderView;
import com.ysbd.beijing.App;
import com.ysbd.beijing.R;
import com.ysbd.beijing.utils.FileUtils;
import com.ysbd.beijing.utils.ToastUtil;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileReaderActivity extends Activity implements TbsReaderView.ReaderCallback {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.llRoot)
    LinearLayout llRoot;
    @BindView(R.id.fl_edit)
    FrameLayout flEdit;

    private TbsReaderView mTbsReaderView;
    private String filePath;
    private boolean firstOpen;
    private boolean isDocument;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_reader);
        ButterKnife.bind(this);
        filePath = getIntent().getStringExtra("filePath");
        mTbsReaderView = new TbsReaderView(this, this);
//        isDocument=getIntent().getBooleanExtra("isDocument",false);
        firstOpen = getIntent().getBooleanExtra("firstOpen", false);
        if (!isDocument) {
            flEdit.setVisibility(View.GONE);
        }
//        mTbsReaderView.onStop();
//        mTbsReaderView= new TbsReaderView(this, this);
        tvTitle.setText("文件详情");
//        if (filePath.length() > 1) {
////            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filePath);
//            displayFile(filePath);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (filePath.length() > 1) {
//            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filePath);
            displayFile(filePath);
        }
    }

    @OnClick({R.id.rlBack, R.id.fl_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlBack:
                finish();
                break;
            case R.id.fl_edit:
//                Intent intent = FileUtils.getInstance().openFile(filePath, this);
//                startActivity(intent);
//                finish();
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra("path", filePath);
                int index = filePath.lastIndexOf("/");
                String name = "";
                if (index > 1) {
                    filePath.substring(index + 1);
                }
                intent.putExtra("filename", name);
                intent.putExtra("uploadurl", "");
                startActivityForResult(intent, 101);
                break;
        }

    }

    private void displayFile(String filePath) {
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
//        show(parseFormat(filePath));
        String extension = getExtensionName(filePath);

        boolean result = mTbsReaderView.preOpen(extension, false);
        if (result) {

            llRoot.addView(mTbsReaderView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            mTbsReaderView.openFile(bundle);
            if (firstOpen) {
                firstOpen = false;
                llRoot.removeAllViews();
                mTbsReaderView.onStop();
                mTbsReaderView = new TbsReaderView(this, this);
                mTbsReaderView.preOpen(extension, false);
                llRoot.addView(mTbsReaderView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
               mTbsReaderView.openFile(bundle);

            }

//            mTbsReaderView.openFile(bundle);
//            if (getIntent().getBooleanExtra("firstOpen",false)) {
//                Intent intent=new Intent(this,FileReaderActivity.class);
//                intent.putExtra("filePath",filePath);
//                intent.putExtra("fileName",getIntent().getStringExtra("fileName"));
//                intent.putExtra("firstOpen",false);
//                startActivity(intent);
//                finish();
//            }
        } else {
            lookFile(filePath);
//            finish();
        }

    }

    public void lookFile(String filePath) {
        if (FileUtils.getInstance().getFileType(new File(filePath)).equals("image/*")) {
//            iv_content.setVisibility(View.VISIBLE);
//            File file = new File(filePath);
//            Glide.with(this)
//                    .load(file)
//                    .into(iv_content);
//            ProgressDialog progressDialog=new ProgressDialog(this);
//            progressDialog.setMessage("正在解析文件信息...");
//            progressDialog.show();
//            progressDialog.setCancelable(true);
//            progressDialog.dismiss();
            ArrayList<String> path = new ArrayList<>();
            path.add(FrescoImageLoader.getFileUrl(filePath));
            new PhotoPagerConfig.Builder(this)
                    .setBigImageUrls(path)
//                    .setSavaImage(true)
//                        .setPosition(2)
//                        .setSaveImageLocalPath("这里是你想保存的图片地址")
                    .build();

            finish();
            return;
        }
        try {
            Intent intent = FileUtils.getInstance().openFile(filePath, this);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            ToastUtil.show("没有找到打开该文件的程序", this);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 101) {

        }

    }

    private void show(String m) {
        ToastUtil.show(m, this);
    }

    private String parseFormat(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "doc";
        }

    }

    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    private void loadImg(String filePath) {
        if (Build.VERSION.SDK_INT >= 24) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        ContentValues contentValues = new ContentValues(1);
//        contentValues.put(MediaStore.Images.Media.DATA, param);
            Uri uri = FileProvider7.getUriForFile(this, new File(filePath));
            intent.setDataAndType(uri, "image/*");
            startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(Uri.fromFile(new File(filePath)), "image/*");
            startActivity(intent);
        }

//        new PhotoPagerConfig.Builder(this)
////                .setBigImageUrls(ImageProvider.getImageUrls())      //大图片url,可以是sd卡res，asset，网络图片.
////                .setSmallImageUrls(ArrayList<String> smallImgUrls)  //小图图片的url,用于大图展示前展示的
////                .addSingleBigImageUrl(String bigImageUrl)           //一张一张大图add进ArrayList
////                .addSingleSmallImageUrl(String smallImageUrl)       //一张一张小图add进ArrayList
//                .setSavaImage(true)                                 //开启保存图片，默认false
//                .setPosition(0)                                     //默认展示第2张图片
//                .setSaveImageLocalPath(filePath)        //这里是你想保存大图片到手机的地址,可在手机图库看到，不传会有默认地址
////                .setBundle(bundle)                                  //传递自己的数据，如果数据中包含java bean，必须实现Parcelable接口
//                .setOpenDownAnimate(false)                          //是否开启下滑关闭activity，默认开启。类似微信的图片浏览，可下滑关闭一样
//                .build();

    }

    public final static String spName = "ADLUtils";

    public void openFileAndReset(String filePath, boolean parse) {
        Intent intent;
        intent = FileUtils.getInstance().openFile(filePath, this);
        if (intent == null)
            return;
        try {
            SharedPreferences sp = getSharedPreferences(spName, Context.MODE_PRIVATE);
            sp.edit().putString(intent.getType(), "").apply();
            PackageManager pm = getPackageManager();
            List<ResolveInfo> bList = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (bList.size() > 1) {
                start(bList.get(0), false, intent);
//                ActivityChooser activityChooser = new ActivityChooser(bList, context, this);
//                activityChooser.show(v);
            } else if (bList.size() == 1) {
                start(bList.get(0), false, intent);
            } else {
                String type = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length()).toLowerCase();
                Toast.makeText(this, "请您先安装能够打开" + type + "类型文件的程序！", Toast.LENGTH_SHORT).show();
            }

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void start(ResolveInfo info, boolean isRemember, Intent intent) {

        String packageName = info.activityInfo.packageName;
        // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
        String className = info.activityInfo.name;
        // LAUNCHER Intent
//            Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // 设置ComponentName参数1:packagename参数2:MainActivity路径
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        startActivity(intent);
        SharedPreferences sp = getSharedPreferences(spName, Context.MODE_PRIVATE);
        if (isRemember) {
            sp.edit().putString(intent.getType(), packageName + "&&" + className).apply();
//            sp.edit().putString(intent.getType()+"name",info.loadLabel(context.getPackageManager()).toString()).apply();
        }
//        handler.sendEmptyMessage(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
        if (!isDocument) {
            File localFile = getLocalFile(filePath);
            if (localFile != null && localFile.exists()) {
                localFile.delete();
            }
        }

    }

    private File getLocalFile(String filePath) {
        return new File(filePath);
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        Intent intent = FileUtils.getInstance().openFile(filePath, this);
        startActivity(intent);
        finish();

    }
}
