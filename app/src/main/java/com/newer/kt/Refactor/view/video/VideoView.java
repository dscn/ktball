package com.newer.kt.Refactor.view.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Nathen
 * On 2016/04/27 10:49
 */
public class VideoView extends VideoPlayerStandard {
    public VideoView(Context context) {
        super(context);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean setUp(String url, Object... objects) {
        if (super.setUp(url, objects)) {
            if (mIfCurrentIsFullscreen) {
                titleTextView.setVisibility(View.VISIBLE);
            } else {
                ll_text.setVisibility(View.VISIBLE);
                tv_mvname.setText(objects[0].toString());
//                tv_mvdate.setText(objects[1].toString());
                tv_mvdate.setVisibility(GONE);
                titleTextView.setVisibility(View.INVISIBLE);
            }
            return true;
        }
        return false;
    }
}
