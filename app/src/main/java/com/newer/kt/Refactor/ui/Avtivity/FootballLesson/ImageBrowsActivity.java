package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.Manager.bitmap.GlideHelper;
import com.newer.kt.Refactor.Manager.bitmap.RotateTransformation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 16/10/10.
 */

public class ImageBrowsActivity extends BaseActivity {

    private ViewPager mViewPager;
    private List<String> mJpg = new ArrayList<>();

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        // 选择支持半透明模式,在有surfaceview的activity中使用。
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_image_brows);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_image_brows);
        File file = new File(getIntent().getStringExtra("photoAddress"));
        if (file.exists()) {
            checkPng(file);
        }
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mJpg.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewGroup) container).removeView((View) object);
                object = null;
            }

            @Override
            public Object instantiateItem(ViewGroup view, int position) {
                View view1 = LayoutInflater.from(getThis()).inflate(R.layout.item_image_view,null);
                ImageView im = (ImageView) view1.findViewById(R.id.iamgeview);
                Glide.with(getThis()).load(new File(mJpg.get(position))).transform(new RotateTransformation(getThis(),90)).into(im);
                view.addView(view1);
                return view1;
            }
        });

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    /**
     * 遍历路径下的图片
     */
    private void checkPng(File f) {
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                String path = file.getAbsolutePath();
                if (path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png")) {
                    mJpg.add(path);
                } else {
                    if (!file.getName().contains("_MACOSX"))
                        checkPng(file);
                }
            }
        }

    }

}
