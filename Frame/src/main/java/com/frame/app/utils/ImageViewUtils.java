package com.frame.app.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chongwuzhiwu.frame.R;
import com.frame.app.application.BaseApplication;

/**
 * Created by jy on 15/12/8.
 */
public class ImageViewUtils {

    public static void dynamicChange(Activity context, ImageView iv, int resd){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resd);
        int bwidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();
        int width = PhoneUtils.getScreenWidth(context);
        int height = width * bHeight / bwidth;
        ViewGroup.LayoutParams para = iv.getLayoutParams();
        para.height = height;
        para.width = width;
        iv.setLayoutParams(para);
        LogUtils.e(resd+"");
        iv.setImageResource(resd);
    }
}
