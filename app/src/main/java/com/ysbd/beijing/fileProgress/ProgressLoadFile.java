package com.ysbd.beijing.fileProgress;


import android.app.ProgressDialog;
import android.content.Context;

import com.ysbd.beijing.utils.CommentFormUtils;
import com.ysbd.beijing.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;


/**
 * Created by ${LCJ} on 2017/3/1.
 */

public class ProgressLoadFile {


    public static void attachmentUpLoad(Context context, Map<String, String> params, okhttp3.Callback callback, File file) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在上传，请稍后...");
        int cacheSize = 20 * 1024 * 1024;
        //设置超时时间及缓存
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new okhttp3.Cache(file.getAbsoluteFile(), cacheSize));
        okhttp3.OkHttpClient mOkHttpClient = builder.build();
        MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (true) {
            mbody.addFormDataPart(params.get("fileName"), file.getName(), createCustomRequestBody(MediaType.parse(FileUtils.getFileType(file)), file, new ProgressListener() {
                @Override
                public void onProgress(long totalBytes, long remainingBytes, boolean done) {
                    progressDialog.setProgress((int) ((totalBytes - remainingBytes) * 100 / totalBytes));
                    progressDialog.setMax(100);
                    if (remainingBytes == 0) {
                        progressDialog.dismiss();
                    }
                }
            }));
            okhttp3.Request request = new okhttp3.Request.Builder()
//                    .addHeader("auth-tenantId",params.get("auth-tenantId"))
//                    .addHeader("auth-userId",params.get("auth-userId"))
                    .url(params.get("url"))
                    .post(mbody.build())
                    .build();
            mOkHttpClient.newCall(request).enqueue(callback);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }


    }

    public static void docUpLoad(Context context, Map<String, String> params, okhttp3.Callback callback, File file) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在上传，请稍后...");
        long l = file.length();
        int cacheSize = 20 * 1024 * 1024;
        //设置超时时间及缓存
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new okhttp3.Cache(file.getAbsoluteFile(), cacheSize));
        okhttp3.OkHttpClient mOkHttpClient = builder.build();
        String url = params.get("url") + "?instanceGUID=" + params.get("instanceGUID")
                + "&DocumentRowGUID=" + params.get("DocumentRowGUID")
                + "&templateGUID=" + params.get("templateGUID")
                + "&templateName=" + params.get("templateName")
                + "&imgID=" + params.get("imgID")
                + "&step=" + params.get("step")
                + "&documentTitle=" + params.get("documentTitle")
                + "&fileName=" + params.get("fileName")
                + "&userid=" + params.get("userId");
        MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (true) {
            mbody.addFormDataPart("chengbaoneirongjncwnew",params.get("fileName"),
                    createCustomRequestBody(MediaType.parse("application/msword"), file,
                            new ProgressListener() {
                                @Override
                                public void onProgress(long totalBytes, long remainingBytes, boolean done) {
                                    progressDialog.setProgress((int) ((totalBytes - remainingBytes) * 100 / totalBytes));
                                    progressDialog.setMax(100);
                                    if (remainingBytes == 0) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }));

            okhttp3.Request request = new okhttp3.Request.Builder()
//                    .addHeader("auth-tenantId",params.get("auth-tenantId"))
//                    .addHeader("auth-userId",params.get("auth-userId"))
                    .url(url)
                    .post(mbody.build())
                    .build();
            mOkHttpClient.newCall(request).enqueue(callback);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }


    }


    public static void up(Map<String, String> params, okhttp3.Callback callback, File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/msword"), file);

        RequestBody requestBody = new MultipartBody.Builder().addFormDataPart(params.get("templateName"), file.getName(), fileBody).build();
        int cacheSize = 20 * 1024 * 1024;
        //设置超时时间及缓存
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)
                .cache(new okhttp3.Cache(file.getAbsoluteFile(), cacheSize));

        String url = params.get("url") + "?instanceGUID=" + params.get("instanceGUID")
                + "&DocumentRowGUID=" + params.get("DocumentRowGUID")
                + "&templateGUID=" + params.get("templateGUID")
                + "&templateName=" + params.get("templateName")
                + "&imgID=" + params.get("imgID")
                + "&step=" + params.get("step")
                + "&documentTitle=" + params.get("documentTitle")
                + "&fileName=" + params.get("fileName")
        + "&userid=" + params.get("userId");
        final Request request = new Request.Builder().url(url).post(fileBody).build();
        Request requestPostFile = new Request.Builder()
                .url(url)//"http://10.11.64.50/upload/UploadServlet"
                .post(requestBody)
                .build();
        okhttp3.OkHttpClient client = builder.build();
        client.newCall(requestPostFile).enqueue(callback);
//        Call call = new OkHttpClient.Builder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
//        call.enqueue(callback);
    }


    public static void avatarUpLoad(Context context, Map<String, String> params, okhttp3.Callback callback, File file) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在上传，请稍后...");
        int cacheSize = 20 * 1024 * 1024;
        //设置超时时间及缓存
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new okhttp3.Cache(file.getAbsoluteFile(), cacheSize));
        okhttp3.OkHttpClient mOkHttpClient = builder.build();
        MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file.exists()) {
            mbody.addFormDataPart(params.get("fileName"), file.getName(), createCustomRequestBody(MediaType.parse("*/*"), file, new ProgressListener() {
                @Override
                public void onProgress(long totalBytes, long remainingBytes, boolean done) {
                    progressDialog.setProgress((int) ((totalBytes - remainingBytes) * 100 / totalBytes));
                    progressDialog.setMax(100);
                    if (remainingBytes == 0) {
                        progressDialog.dismiss();
                    }
                }
            }));
        }

        okhttp3.Request request = new okhttp3.Request.Builder()
                .addHeader("auth-tenantId", params.get("auth-tenantId"))
                .addHeader("auth-userId", params.get("auth-userId"))
//                .addHeader("Connection", "close")
                .url(params.get("url"))
                .post(mbody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
        progressDialog.show();
    }

    public static void downLoad(Context context, Map<String, String> params, okhttp3.Callback callback) {
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        okhttp3.OkHttpClient mOkHttpClient = builder.build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(params.get("url"))
                .addHeader("auth-tenantId", params.get("auth-tenantId"))
                .addHeader("auth-userId", params.get("auth-userId"))
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    private static RequestBody createCustomRequestBody(final MediaType contentType, final File file, final ProgressListener listener) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    //sink.writeAll(source);
                    Buffer buf = new Buffer();
                    Long remaining = contentLength();
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        listener.onProgress(contentLength(), remaining -= readCount, remaining == 0);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    interface ProgressListener {
        void onProgress(long totalBytes, long remainingBytes, boolean done);
    }


}
