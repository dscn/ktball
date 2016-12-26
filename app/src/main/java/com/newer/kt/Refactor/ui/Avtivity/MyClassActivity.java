package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseToolBarActivity3;
import com.newer.kt.R;
import com.newer.kt.adapter.MyClassAdapter;
import com.newer.kt.entity.User;
import com.newer.kt.myClass.MyDialog;

import java.util.ArrayList;

import butterknife.Bind;

public class MyClassActivity extends BaseToolBarActivity3 {

    public static final String EXTRA_USER_ID = "userId";
    @Bind(R.id.listView7)
    ListView listView;

    MyClassAdapter myClassAdapter;
    MyDialog myDialog;

    int cls;
    int grade;
    String classname;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.activity_my_class);

    }

    @Override
    protected void initToolBar() {
        setRightImage(R.drawable.sao_yi_sao);
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void setListener() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyClassActivity.this, CaptureActivity.class);
                intent.putExtra(CaptureActivity.CAPTURE_CODE, 3);
                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        final Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        cls = bundle.getInt("cls");
        grade = bundle.getInt("grade");
        classname = bundle.getString("classname");
        setToolBarTitle(classname);
        ArrayList<User> users = (ArrayList<User>) bundle.getSerializable("users");
        myClassAdapter = new MyClassAdapter(users, this);
        listView.setAdapter(myClassAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//点击条目
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(MyClassActivity.this, UserInfoActivity.class);
                String userId = myClassAdapter.getItem(position).user_id;
                intent1.putExtra(EXTRA_USER_ID, userId);
                intent1.putExtra(ListActivity.EXTRA_USER_CODE, 2);
                intent1.putExtra("grade", grade);
                intent1.putExtra("cls", cls);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == 3) {
                myDialog = new MyDialog(this, "正在查询");
                myDialog.show();
                Bundle bundle = data.getBundleExtra("bundle");
                String result = bundle.getString(CaptureActivity.BUNDLE_SCAN_RESULT);
                Intent intent = new Intent(MyClassActivity.this, UserInfoActivity.class);
                intent.putExtra(MyClassActivity.EXTRA_USER_ID, result);
                intent.putExtra(ListActivity.EXTRA_USER_CODE, 2);
                intent.putExtra("grade", grade);
                intent.putExtra("cls", cls);
                myDialog.dismiss();
                startActivity(intent);
            }
        }
    }
}
