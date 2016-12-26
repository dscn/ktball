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

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;

/**
 * Created by Neil Zheng on 11/23.
 */
public class TrainCloseDialog extends Dialog {

    private BaseActivity activity;
    private ImageView train_1;
    private ImageView train_2;

    public TrainCloseDialog(BaseActivity activity, int themeResId) {
        super(activity, themeResId);
        this.activity = activity;
    }

    public TrainCloseDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_trainclose);
        init();
    }

    private void init() {
        train_1 = (ImageView) findViewById(R.id.dialog_trainpause_train_1);
        train_2 = (ImageView) findViewById(R.id.dialog_trainpause_train_2);
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
}
