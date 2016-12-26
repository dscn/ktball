package com.newer.kt.Refactor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.ktfootball.www.dao.RankingLeagueScores;
import com.ktfootball.www.dao.RankingLeagueScores1v1;
import com.ktfootball.www.dao.RankingLeagueScores3v3;
import com.newer.kt.R;

import java.util.List;

/**
 * Created by jy on 16/8/10.
 */
public class Score2v2Adapter extends BaseRecyclerViewAdapter<RankingLeagueScores, Score2v2Adapter.Score2v2Viewhoder> {

    public Score2v2Adapter(RecyclerView recyclerView, List<RankingLeagueScores> combinationses) {
        super(recyclerView, combinationses);
    }

    @Override
    protected void bindView(final Score2v2Viewhoder holder, int position, final RankingLeagueScores model) {
        holder.xm.setText(model.getName());
        holder.mc.setText((position+1)+"");
        holder.jf.setText(model.getScores());
        holder.sl.setText(model.getWin_rate());
    }

    @Override
    public Score2v2Viewhoder onCreateViewHolder(ViewGroup parent, int viewType) {
        Score2v2Viewhoder combinationClassViewhoder = new Score2v2Viewhoder(getRecyclerView(), layoutInflater(getContext(), R.layout.item_score2v2, parent, false), this);
        return combinationClassViewhoder;
    }

    public class Score2v2Viewhoder extends BaseViewHolder {

        public TextView mc;
        public TextView xm;
        public TextView jf;
        public TextView sl;

        public Score2v2Viewhoder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);

            mc = (TextView) rootView.findViewById(R.id.item_score_mc);
            xm = (TextView) rootView.findViewById(R.id.item_score_xm);
            jf = (TextView) rootView.findViewById(R.id.item_score_jf);
            sl = (TextView) rootView.findViewById(R.id.item_score_sl);
        }
    }
}
