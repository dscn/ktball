package com.frame.app.base.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chongwuzhiwu.frame.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by jiangyu on 15/9/20.
 */
public abstract class BaseToolBarActivity2 extends BaseActivity {

    public Toolbar toolbar;
    public LinearLayout relativeLayout;
    public TextView title;
    public TextView tv_right;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_basetoolbar);
        relativeLayout = (LinearLayout) findViewById(com.chongwuzhiwu.frame.R.id.layout_baserecycler_parent);
        toolbar = (Toolbar) findViewById(com.chongwuzhiwu.frame.R.id.toolbar);
        title = (TextView) findViewById(com.chongwuzhiwu.frame.R.id.layout_title_tv);
        tv_right = (TextView) findViewById(com.chongwuzhiwu.frame.R.id.layout_title_icon);
        setSupportActionBar(toolbar);
        setDisplayHomeAsUpEnabled(true);
        setDisplayShowCustomEnabled(true);
        setDisplayShowHomeEnabled(true);
        setDisplayShowTitleEnabled(false);
        setNavigationIcon(R.drawable.icon_back);
        initToolBar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnNavigationClick(v);
            }
        });
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
    }

    protected void addContentView(@LayoutRes int layoutResID){
        View view  = View.inflate(getThis(),layoutResID,null);
        relativeLayout.addView(view);
        ButterKnife.bind(this,view);
    }

    protected abstract void initToolBar();

    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    protected void setDisplayHomeAsUpEnabled(boolean b) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(b);
    }

    protected void setDisplayShowHomeEnabled(boolean b) {
        getSupportActionBar().setDisplayShowHomeEnabled(b);
    }

    protected void setDisplayShowTitleEnabled(boolean b) {
        getSupportActionBar().setDisplayShowTitleEnabled(b);
    }

    protected void setDisplayShowCustomEnabled(boolean b) {
        getSupportActionBar().setDisplayShowCustomEnabled(b);
    }

    protected void setToolBarTitle(String titleStr) {
        title.setText(titleStr);
        title.setBackground(null);
    }

    protected void setRightImage(int red){
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("");
        tv_right.setBackgroundResource(red);
    }

    protected void setRightText(String text){
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(text);
        tv_right.setBackgroundResource(0);
    }

    protected TextView getTv_right(){
        return tv_right;
    }

    protected void setNavigationIcon(int resId) {
        toolbar.setNavigationIcon(resId);
    }

    protected void setNavigationIcon(Drawable icon) {
        toolbar.setNavigationIcon(icon);
    }

    protected abstract void OnNavigationClick(View v);

    protected Toolbar getToolbar() {
        return toolbar;
    }

    protected void setBackgroundResource(int red){
        relativeLayout.setBackgroundResource(red);
    }


    protected void setBackgroundColor(int red){
        relativeLayout.setBackgroundColor(red);
    }
}
