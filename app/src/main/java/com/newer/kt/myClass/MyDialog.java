package com.newer.kt.myClass;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.newer.kt.R;

/**
 * Created by ARTHUR on 2016/4/21.
 */
public class MyDialog extends Dialog {

    Context mContext;
    String mTitle;

    public MyDialog(Context context,String title) {
        super(context);
        mContext = context;
        mTitle = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //没有标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //不可取消
        setCanceledOnTouchOutside(false);
        //设置视图
        setContentView(R.layout.progress);
        //控件赋值
        ImageView imageView = (ImageView) findViewById(R.id.imageView31);
        TextView textView = (TextView) findViewById(R.id.textView21);
        textView.setText(mTitle);
        //设置背景资源（动画）
        imageView.setBackgroundResource(R.drawable.animation);
        //开始动画
        ((AnimationDrawable)imageView.getBackground()).start();
    }
}
