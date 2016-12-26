package com.newer.kt.Refactor.ui.Avtivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.myClass.MyAlertDialog;

public class BagsScannerActivity extends BaseActivity {

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bags_scanner);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public void doBack(View view) {
        finish();
    }

    public void doScan(View view) {
        Intent intent = new Intent(this,CaptureActivity.class);
        intent.putExtra(CaptureActivity.CAPTURE_CODE,1);
        startActivityForResult(intent,1);
    }

    //根据返回的结果判断气场是否存在
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            if (resultCode == 1){
                String bagsScanResult = data.getStringExtra(CaptureActivity.BAGS_SCAN_RESULT);
                if (bagsScanResult.equals("ok")){
//                    new MyAlertDialog(BagsScannerActivity.this).doAlertDialog("ok");
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("扫描气场成功");
//                    builder.setCancelable(false);
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(BagsScannerActivity.this, GameSelectorActivity.class));
//                        }
//                    });
////                    builder.setNegativeButton("取消",null);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                } else {
                    new MyAlertDialog(BagsScannerActivity.this).doAlertDialog("该气场非本校认证气场");
                }
            }
        }
    }
}
