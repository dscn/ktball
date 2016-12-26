package com.newer.kt.engine;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.myClass.GlideCircleTransform;

import java.io.File;

/**
 * Created by Taurus on 15/8/6.
 * Glide加载库配置管理
 */
public class GlideHelper {

    private static DiskCacheStrategy diskCacheStrategy_result = DiskCacheStrategy.ALL;
    public static DiskCacheStrategy diskCacheStrategy_result_no_cache = DiskCacheStrategy.NONE;
    private static final int diskCacheSize = 200 * 1024 * 1024;
    private static final String cache_name = "cache";

    static {
        String path = DirectoryManager.getInstance().getBitmapCacheDirPath();
        DiskLruCacheFactory diskLruCacheFactory = new DiskLruCacheFactory(path, cache_name, diskCacheSize);
        diskLruCacheFactory.build();
    }

    public static void clearCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(KTApplication.getContext()).clearDiskCache();
                Glide.get(KTApplication.getContext()).clearMemory();
            }
        });
    }

    public static void displayFromCache(ImageView view, String url) {
        Glide.with(KTApplication.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    public static void display(Context context, ImageView view, String url, SimpleTarget<Bitmap> simpleTarget) {
        load(context, view, url, simpleTarget);
    }

    public static void displayCirlcle(Context context, String path, ImageView view) {
        Glide.with(context).load(new File(path)).transform(new GlideCircleTransform(context)).into(view);
    }
    public static void display(Context context, ImageView view, String url, BitmapImageViewTarget bitmapImageViewTarget) {
        load(context, view, url, bitmapImageViewTarget);
    }

    public static void display(Activity context, ImageView view, String url, BitmapImageViewTarget bitmapImageViewTarget) {
        load(context, view, url, bitmapImageViewTarget);
    }

    public static void display(Context context, ImageView view, String url) {
        load(context, view, url);
    }

    public static void display(Activity activity, ImageView view, String url) {
        load(activity, view, url);
    }

    public static void display(Fragment fragment, ImageView view, String url) {
        load(fragment, view, url);
    }

    public static void display(android.support.v4.app.Fragment fragment, ImageView view, String url) {
        load(fragment, view, url);
    }

    public static void display(FragmentActivity fragment, ImageView view, String url) {
        load(fragment, view, url);
    }

    public static void display(Context context, ImageView view, String url, int placeHolder, DiskCacheStrategy diskCacheStrategy) {
        load(context, view, url, placeHolder, diskCacheStrategy);
    }

    public static void display(android.support.v4.app.Fragment fragment, ImageView view, String url, int placeHolder, DiskCacheStrategy diskCacheStrategy) {
        load(fragment, view, url, placeHolder, diskCacheStrategy);
    }

    public static void display(Context context, ImageView view, String url, int placeHolder) {
        load(context, view, url, placeHolder);
    }

    public static void display(Activity activity, ImageView view, String url, int placeHolder) {
        load(activity, view, url, placeHolder);
    }

    public static void display(Fragment fragment, ImageView view, String url, int placeHolder) {
        load(fragment, view, url, placeHolder);
    }

    public static void display(android.support.v4.app.Fragment fragment, ImageView view, String url, int placeHolder) {
        load(fragment, view, url, placeHolder);
    }

    public static void display(FragmentActivity fragment, ImageView view, String url, int placeHolder) {
        load(fragment, view, url, placeHolder);
    }

    private static void load(Context context, ImageView view, String url, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).into(simpleTarget);
    }

    private static void load(Context context, ImageView view, String url, BitmapImageViewTarget bitmapImageViewTarget) {
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).into(bitmapImageViewTarget);
    }

    private static void load(Activity context, ImageView view, String url, BitmapImageViewTarget bitmapImageViewTarget) {
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).into(bitmapImageViewTarget);
    }

    private static void load(Context context, ImageView view, String url) {
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).into(view);
    }

    private static void load(Activity activity, ImageView view, String url) {
        Glide.with(activity).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).into(view);
    }

    private static void load(Fragment fragment, ImageView view, String url) {
        Glide.with(fragment).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).into(view);
    }

    private static void load(android.support.v4.app.Fragment fragment, ImageView view, String url) {
        Glide.with(fragment).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).into(view);
    }

    private static void load(FragmentActivity fragment, ImageView view, String url) {
        Glide.with(fragment).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).into(view);
    }

    private static void load(Context context, ImageView view, String url, int placeHolder, DiskCacheStrategy diskCacheStrategy) {
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy).placeholder(placeHolder).into(view);
    }

    private static void load(android.support.v4.app.Fragment fragment, ImageView view, String url, int placeHolder, DiskCacheStrategy diskCacheStrategy) {
        Glide.with(fragment).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy).placeholder(placeHolder).into(view);
    }

    private static void load(Context context, ImageView view, String url, int placeHolder) {
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).placeholder(placeHolder).into(view);
    }

    private static void load(Activity activity, ImageView view, String url, int placeHolder) {
        Glide.with(activity).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).placeholder(placeHolder).into(view);
    }

    private static void load(Fragment fragment, ImageView view, String url, int placeHolder) {
        Glide.with(fragment).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).placeholder(placeHolder).into(view);
    }

    private static void load(android.support.v4.app.Fragment fragment, ImageView view, String url, int placeHolder) {
        Glide.with(fragment).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).placeholder(placeHolder).into(view);
    }

    private static void load(FragmentActivity fragment, ImageView view, String url, int placeHolder) {
        Glide.with(fragment).load(url).asBitmap().diskCacheStrategy(diskCacheStrategy_result).placeholder(placeHolder).into(view);
    }

}
