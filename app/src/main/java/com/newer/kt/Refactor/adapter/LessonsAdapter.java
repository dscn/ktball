package com.newer.kt.Refactor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.newer.kt.R;

import java.util.List;

/**
 * Created by jy on 16/6/22.
 */
public class LessonsAdapter extends BaseRecyclerViewAdapter<Integer,LessonsAdapter.LessonsViewHolder> {

    public LessonsAdapter(RecyclerView recyclerView, List<Integer> integers) {
        super(recyclerView, integers);
    }

    @Override
    protected void bindView(LessonsViewHolder holder, int position, Integer model) {
        holder.img.setBackgroundResource(model);
    }

    @Override
    public LessonsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LessonsViewHolder viewHolder = new LessonsViewHolder(getContext(), LayoutInflater.from(getContext()).inflate(R.layout.item_imageview,parent,false),this);
        return viewHolder;
    }

    public class LessonsViewHolder extends BaseViewHolder {

        public ImageView img;

        public LessonsViewHolder(Context context, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(context, rootView, mBaseRecyclerViewAdapter);
            img = (ImageView) rootView.findViewById(R.id.item_imageview_img);
        }
    }
}
