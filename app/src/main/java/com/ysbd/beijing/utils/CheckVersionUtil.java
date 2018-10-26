package com.ysbd.beijing.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckVersionUtil {

    private final String VERSION_NAME = "imageView2/1/w/240/h/240";
    private int VERSION_CODE = 1;
    private static CheckVersionUtil checkVersionUtil;

    private CheckVersionUtil() {
    }

    public static CheckVersionUtil getInstance() {
        if (checkVersionUtil == null) {
            checkVersionUtil = new CheckVersionUtil();
        }
        return checkVersionUtil;
    }

    public void check(final VersionCall versionCall) {
        final String url_ = VERSION_NAME + (VERSION_CODE + 1);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(VERSION_NAME)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                versionCall.notUpdate();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.code() == 200) {
                    versionCall.update(url_);
                } else {
                    versionCall.notUpdate();
                }
            }
        });
    }

    public interface VersionCall {
        void update(String url);

        void notUpdate();
    }
}
