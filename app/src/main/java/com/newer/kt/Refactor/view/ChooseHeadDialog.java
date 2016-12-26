package com.newer.kt.Refactor.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;

/**
 * Created by leo on 16/10/21.
 */

public class ChooseHeadDialog extends Dialog {
    private BaseActivity activity;
    private TextView mTv_paizhao, mTv_xiangche, mTv_save, mTv_cancle;

    public ChooseHeadDialog(Context context) {
        super(context);
    }


    public ChooseHeadDialog(BaseActivity activity, int themeResId) {
        super(activity, themeResId);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo_choose);
        mTv_cancle = (TextView) findViewById(R.id.tv_cancle);
        mTv_save = (TextView) findViewById(R.id.tv_save);
        mTv_paizhao = (TextView) findViewById(R.id.tv_paizhao);
        mTv_xiangche = (TextView) findViewById(R.id.tv_xiangche);
        init();
    }

    private void init() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(true);
        mTv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDialogOnClickListener.onSave();
                dismiss();
            }
        });
        mTv_xiangche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDialogOnClickListener.onXiangChe();
                dismiss();
            }
        });

        mTv_paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDialogOnClickListener.onPiaZhao();
                dismiss();
            }
        });

    }

    public interface OnDialogOnClickListener {
        void onSave();

        void onPiaZhao();

        void onXiangChe();
    }

    private OnDialogOnClickListener mOnDialogOnClickListener;

    public void setDialogOnClickListener(OnDialogOnClickListener onClickListener) {
        if (onClickListener != null) {
            this.mOnDialogOnClickListener = onClickListener;
        }

    }

}
