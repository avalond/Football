package cn.brision.football.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AnimRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.util.Util;

/**
 * Created by wangchengcheng on 16/8/29.
 */
public class GlideUtils {

    private static GlideUtils sInstance;
    private Context context;

    public GlideUtils(Context context) {
        this.context = context;
    }

    public static GlideUtils get(Context context) {
        if (sInstance == null) {
            sInstance = new GlideUtils(context);
        }
        return sInstance;
    }

    public void getImage(String url, Drawable holderDrawable, ImageView imageView, @AnimRes int resId) {
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .asBitmap()
                    .animate(resId)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(holderDrawable)
                    .into(imageView);
        }

    }

    public void getImage(String url, ImageView imageView, @AnimRes int resId) {
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .asBitmap()
                    .animate(resId)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);
        }

    }

    public void getImage(String url, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);
        }

    }

    public void getImage(Integer resourceId, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext())
                    .load(resourceId)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);
        }

    }



}
