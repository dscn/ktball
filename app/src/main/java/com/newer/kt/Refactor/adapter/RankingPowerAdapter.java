package com.newer.kt.Refactor.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.SharedPreferencesUtils;
import com.ktfootball.www.dao.RankingPower;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.Combinations;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinationDetail;
import com.newer.kt.Refactor.Manager.ListDownManager;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.Request.SchoolGymCourseCombinationDetailRequest;
import com.newer.kt.Refactor.ui.Avtivity.FootballLesson.LessonListAvtivity;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

/**
 * Created by jy on 16/8/10.
 */
public class RankingPowerAdapter extends BaseRecyclerViewAdapter<RankingPower, RankingPowerAdapter.RankingPowerViewhoder> {

    String[] nianJi = {"", "小班", "中班", "大班", "一年级", "二年级", "三年级", "四年级",
            "五年级", "六年级", "初一", "初二", "初三"};

    public RankingPowerAdapter(RecyclerView recyclerView, List<RankingPower> combinationses) {
        super(recyclerView, combinationses);
    }

    @Override
    protected void bindView(final RankingPowerViewhoder holder, int position, final RankingPower model) {
        holder.xm.setText(model.getNickname());
        holder.mc.setText((position+1)+"");
        holder.nl.setText(model.getAge());
        if(model.getGender().equals("GG")){
            holder.xb.setText("男");
        }else{
            holder.xb.setText("女");
        }
        if(model.getSchool_grade() == null){
            holder.nj.setText("无");
        }else{
            holder.nj.setText(nianJi[Integer.parseInt(model.getSchool_grade())]);
        }
        if(model.getSchool_cls() == null){
            holder.bj.setText("无");
        }else{
            holder.bj.setText(model.getSchool_cls()+"班");
        }
        holder.jf.setText(model.getScores());
        holder.zdl.setText(model.getPower());
        holder.sl.setText(model.getWin_rate());
    }

    @Override
    public RankingPowerViewhoder onCreateViewHolder(ViewGroup parent, int viewType) {
        RankingPowerViewhoder combinationClassViewhoder = new RankingPowerViewhoder(getRecyclerView(), layoutInflater(getContext(), R.layout.item_rankingpower, parent, false), this);
        return combinationClassViewhoder;
    }

    public class RankingPowerViewhoder extends BaseViewHolder {

        public TextView mc;
        public TextView xm;
        public TextView nl;
        public TextView xb;
        public TextView nj;
        public TextView bj;
        public TextView jf;
        public TextView zdl;
        public TextView sl;

        public RankingPowerViewhoder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);

            mc = (TextView) rootView.findViewById(R.id.item_rankingpower_mc);
            xm = (TextView) rootView.findViewById(R.id.item_rankingpower_xm);
            nl = (TextView) rootView.findViewById(R.id.item_rankingpower_nl);
            xb = (TextView) rootView.findViewById(R.id.item_rankingpower_xb);
            nj = (TextView) rootView.findViewById(R.id.item_rankingpower_nj);
            bj = (TextView) rootView.findViewById(R.id.item_rankingpower_bj);
            jf = (TextView) rootView.findViewById(R.id.item_rankingpower_jf);
            zdl = (TextView) rootView.findViewById(R.id.item_rankingpower_zdl);
            sl = (TextView) rootView.findViewById(R.id.item_rankingpower_sl);
        }
    }
}
