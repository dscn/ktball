package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.newer.kt.Business.BitmapManager;
import com.newer.kt.R;
import com.newer.kt.entity.BigRoomIdiviBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jy on 16/6/23.
 */
public class VedioViewpagerActiviy extends BaseActivity {

    private BigRoomIdiviBean BigRoomIdiviBean;
    private ViewPager viewPager;
    private String name;
    private String path;
    private List<View> list;
    private BigRoomIdiviBean.VideosBean videos;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_vedioviewpager);
        viewPager = (ViewPager) findViewById(R.id.layout_vediovieapager_vp);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        path = getIntent().getStringExtra("path");
        LogUtils.e(path);
        name = getIntent().getStringExtra("name");
        videos = (BigRoomIdiviBean.VideosBean) getIntent().getSerializableExtra("video");
        LogUtils.e(name);
        BigRoomIdiviBean = (BigRoomIdiviBean) getIntent().getSerializableExtra("data");

        String recommendPath = path + name + "/" + name + "介绍";
        list = new ArrayList<View>();
        int fileSize = getFile(recommendPath);
        for (int x = 0; x < fileSize; x++) {
            String imagePath = recommendPath + "/" + name + "介绍" + (x + 1) + ".png";
            LogUtils.e(imagePath);
            View view = getView();
            ImageView img = (ImageView) view.findViewById(R.id.layout_imagefragment_iv);
//            GlideHelper.display(getThis(), img, imagePath);
            BitmapManager.getInstance().display(img, imagePath);
//            img.setImageDrawable(getDownDrawable(imagePath));
            list.add(view);
            if (x == (fileSize - 1)) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), VedioStartActiviy.class);
                        intent.putExtra("path", path);
                        intent.putExtra("name", name);
                        intent.putExtra("data", BigRoomIdiviBean);
                        intent.putExtra("video", videos);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter();
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(0);
    }

    private int getFile(String path) {
        // get file list where the path has
        LogUtils.e(path);
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();
        int size = 0;
        if (array == null) {
            return 0;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                if (!array[i].isHidden()) {
                    LogUtils.e(array[i].getPath());
                    ++size;
                }
            }
        }
        LogUtils.e("结束遍历" + size);
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
