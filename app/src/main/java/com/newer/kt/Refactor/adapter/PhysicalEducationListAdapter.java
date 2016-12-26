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
import com.newer.kt.Refactor.Entitiy.GymCourseTeacherFinishedStatistics;
import com.newer.kt.myClass.MyCircleImageView;

import java.util.List;

/**
 * Created by jy on 16/8/25.
 */
public class PhysicalEducationListAdapter extends BaseRecyclerViewAdapter<GymCourseTeacherFinishedStatistics.GymCourseTeacherFinishedStatisticsBean, PhysicalEducationListAdapter.PhysicalEducationListViewHolder> {

    public PhysicalEducationListAdapter(RecyclerView recyclerView, List<GymCourseTeacherFinishedStatistics.GymCourseTeacherFinishedStatisticsBean> gymCourseTeacherFinishedStatisticsBeen) {
        super(recyclerView, gymCourseTeacherFinishedStatisticsBeen);
    }

    @Override
    protected void bindView(PhysicalEducationListViewHolder holder, int position, GymCourseTeacherFinishedStatistics.GymCourseTeacherFinishedStatisticsBean model) {
        holder.name.setText(model.nickname);
        holder.count.setText("已完成"+model.finished_count+"课教学");
        BitmapManager.getInstance().displayUserLogo(holder.header, Constants.Head_HOST+model.avatar);
    }

    @Override
    public PhysicalEducationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PhysicalEducationListViewHolder viewHolder = new PhysicalEducationListViewHolder(getRecyclerView(), layoutInflater(getContext(), R.layout.item_physicaleducationlist, parent, false), this);
        return viewHolder;
    }

    public class PhysicalEducationListViewHolder extends BaseViewHolder {

        public MyCircleImageView header;
        public TextView name;
        public TextView count;

        public PhysicalEducationListViewHolder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);
            header = (MyCircleImageView) rootView.findViewById(R.id.item_physicaleducationlist_header);
            name = (TextView) rootView.findViewById(R.id.item_physicaleducationlist_name);
            count = (TextView) rootView.findViewById(R.id.item_physicaleducationlist_count);

        }
    }
}
