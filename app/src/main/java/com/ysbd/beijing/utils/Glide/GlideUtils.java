package com.ysbd.beijing.utils.Glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

public class GlideUtils {
    private static GlideUtils glideUtils;

    public static GlideUtils getInstence() {
        if (glideUtils == null) {
            glideUtils = new GlideUtils();
        }
        return glideUtils;
    }

    public void load(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public void load(Context context, File file, ImageView imageView) {
        Glide.with(context)
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public void load(Context context, int resourceId, ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载为圆形图片
     */
    public void loadCircle(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transform(new GlideCircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(1000)
                .into(imageView);
    }

    public void loadCircle(Context context, int resourceId, ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .transform(new GlideCircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(1000)
                .into(imageView);
    }

    /**
     * 加载为圆角图片
     */
    public void loadRadius(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transform(new GlideRoundTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public void loadadius(Context context, String url, int radius, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transform(new GlideRoundTransform(context, radius))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public void loadRadius(Context context, int resourceId, ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .transform(new GlideRoundTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public void loadadius(Context context, int resourceId, int radius, ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .transform(new GlideRoundTransform(context, radius))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
