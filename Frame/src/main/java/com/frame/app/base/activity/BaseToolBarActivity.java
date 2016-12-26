package com.frame.app.base.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chongwuzhiwu.frame.R;
import com.frame.app.manager.ToolBarHelper;


/**
 * Created by jiangyu on 15/9/20.
 */
public abstract class BaseToolBarActivity extends BaseActivity {

    private ToolBarHelper mToolBarHelper;
    public Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {

        mToolBarHelper = new ToolBarHelper(getThis(), layoutResID);
        toolbar = mToolBarHelper.getToolBar();
        setContentView(mToolBarHelper.getContentView());
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_color));
        /*把 toolbar 设置到Activity 中*/
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

    protected void setToolBarTitle(CharSequence text) {
        mToolBarHelper.setTitle(text);
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

    protected TextView getRightTextView() {
        return mToolBarHelper.getRightTextView();
    }

    protected ImageView getRightImageView() {
        return mToolBarHelper.getRightImageView();
    }

    protected void getRightTextHide() {
         mToolBarHelper.getRightTextView().setVisibility(View.GONE);
    }

    protected void getRightTextShow() {
        mToolBarHelper.getRightTextView().setVisibility(View.VISIBLE);
    }

    protected void getRightImageViewShow() {
        mToolBarHelper.getRightImageView().setVisibility(View.VISIBLE);
    }

    protected void getRightImageViewHide() {
        mToolBarHelper.getRightImageView().setVisibility(View.GONE);
    }
}
