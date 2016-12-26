package com.newer.kt.Refactor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newer.kt.Refactor.utils.AdapterItemUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by huangbo on 2016/10/2.
 */

public abstract class CommonRcvAdapter<T> extends RecyclerView.Adapter {

    private List<T> mDataList;
    private Object mItemType;
    private AdapterItemUtil mUtil = new AdapterItemUtil();

    protected CommonRcvAdapter(List<T> data) {
        mDataList = data;
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public List<T> getDataList() {
        return mDataList;
    }


    /**
     * 可以被复写用于单条刷新等
     */
    public void updateData(@NonNull List<T> data) {
        mDataList = data;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * instead by{@link #getItemViewType(Object)}
     * 返回一个int类型的值给onCreateViewHolder 的viewType参数，以供展示不同的item
     */
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        mItemType = getItemViewType(mDataList.get(position));
        return mUtil.getIntType(mItemType);
    }

    public Object getItemViewType(T t) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RcvAdapterItem rcvAdapterItem = new RcvAdapterItem(parent.getContext(), parent, getItemView(mItemType));
        rcvAdapterItem.setIsRecyclable(false);
        return rcvAdapterItem;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RcvAdapterItem) holder).getItem().onUpdateViews(mDataList.get(position), position);
    }

    public abstract @NonNull AdapterItem<T> getItemView(Object type);

    protected void onItemClick(T model, int position) {

    }

    protected void onItemLongClick(int position) {

    }

    protected void onItemLongClick(T model, int position) {

    }

    /**
     * 相当于viewHolder ，因为 extends RecyclerView.ViewHolder
     */
    private class RcvAdapterItem extends RecyclerView.ViewHolder {
        // 调用对应item里面的方法。
        private AdapterItem<T> mItem;

        protected RcvAdapterItem(Context context, ViewGroup parent, AdapterItem<T> item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false));
//            View view = LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false);
//            AutoUtils.autoSize(view);
            mItem = item;
            mItem.onBindViews(itemView, context);
            mItem.onSetViews();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItem.OnRootClickListener(mDataList.get(getPosition()), getPosition());
                    onItemClick(mDataList.get(getPosition()), getPosition());

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClick(getPosition());
                    return false;
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClick(mDataList.get(getPosition()),getPosition());
                    return false;
                }
            });
        }



        protected AdapterItem<T> getItem() {
            return mItem;
        }

    }

    public void AddDatas(List<T> datas) {
        if (datas != null) {
            mDataList.addAll(mDataList.size(), datas);
            notifyItemRangeInserted(mDataList.size() - datas.size(), datas.size());
//            L.d(mDataList.size() + "---" + datas.size());
        }
    }

}

