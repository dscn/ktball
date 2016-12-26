package com.frame.app.base.Adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jiangyu on 15/9/30.
 */
public interface OnRecyclerViewItemClickListener {
    void onItemClick(ViewGroup parent, View view, int position);
}
