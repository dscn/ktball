package com.frame.app.base.Adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jiangyu on 15/9/30.
 */
public interface OnRecyclerViewItemLongClickListener {
    void onItemLongClick(ViewGroup parent, View view, int position);
}