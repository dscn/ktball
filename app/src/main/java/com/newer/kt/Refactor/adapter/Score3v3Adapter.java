package com.newer.kt.Refactor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.ktfootball.www.dao.RankingLeagueScores3v3;
import com.newer.kt.R;

import java.util.List;

/**
 * Created by jy on 16/8/10.
 */
public class Score3v3Adapter extends BaseRecyclerViewAdapter<RankingLeagueScores3v3, Score3v3Adapter.Score3v3Viewhoder> {

    public Score3v3Adapter(RecyclerView recyclerView, List<RankingLeagueScores3v3> combinationses) {
        super(recyclerView, combinationses);
    }

    @Override
    protected void bindView(final Score3v3Viewhoder holder, int position, final RankingLeagueScores3v3 model) {
        holder.xm.setText(model.getName());
        holder.mc.setText((position+1)+"");
        holder.jf.setText(model.getScores());
        holder.sl.setText(model.getWin_rate());
    }

    @Override
    public Score3v3Viewhoder onCreateViewHolder(ViewGroup parent, int viewType) {
        Score3v3Viewhoder combinationClassViewhoder = new Score3v3Viewhoder(getRecyclerView(), layoutInflater(getContext(), R.layout.item_score2v2, parent, false), this);
        return combinationClassViewhoder;
    }

    public class Score3v3Viewhoder extends BaseViewHolder {

        public TextView mc;
        public TextView xm;
        public TextView jf;
        public TextView sl;

        public Score3v3Viewhoder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);

            mc = (TextView) rootView.findViewById(R.id.item_score_mc);
            xm = (TextView) rootView.findViewById(R.id.item_score_xm);
            jf = (TextView) rootView.findViewById(R.id.item_score_jf);
            sl = (TextView) rootView.findViewById(R.id.item_score_sl);
        }
    }
}
