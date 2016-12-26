package com.newer.kt.Refactor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.ktfootball.www.dao.RankingLeagueScores1v1;
import com.ktfootball.www.dao.RankingPower;
import com.newer.kt.R;

import java.util.List;

/**
 * Created by jy on 16/8/10.
 */
public class Score1v1Adapter extends BaseRecyclerViewAdapter<RankingLeagueScores1v1, Score1v1Adapter.Score1v1Viewhoder> {

    public Score1v1Adapter(RecyclerView recyclerView, List<RankingLeagueScores1v1> combinationses) {
        super(recyclerView, combinationses);
    }

    @Override
    protected void bindView(final Score1v1Viewhoder holder, int position, final RankingLeagueScores1v1 model) {
        holder.xm.setText(model.getNickname());
        holder.mc.setText((position+1)+"");
        holder.nl.setText(model.getAge());
        if(model.getGender().equals("GG")){
            holder.xb.setText("男");
        }else{
            holder.xb.setText("女");
        }
        if(model.getGrade() == null){
            holder.nj.setText("无");
        }
        if(model.getCls() == null){
            holder.bj.setText("无");
        }
        holder.jf.setText(model.getScores());
        holder.sl.setText(model.getWin_rate());
    }

    @Override
    public Score1v1Viewhoder onCreateViewHolder(ViewGroup parent, int viewType) {
        Score1v1Viewhoder combinationClassViewhoder = new Score1v1Viewhoder(getRecyclerView(), layoutInflater(getContext(), R.layout.item_score1v1, parent, false), this);
        return combinationClassViewhoder;
    }

    public class Score1v1Viewhoder extends BaseViewHolder {

        public TextView mc;
        public TextView xm;
        public TextView nl;
        public TextView xb;
        public TextView nj;
        public TextView bj;
        public TextView jf;
        public TextView sl;

        public Score1v1Viewhoder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);

            mc = (TextView) rootView.findViewById(R.id.item_score_mc);
            xm = (TextView) rootView.findViewById(R.id.item_score_xm);
            nl = (TextView) rootView.findViewById(R.id.item_score_nl);
            xb = (TextView) rootView.findViewById(R.id.item_score_xb);
            nj = (TextView) rootView.findViewById(R.id.item_score_nj);
            bj = (TextView) rootView.findViewById(R.id.item_score_bj);
            jf = (TextView) rootView.findViewById(R.id.item_score_jf);
            sl = (TextView) rootView.findViewById(R.id.item_score_sl);
        }
    }
}
