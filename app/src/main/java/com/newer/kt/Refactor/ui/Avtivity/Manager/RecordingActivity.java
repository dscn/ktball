package com.newer.kt.Refactor.ui.Avtivity.Manager;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.adapter.AdapterItem;
import com.newer.kt.Refactor.adapter.CommonRcvAdapter;
import com.newer.kt.Refactor.adapter.RecordItem;
import com.newer.kt.Refactor.ui.HorCenterRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by huangbo on 2016/10/12.
 */
public class RecordingActivity extends BaseActivity {
    @Bind(R.id.recyclerview)
    HorCenterRecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_football_recording);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getThis(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(new CommonRcvAdapter<String>(mList) {
            @NonNull
            @Override
            public AdapterItem getItemView(Object type) {
                return new RecordItem();
            }
        });
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));

    }

    @OnClick(R.id.tv_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.btn1)
    public void btn() {

    }

    /**
     * 設置間距
     */
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getChildPosition(view) != mList.size() - 1)
                outRect.right = space;
        }
    }
}
