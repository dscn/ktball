package com.newer.kt.Refactor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.view.MyListView;
import com.newer.kt.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 16/10/12.
 */

public class ClssFragmentAdapter extends RecyclerView.Adapter<ClssFragmentAdapter.ViewHoder> {
    private Context mContext;
    private List<String> mList;

    public ClssFragmentAdapter(Context aThis, List<String> mList) {
        mContext = aThis;
        this.mList = mList;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(mContext).inflate(R.layout.item_class_recycler, null));
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
        holder.mTtile.setText(mList.get(position));
        List<String> mlStrings = new ArrayList<>();
        mlStrings.add("dsadas");
        mlStrings.add("dsadas");
        mlStrings.add("dsadas");
        holder.mListView.setAdapter(new ClassListAdapter(mContext,mlStrings));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        private TextView mTtile;
        private MyListView mListView;
        private TextView mData;
        private TextView mTime;

        public ViewHoder(View itemView) {
            super(itemView);
            mListView = (MyListView) itemView.findViewById(R.id.listView);
            mTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTtile = (TextView) itemView.findViewById(R.id.tv_title);
            mData = (TextView) itemView.findViewById(R.id.tv_data);
        }
    }
}
