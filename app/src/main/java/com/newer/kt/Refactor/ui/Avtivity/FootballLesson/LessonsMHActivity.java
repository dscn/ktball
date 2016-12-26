package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.FileUtil;
import com.frame.app.utils.LogUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.Manager.bitmap.GlideHelper;
import com.newer.kt.Refactor.Manager.bitmap.RotateTransformation;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jy on 16/6/22.
 */
public class LessonsMHActivity extends BaseActivity {

    private String path;
    private String name;
    private List<View> list;
    private ViewPager viewPager;
    private RelativeLayout close;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.layout_lessins);
        viewPager = (ViewPager) findViewById(R.id.layout_lessons_vp);
        close = (RelativeLayout) findViewById(R.id.layout_lessons_close);
    }

    @Override
    protected void setListener() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        name = intent.getStringExtra("name");
        list = new ArrayList<View>();
        for (int x = 0; x < getFile(path); x++) {
            String path2 = path + "/" + name + (x + 1) + ".jpg";

            Log.e("getFile",path2);
            View view = getView();
            ImageView img = (ImageView) view.findViewById(R.id.layout_imagefragment_iv);
            GlideHelper.loadImageRotated(getThis(), img, path2,new RotateTransformation(getThis(), 90f));
            list.add(view);
        }
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter();
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(0);
    }

    private int getFile(String path) {
        // get file list where the path has
        Log.e("getFile",path);
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();
        Log.e("getFile", "开始遍历..." + array.length);
        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                if (!array[i].isHidden()) {
                    Log.e("getFile",array[i].getPath());
                    ++size;
                }
            }
        }
        Log.e("getFile","结束遍历..." + size);
        return size;
    }

    private View getView() {
        return View.inflate(getThis(), R.layout.layout_imagefragment, null);
    }

    class MyFragmentPagerAdapter extends PagerAdapter {

        //viewpager中的组件数量
        @Override
        public int getCount() {
            return list.size();
        }

        //滑动切换的时候销毁当前的组件
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            ((ViewPager) container).removeView(list.get(position));
        }

        //每次滑动的时候生成的组件
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(list.get(position));
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }
}
