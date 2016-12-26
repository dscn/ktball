package com.newer.kt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newer.kt.R;
import com.newer.kt.Refactor.ui.Avtivity.Manager.FootBallDetailActivity;
import com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.FootBallTestActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leo on 16/10/12.
 */

public class FootBallManagerAdapter extends BaseAdapter {
    private List<FootBean> mList = new ArrayList<>();
    private Context mContext;
    private HashMap<Integer, Boolean> mHasmap = new HashMap<>();

    public FootBallManagerAdapter(List<FootBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        for (int i = 0; i < mList.size(); i++) {
            mHasmap.put(i, false);
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 全选
     */
    public void setAll() {
        for (int i = 0; i < mList.size(); i++) {
            mHasmap.put(i, true);
            if (mList.get(i).getType() == 1) {
                ((FootBallTestActivity) mContext).mSelectedList.add(mList.get(i));
            }
        }
        notifyDataSetChanged();
    }


    /**
     * 全不选
     */
    public void clearAll() {
        for (int i = 0; i < mList.size(); i++) {
            mHasmap.put(i, false);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final FootView footView;
        if (convertView == null) {
            footView = new FootView();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_foot_gridview, null);
            footView.mLinear = (LinearLayout) convertView.findViewById(R.id.linear);
            footView.mContent = (TextView) convertView.findViewById(R.id.tv_content);
            footView.mTitle = (TextView) convertView.findViewById(R.id.tv_title1);
            convertView.setTag(footView);
        } else {
            footView = (FootView) convertView.getTag();
        }
        footView.mTitle.setText(mList.get(position).getTitle());
        if (mList.get(position).getType() == 1) {
            footView.mTitle.setTextColor(mContext.getResources().getColor(R.color.gold));
            footView.mContent.setTextColor(mContext.getResources().getColor(R.color.gold));
            footView.mLinear.setBackgroundResource(R.mipmap.foot_no_select);
            footView.mContent.setText(mList.get(position).getWanc());
        } else {
            footView.mTitle.setTextColor(0XFF333333);
            footView.mContent.setTextColor(0XFF333333);
            footView.mContent.setText("查看测评");
            footView.mLinear.setBackgroundResource(R.mipmap.foot_select);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        return convertView;
    }

    class FootView {
        LinearLayout mLinear;
        TextView mTitle;
        TextView mContent;
    }
}
