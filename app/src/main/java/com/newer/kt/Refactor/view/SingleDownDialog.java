package com.newer.kt.Refactor.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.frame.app.view.NumberProgressBar.NumberProgressBar;
import com.newer.kt.R;

/**
 * Created by jy on 16/6/21.
 */
public class SingleDownDialog extends Dialog {

    private NumberProgressBar progressBar;
    private TextView name;
    private String nameStr;
    private TextView cancle;

    public SingleDownDialog(Context context, int themeResId, String name) {
        super(context, themeResId);
        nameStr = name;
    }

    public SingleDownDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_singledown);
        init();
    }

    private void init() {
        progressBar = (NumberProgressBar) findViewById(R.id.layout_singledown_progrees);
        cancle = (TextView) findViewById(R.id.layout_singledown_cancle);
        name = (TextView) findViewById(R.id.layout_singledown_name);
        name.setText(nameStr);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismiss();
            }
        });
    }

    public void setProgress(int pregress){
        if(progressBar!=null){
            progressBar.setProgress(pregress);
        }
    }

    public void dialogDismiss(){
        if(progressBar!=null && isShowing()){
            dismiss();
        }
    }

    public void setName(String str){
        if(name != null){
            name.setText(str);
        }
    }
}
