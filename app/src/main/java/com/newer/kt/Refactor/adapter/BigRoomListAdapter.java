package com.newer.kt.Refactor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.newer.kt.Business.BitmapManager;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.BigClassroomRecords;
import com.newer.kt.Refactor.Entitiy.GymCourseTeacherFinishedStatistics;
import com.newer.kt.myClass.MyCircleImageView;

import java.util.List;

/**
 * Created by jy on 16/8/25.
 */
public class BigRoomListAdapter extends BaseRecyclerViewAdapter<BigClassroomRecords, BigRoomListAdapter.BigRoomListViewHolder> {

    public BigRoomListAdapter(RecyclerView recyclerView, List<BigClassroomRecords> gymCourseTeacherFinishedStatisticsBeen) {
        super(recyclerView, gymCourseTeacherFinishedStatisticsBeen);
    }

    @Override
    protected void bindView(BigRoomListViewHolder holder, int position, BigClassroomRecords model) {
        holder.name.setText(model.classroom);
        if(model.finished_time.length()>=10){
            holder.time.setText("已完时间："+model.finished_time.substring(0,10));
        }
        StringBuffer sb = new StringBuffer();
        for(int x = 0;x < model.classes.size();x++){
            sb.append(model.classes.get(x).grade+"("+model.classes.get(x).cls + ") ");
        }
        holder.clazz.setText(sb.toString());
    }

    @Override
    public BigRoomListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BigRoomListViewHolder viewHolder = new BigRoomListViewHolder(getRecyclerView(), layoutInflater(getContext(), R.layout.item_bigroomlist, parent, false), this);
        return viewHolder;
    }

    public class BigRoomListViewHolder extends BaseViewHolder {

        public TextView time;
        public TextView name;
        public TextView clazz;

        public BigRoomListViewHolder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);
            time = (TextView) rootView.findViewById(R.id.item_bigroomlist_time);
            name = (TextView) rootView.findViewById(R.id.item_bigroomlist_name);
            clazz = (TextView) rootView.findViewById(R.id.item_bigroomlist_class);

        }
    }
}
