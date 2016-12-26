package com.frame.app.base.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jiangyu on 15/9/30.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private OnRecyclerViewItemClickListener mItemClickListener;
    private OnRecyclerViewItemLongClickListener mItemLongClickListener;
    protected RecyclerView mRecyclerView;
    protected Context mContext;

    public BaseViewHolder(RecyclerView recyclerView,View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
        super(rootView);
        mContext = recyclerView.getContext();
        this.mItemClickListener = mBaseRecyclerViewAdapter.getClickListener();
        this.mItemLongClickListener = mBaseRecyclerViewAdapter.getLongClickListener();
        rootView.setOnClickListener(this);
        rootView.setOnLongClickListener(this);
    }

    public BaseViewHolder(Context context,View rootView, BaseRecyclerViewAdapter
            mBaseRecyclerViewAdapter) {
        super(rootView);
        mContext = context;
        this.mItemClickListener = mBaseRecyclerViewAdapter.getClickListener();
        this.mItemLongClickListener = mBaseRecyclerViewAdapter.getLongClickListener();
        rootView.setOnClickListener(this);
        rootView.setOnLongClickListener(this);
    }

    /**
     * 点击监听
     */
    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(mRecyclerView,v, getAdapterPosition());
        }
    }

    /**
     * 长按监听
     */
    @Override
    public boolean onLongClick(View v) {
        if (mItemLongClickListener != null) {
            mItemLongClickListener.onItemLongClick(mRecyclerView,v, getAdapterPosition());
        }
        return true;
    }
}