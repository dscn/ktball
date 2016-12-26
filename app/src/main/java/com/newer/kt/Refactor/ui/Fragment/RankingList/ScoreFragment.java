package com.newer.kt.Refactor.ui.Fragment.RankingList;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.ktfootball.www.dao.RankingLeagueScores;
import com.ktfootball.www.dao.RankingLeagueScores1v1;
import com.ktfootball.www.dao.RankingLeagueScores1v1Dao;
import com.ktfootball.www.dao.RankingLeagueScores3v3;
import com.newer.kt.R;
import com.newer.kt.Refactor.adapter.Score1v1Adapter;
import com.newer.kt.Refactor.adapter.Score2v2Adapter;
import com.newer.kt.Refactor.adapter.Score3v3Adapter;
import com.newer.kt.Refactor.db.RankingLeague1v1DaoHelper;
import com.newer.kt.Refactor.db.RankingLeague3v3DaoHelper;
import com.newer.kt.Refactor.db.RankingLeagueDaoHelper;

import java.util.List;

/**
 * Created by jy on 16/8/23.
 */
public class ScoreFragment extends BaseFragment {

    private TextView v_1;
    private TextView v_2;
    private TextView v_3;
    private RecyclerView rv;
    private LinearLayout model_2;
    private LinearLayout model_1;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_score);
        v_1 = getViewById(R.id.layout_score_list_1);
        v_2 = getViewById(R.id.layout_score_list_2);
        v_3 = getViewById(R.id.layout_score_list_3);
        model_2 = getViewById(R.id.layout_score_model_2);
        model_1 = getViewById(R.id.layout_score_model_1);
        rv = getViewById(R.id.layout_score_rv);
        LinearLayoutManager ll = new LinearLayoutManager(getThis());
        rv.setLayoutManager(ll);
    }

    @Override
    protected void setListener() {
        v_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_1Click();
            }
        });

        v_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_2Click();
            }
        });

        v_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_3Click();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        v_1Click();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    private void v_1Click(){
        v_1.setTextColor(Color.parseColor("#feee35"));
        v_2.setTextColor(Color.parseColor("#f9c143"));
        v_3.setTextColor(Color.parseColor("#f9c143"));
        model_1.setVisibility(View.VISIBLE);
        model_2.setVisibility(View.GONE);
        List<RankingLeagueScores1v1> list = RankingLeague1v1DaoHelper.getInstance().getAllData();
        Score1v1Adapter adapter = new Score1v1Adapter(rv,list);
        rv.setAdapter(adapter);
    }

    private void v_2Click(){
        v_2.setTextColor(Color.parseColor("#feee35"));
        v_1.setTextColor(Color.parseColor("#f9c143"));
        v_3.setTextColor(Color.parseColor("#f9c143"));
        model_1.setVisibility(View.GONE);
        model_2.setVisibility(View.VISIBLE);

        List<RankingLeagueScores> list = RankingLeagueDaoHelper.getInstance().getAllData();
        Score2v2Adapter adapter = new Score2v2Adapter(rv,list);
        rv.setAdapter(adapter);
    }

    private void v_3Click(){
        v_3.setTextColor(Color.parseColor("#feee35"));
        v_2.setTextColor(Color.parseColor("#f9c143"));
        v_1.setTextColor(Color.parseColor("#f9c143"));
        model_1.setVisibility(View.GONE);
        model_2.setVisibility(View.VISIBLE);

        List<RankingLeagueScores3v3> list = RankingLeague3v3DaoHelper.getInstance().getAllData();
        Score3v3Adapter adapter = new Score3v3Adapter(rv,list);
        rv.setAdapter(adapter);
    }
}
