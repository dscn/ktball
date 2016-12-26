package com.newer.kt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.newer.kt.R;
import com.newer.kt.entity.GradeList;

import java.util.List;

/**
 * Created by jy on 16/8/23.
 */
public class ClassListAdapter extends BaseRecyclerViewAdapter<GradeList,ClassListAdapter.ClassListViewholder> {

    String[] nianJi = {"","小班","中班","大班","一年级","二年级","三年级","四年级",
            "五年级","六年级","初一","初二","初三"};

    public ClassListAdapter(RecyclerView recyclerView, List<GradeList> strings) {
        super(recyclerView, strings);
    }

    @Override
    protected void bindView(ClassListViewholder holder, int position, GradeList model) {
        holder.tv.setText(nianJi[model.grade]);
    }

    @Override
    public ClassListViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        ClassListViewholder viewholder = new ClassListViewholder(getRecyclerView(),layoutInflater(getContext(), R.layout.item_classlist,parent,false),this);
        return viewholder;
    }

    public class ClassListViewholder extends BaseViewHolder{

        public TextView tv;

        public ClassListViewholder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);

            tv = (TextView) rootView.findViewById(R.id.item_classlist_name);
        }
    }
}
