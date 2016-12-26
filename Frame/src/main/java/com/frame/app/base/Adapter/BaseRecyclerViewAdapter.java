package com.frame.app.base.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by jiangyu on 15/9/30.
 */
public abstract class BaseRecyclerViewAdapter<M,VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    private OnRecyclerViewItemClickListener mListener;
    private OnRecyclerViewItemLongClickListener mLongClickListener;
    protected Context mContext;
    protected List<M> mDatas;
    protected RecyclerView mRecyclerView;

    public BaseRecyclerViewAdapter(RecyclerView recyclerView,List<M> mList) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mDatas = mList;
    }



    @Override
    public void onBindViewHolder(VH holder, int position) {
        if(position < mDatas.size()){
            bindView(holder, position, getItem(position));
        }
    }

    /**
     * 填充item数据
     *
     * @param VH
     * @param position
     * @param model
     */
    protected abstract void bindView(VH holder, int position, M model);

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mListener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener){
        this.mLongClickListener = listener;
    }

    public OnRecyclerViewItemClickListener getClickListener(){
        return mListener;
    }

    public OnRecyclerViewItemLongClickListener getLongClickListener(){
        return mLongClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public M getItem(int position) {
        return mDatas.get(position);
    }

    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public List<M> getListData(){
        return mDatas;
    }

    public Context getContext(){
        return mContext;
    }

    public View layoutInflater(Context context,int resd,ViewGroup root,boolean b){
        return LayoutInflater.from(context).inflate(resd,root,b);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<M> getDatas() {
        return mDatas;
    }

    /**
     * 在集合头部添加新的数据集合（下拉从服务器获取最新的数据集合，例如新浪微博加载最新的几条微博数据）
     *
     * @param datas
     */
    public void addNewDatas(List<M> datas) {
        if (datas != null) {
            mDatas.addAll(0, datas);
            notifyItemRangeInserted(0, datas.size());
        }
    }

    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     *
     * @param datas
     */
    public void addMoreDatas(List<M> datas) {
        if (datas != null) {
            mDatas.addAll(mDatas.size(), datas);
            notifyItemRangeInserted(mDatas.size(), datas.size());
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param datas
     */
    public void setDatas(List<M> datas) {
        if (datas != null) {
            mDatas = datas;
        } else {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public void removeItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除指定数据条目
     *
     * @param model
     */
    public void removeItem(M model) {
        removeItem(mDatas.indexOf(model));
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param model
     */
    public void addItem(int position, M model) {
        mDatas.add(position, model);
        notifyItemInserted(position);
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model
     */
    public void addFirstItem(M model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model
     */
    public void addLastItem(M model) {
        addItem(mDatas.size(), model);
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param newModel
     */
    public void setItem(int location, M newModel) {
        mDatas.set(location, newModel);
        notifyItemChanged(location);
    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel
     * @param newModel
     */
    public void setItem(M oldModel, M newModel) {
        setItem(mDatas.indexOf(oldModel), newModel);
    }

    /**
     * 交换两个数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        Collections.swap(mDatas, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }
}
