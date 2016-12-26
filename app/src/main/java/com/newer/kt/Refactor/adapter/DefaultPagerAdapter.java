package com.newer.kt.Refactor.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Neil Zheng on 12/08.
 */
public class DefaultPagerAdapter<T extends View>  extends PagerAdapter {

    private ArrayList<T> views;

    public DefaultPagerAdapter() {
    }

    public DefaultPagerAdapter(ArrayList<T> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views.get(position));
        return views.get(position);
    }

    /**
     * 插入一个View到viewpager中
     *
     * @param view
     * @return
     */
    public boolean insert(T view) {
        if (this.views == null) {
            this.views = new ArrayList<T>();
        }
        return this.views.add(view);
    }

    public boolean insert(ArrayList<T> list) {
        if (this.views == null) {
            this.views = new ArrayList<T>();
        }
        return this.views.addAll(list);
    }
}

