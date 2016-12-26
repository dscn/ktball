package com.frame.app.base.activity;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chongwuzhiwu.frame.R;
import com.frame.app.utils.PhoneUtils;


/**
 * Created by jiangyu on 15/9/20.
 * 自定义title
 */
public abstract class BaseToolBarActivity3 extends BaseActivity {

    public LinearLayout relativeLayout;
    public TextView title;
    public TextView tv_right;
    private RelativeLayout left_rl_img;
    private RelativeLayout left_rl_tv;
    private TextView left_tv;
    private ImageView left_img;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_basetoolbar3);
        //初始化title
        left_rl_img = (RelativeLayout) findViewById(R.id.layout_title_left_rl);
        left_rl_tv = (RelativeLayout) findViewById(R.id.layout_title_left_rl_2);
        left_tv = (TextView) findViewById(R.id.layout_title_left_tv);
        left_img = (ImageView) findViewById(R.id.layout_title_left_img);
        title = (TextView) findViewById(R.id.layout_title_title);
        tv_right = (TextView) findViewById(R.id.layout_title_right_tv);
        relativeLayout = (LinearLayout) findViewById(R.id.layout_baserecycler3_parent);
        initToolBar();
        left_rl_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnNavigationClick(v);
            }
        });
        left_rl_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnNavigationClick(v);
            }
        });
    }

    protected void addContentView(@LayoutRes int layoutResID){
        View view  = View.inflate(getThis(),layoutResID,null);
        relativeLayout.addView(view);
    }

    protected abstract void initToolBar();

    protected void setToolBarTitle(String titleStr) {
        title.setText(titleStr);
        title.setBackground(null);
    }

    protected void setRightImage(int red){
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("");
        tv_right.setBackgroundResource(red);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv_right.getLayoutParams();
        lp.width = PhoneUtils.dip2px(getThis(),20);
        lp.height = PhoneUtils.dip2px(getThis(),20);
        tv_right.setLayoutParams(lp);
    }

    protected void setRightText(String text){
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(text);
        tv_right.setBackgroundResource(0);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv_right.getLayoutParams();
        lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        lp.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        tv_right.setLayoutParams(lp);
    }

    protected TextView getTv_right(){
        return tv_right;
    }

    protected void setNavigationIcon(int resId) {
        left_rl_tv.setVisibility(View.GONE);
        left_rl_img.setVisibility(View.VISIBLE);
        left_img.setImageResource(resId);
    }

    protected void setNavigationIcon(Drawable icon) {
        left_rl_tv.setVisibility(View.GONE);
        left_rl_img.setVisibility(View.VISIBLE);
        left_img.setImageDrawable(icon);
    }

    protected void setNavigationTextView(String str) {
        left_rl_img.setVisibility(View.GONE);
        left_rl_tv.setVisibility(View.VISIBLE);
        left_tv.setText(str);
    }

    protected abstract void OnNavigationClick(View v);

    protected void setOnNavigationClick(View.OnClickListener listener){
        left_rl_img.setOnClickListener(listener);
        left_rl_tv.setOnClickListener(listener);
    }

    protected void setRightClickListener(View.OnClickListener listener){
        tv_right.setOnClickListener(listener);
    }

    protected void setBackgroundResource(int red){
        relativeLayout.setBackgroundResource(red);
    }
}
