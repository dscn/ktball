package com.newer.kt.Refactor.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;

/**
 * Created by Neil Zheng on 11/23.
 */
public class PauseDialog extends Dialog {

    private BaseActivity activity;
    private TextView title;
    private ImageView train_1;
    private ImageView train_2;
    private ImageView agin;
    private int num;

    public PauseDialog(BaseActivity activity, int themeResId, int num) {
        super(activity, themeResId);
        this.activity = activity;
        this.num = num;
    }

    public PauseDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_trainpause);
        init();
    }

    private void init() {
        title = (TextView) findViewById(R.id.dialog_trainpause_title);
        title.setText(num +"");
        train_1 = (ImageView) findViewById(R.id.dialog_trainpause_train_1);
        train_2 = (ImageView) findViewById(R.id.dialog_trainpause_train_2);
        agin = (ImageView) findViewById(R.id.dialog_trainpause_agin);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(true);
    }

    public void setTrain1OnClickListener(View.OnClickListener clickListener){
        train_1.setOnClickListener(clickListener);
    }

    public void setTrain2OnClickListener(View.OnClickListener clickListener){
        train_2.setOnClickListener(clickListener);
    }

    public void setAginOnClickListener(View.OnClickListener clickListener){
        agin.setOnClickListener(clickListener);
    }
}
