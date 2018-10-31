package com.ysbd.beijing.utils.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.ysbd.beijing.utils.update.ProgressManager.ProgressListener;
import com.ysbd.beijing.utils.update.ProgressManager.ProgressManager;
import com.ysbd.beijing.utils.update.ProgressManager.body.ProgressInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckVersionUtil {

    private final String VERSION_NAME = "http://10.123.27.194:9910/app/bjcz_";
    private int VERSION_CODE = 4;
    private static CheckVersionUtil checkVersionUtil;
    private ProgressDialog progressDialog;
    private Handler mHandler;

    private CheckVersionUtil() {
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public static CheckVersionUtil getInstance() {
        if (checkVersionUtil == null) {
            checkVersionUtil = new CheckVersionUtil();
        }
        return checkVersionUtil;
    }

    public void check(final VersionCall versionCall) {
        final String url_ = VERSION_NAME + (VERSION_CODE + 1) + ".apk";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url_)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        versionCall.notUpdate();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.code() == 200) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            versionCall.update(checkVersionUtil, url_);
                        }
                    });
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            versionCall.notUpdate();
                        }
                    });
                }
            }
        });
    }

    public interface VersionCall {
        void update(CheckVersionUtil checkVersionUtil, String url);

        void notUpdate();
    }

    public void downApk(String url, String apkName, final Activity activity) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = ProgressManager.getInstance().with(builder).build();

        final int[] progressInit = {0};
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        download(okHttpClient, url, apkName, new OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                progressDialog.cancel();
                startActivityApk(activity, file);
                Log.e("APK下载", "安装包下载成功！" + file.length());
            }

            @Override
            public void onDownloading(ProgressInfo progress) {
                int percent = progress.getPercent();
                if (percent >= progressInit[0]) {
                    progressDialog.setProgress(progress.getPercent());
                    progressInit[0] = percent;
                }
            }

            @Override
            public void onDownloadFailed() {
                progressDialog.cancel();
                Toast.makeText(activity, "下载失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startActivityApk(Activity activity, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        setIntentDataAndType(activity, intent, "application/vnd.android.package-archive", file, true);
        activity.startActivity(intent);
    }

    public interface OnDownloadListener {
        void onDownloadSuccess(File file);

        void onDownloading(ProgressInfo progress);

        void onDownloadFailed();
    }

    private void download(OkHttpClient okHttpClient, String url, final String apkName, final OnDownloadListener listener) {
        Request request = new Request
                .Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    if (is != null) {
                        File file = new File(Environment.getExternalStorageDirectory(), apkName);
                        fos = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int ch = -1;
                        while ((ch = is.read(buf)) != -1) {
                            fos.write(buf, 0, ch);
                        }
                    }
                    fos.flush();
                    if (fos != null) {
                        fos.close();
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadSuccess(new File(Environment.getExternalStorageDirectory(), apkName));
                        }
                    });

                } catch (Exception e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadFailed();
                        }
                    });
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
        ProgressManager.getInstance().addResponseListener(url, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                listener.onDownloading(progressInfo);
            }

            @Override
            public void onError(long l, Exception e) {
                listener.onDownloadFailed();
            }
        });
    }

    private void setIntentDataAndType(Context context, Intent intent, String type, File file, boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(context, file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }

    private Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    private Uri getUriForFile24(Context context, File file) {
        Uri fileUri = FileProvider.getUriForFile(context,
                "com.ysbd.beijing.fileprovider", file);
        return fileUri;
    }

}
