package com.newer.kt.Refactor.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Created by huangbo on 2016/10/2.
 */

public interface AdapterItem<T> {

    /**
     * @return item布局文件的layoutId
     */
    @LayoutRes
    int getLayoutResId();

    /**
     * 初始化views
     */
    void onBindViews(final View root, Context context);

    /**
     * 设置view的参数
     * 在这里直接设置按钮的监听器。
     * 因为这个方法仅仅在item建立时才调用，所以不会重复建立监听器。
     */
    void onSetViews();

    /**
     * 根据数据来设置item的内部views
     *
     * @param model    数据list内部的model
     * @param position 当前adapter调用item的位置
     * 在每次适配器getView的时候就会触发，这里避免做耗时的操作
     */
    void onUpdateViews(T model, int position);
    /**
     * item 点击事件
     */
    void OnRootClickListener(T model,int position);
    /**
     *
     */
}

