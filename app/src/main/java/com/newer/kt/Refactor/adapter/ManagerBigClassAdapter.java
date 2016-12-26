package com.newer.kt.Refactor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;

import java.util.List;

/**
 * Created by leo on 16/10/12.
 */

public class ManagerBigClassAdapter extends RecyclerView.Adapter<ManagerBigClassAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mList;

    public ManagerBigClassAdapter(Context aThis, List<String> list) {
        mContext = aThis;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_manager_big_class, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            holder.mLinear_bg.setBackgroundResource(R.drawable.bigclass_shape_first_nomal);
        } else if (position + 1 == mList.size()) {
            holder.mLinear_bg.setBackgroundResource(R.drawable.bigclass_shape_last_nomal);
        } else {
            holder.mLinear_bg.setBackgroundResource(R.drawable.bigclass_shape_nomal);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mContent, mTime, mStatus;
        LinearLayout mLinear_bg, mLinear_time;
        Button mBtn;
        View mLine;

        public ViewHolder(View root) {
            super(root);
            mLinear_bg = (LinearLayout) root.findViewById(R.id.linear_content);
            mLinear_time = (LinearLayout) root.findViewById(R.id.linear_time);
            mBtn = (Button) root.findViewById(R.id.btn_look);
            mLine = root.findViewById(R.id.view_line);
            mTitle = (TextView) root.findViewById(R.id.tv_title);
            mStatus = (TextView) root.findViewById(R.id.tv_status);
            mTime = (TextView) root.findViewById(R.id.tv_time);
            mContent = (TextView) root.findViewById(R.id.tv_content);
        }
    }
}
