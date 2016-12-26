package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baseproject.utils.Logger;
import com.frame.app.base.activity.BaseToolBarActivity2;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.BigClassrooms;
import com.newer.kt.Refactor.adapter.AdapterItem;
import com.newer.kt.Refactor.adapter.CommonRcvAdapter;
import com.newer.kt.Refactor.ui.Avtivity.Settings.PlayerActivity;
import com.newer.kt.entity.BigClassDbBean;
import com.newer.kt.item.BigRoomTremItem;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by huangbo on 2016/10/2.
 */

public class BigRoomTremActivity extends BaseToolBarActivity2 {
    private BigClassDbBean.ClassBean mClassBean;
    @Bind(R.id.tv_title)
    TextView mTv_title;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_video_title)
    TextView tv_video_title;
    @Bind(R.id.image_video)
    ImageView mImage_Video;




    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.big_room_trem_activity);
        setBackgroundColor(0xff4c4847);
        mClassBean = (BigClassDbBean.ClassBean) getIntent().getSerializableExtra(Constants.BIG_ROOM_INFO);
        setToolBarTitle(mClassBean.getClass_name());
        mTv_title.setText(mClassBean.getClass_name());
        tv_video_title.setText(mClassBean.getClass_name());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setRecycleChildrenOnDetach(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new CommonRcvAdapter<BigClassDbBean.ClassBean.ListBean>(mClassBean.getList()) {
            @NonNull
            @Override
            public AdapterItem getItemView(Object type) {
                return new BigRoomTremItem();
            }

            @Override
            protected void onItemClick(BigClassDbBean.ClassBean.ListBean model, int position) {
                super.onItemClick(model, position);
                Intent intent = new Intent(BigRoomTremActivity.this, BigRoomIndividualActivity.class);
                intent.putExtra("app_cartoon_id",mClassBean.getList().get(position).getId()+"");
                startActivity(intent);
            }
        });
        mImage_Video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mImage_Video.setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                        mImage_Video.setAlpha(1.0f);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.image_video)
    public void startViode(){
        Intent intent = new Intent(getThis(), PlayerActivity.class);
        intent.putExtra("vid",getIntent().getStringExtra("video_id"));
        startActivity(intent);
    }

}
