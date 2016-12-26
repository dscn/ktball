package com.newer.kt.Refactor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.newer.kt.R;
import com.newer.kt.Refactor.Entitiy.Courses;

import java.util.List;

/**
 * Created by jy on 16/8/10.
 */
public class LessonListAdapter extends BaseRecyclerViewAdapter<Courses, LessonListAdapter.LessonListViewhoder> {

    public LessonListAdapter(RecyclerView recyclerView, List<Courses> combinationses) {
        super(recyclerView, combinationses);
    }

    @Override
    protected void bindView(LessonListViewhoder holder, int position, Courses model) {
        holder.name.setText(model.name);
        holder.time.setText(model.minute + "分钟");
    }

    @Override
    public LessonListViewhoder onCreateViewHolder(ViewGroup parent, int viewType) {
        LessonListViewhoder combinationClassViewhoder = new LessonListViewhoder(getRecyclerView(), layoutInflater(getContext(), R.layout.item_lessonlist, parent, false), this);
        return combinationClassViewhoder;
    }

    public class LessonListViewhoder extends BaseViewHolder {

        public TextView name;
        public TextView time;

        public LessonListViewhoder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);

            name = (TextView) rootView.findViewById(R.id.item_lessonlist_name);
            time = (TextView) rootView.findViewById(R.id.item_lessonlist_time);
        }
    }
}
