package com.newer.kt.Refactor.ui.Fragment.FootballLesson;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.utils.GsonTools;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.BigClassrooms;
import com.newer.kt.Refactor.Entitiy.BigRoomBean;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.adapter.CombinationClassTermAdapter;
import com.newer.kt.Refactor.ui.Avtivity.FootballLesson.BigRoomTremActivity;
import com.newer.kt.Refactor.ui.Avtivity.Settings.BigRoomVedioActivity;
import com.newer.kt.Refactor.ui.Avtivity.Settings.PlayerActivity;
import com.newer.kt.entity.BigClassDbBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbo on 2016/10/2.
 */

public class BigClassFragment extends BaseFragment {
    private BigClassDbBean bean;
    public RecyclerView mRecyclerView;
    private List<String> name = new ArrayList<>();
    private CombinationClassTermAdapter combinationClassAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_recyclerview);
        mRecyclerView = getViewById(R.id.layout_recyclerview_rv);
        LinearLayoutManager ll = new LinearLayoutManager(getThis());
        mRecyclerView.setLayoutManager(ll);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String data = KTApplication.getBigClassDetail();
        bean = GsonTools.changeGsonToBean(data, BigClassDbBean.class);
        name = new ArrayList<>();
        for (int x = 0; x < bean.getClassX().size(); x++) {
            BigClassDbBean.ClassBean classBean = bean.getClassX().get(x);
            name.add(classBean.getClass_name());
        }
        combinationClassAdapter = new CombinationClassTermAdapter(mRecyclerView, name, true);
        mRecyclerView.setAdapter(combinationClassAdapter);
        combinationClassAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Intent intent = new Intent(getThis(), BigRoomTremActivity.class);
                intent.putExtra(Constants.BIG_ROOM_INFO,  bean.getClassX().get(position));
                intent.putExtra("video_id",bean.getClassX().get(position).getVideo_url().split("/")[bean.getClassX().get(position).getVideo_url().split("/").length-1]);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
}
