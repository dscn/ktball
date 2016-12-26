package com.newer.kt.Refactor.ui.Fragment.RankingList;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.frame.app.base.fragment.BaseFragment;
import com.ktfootball.www.dao.RankingPower;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.adapter.RankingPowerAdapter;
import com.newer.kt.Refactor.db.RankingPowerDaoHelper;

import java.util.List;

/**
 * Created by jy on 16/8/23.
 */
public class FightingPowerFragment extends BaseFragment {

    private RecyclerView rv;
    private List<RankingPower> list;
    private int gradeid;
    private int clsid;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_fightingpower);
        rv = getViewById(R.id.layout_fighting_rv);
        LinearLayoutManager ll = new LinearLayoutManager(getThis());
        rv.setLayoutManager(ll);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle == null){
            list = RankingPowerDaoHelper.getInstance().getAllData();
        }else{
            gradeid = bundle.getInt(Constants.GRADEID,-1);
            clsid = bundle.getInt(Constants.CLSID,-1);
            if(gradeid ==  -1 && clsid == -1){
                list = RankingPowerDaoHelper.getInstance().getAllData();
            }else if(gradeid !=  -1 && clsid == -1){
                list = RankingPowerDaoHelper.getInstance().queryByGrade(gradeid+"");
            }else if(gradeid !=  -1 && clsid != -1){
                list = RankingPowerDaoHelper.getInstance().queryByGrade(gradeid+"",clsid+"");
            }
        }
        RankingPowerAdapter adapter = new RankingPowerAdapter(rv,list);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
}
