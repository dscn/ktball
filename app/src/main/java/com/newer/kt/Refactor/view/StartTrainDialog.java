package com.newer.kt.Refactor.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;

/**
 * Created by Neil Zheng on 11/23.
 */
public class StartTrainDialog extends Dialog {

    private BaseActivity activity;
    private LinearLayout chuButton;
    private LinearLayout zhongButton;
    private LinearLayout gaoButton;
    private Button cancelButton;
    private View.OnClickListener chuClick ;
    private View.OnClickListener zhongClick;
    private View.OnClickListener gaoClick;

    public void setItenClickListener(View.OnClickListener chuClick,View.OnClickListener zhongClick,View.OnClickListener gaoClick){
        this.chuClick = chuClick;
        this.zhongClick = zhongClick;
        this.gaoClick = gaoClick;

    }

    private View.OnClickListener OnCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public StartTrainDialog(BaseActivity activity, int themeResId) {
        super(activity, themeResId);
        this.activity = activity;
    }

    public StartTrainDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_starttrain_choose);
        init();
    }

    private void init() {
        chuButton = (LinearLayout) findViewById(R.id.dialog_starttrain_choose_chu);
        zhongButton = (LinearLayout) findViewById(R.id.dialog_starttrain_choose_zhong);
        gaoButton = (LinearLayout) findViewById(R.id.dialog_starttrain_choose_gao);
        cancelButton = (Button) findViewById(R.id.button_cancel);
        chuButton.setOnClickListener(chuClick);
        zhongButton.setOnClickListener(zhongClick);
        gaoButton.setOnClickListener(gaoClick);
        cancelButton.setOnClickListener(OnCancelClick);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(true);
    }
}
