package com.newer.kt.Refactor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newer.kt.R;
import com.newer.kt.Refactor.ui.MyGridView;
import com.newer.kt.adapter.FootBallTestAdapter;

import java.util.List;

/**
 * Created by huangbo on 2016/10/12.
 */

public class FootBallDetailAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public FootBallDetailAdapter(Context aThis, List<String> tabList) {
        mContext = aThis;
        mList = tabList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FootBallDetailViewHoder mFootBallDetailViewHoder;
        if (convertView == null) {
            mFootBallDetailViewHoder = new FootBallDetailViewHoder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_football_detail,null);
            mFootBallDetailViewHoder.mTitle = (TextView) convertView.findViewById(R.id.title);
            mFootBallDetailViewHoder.myGridView = (MyGridView) convertView.findViewById(R.id.gridView);
            convertView.setTag(mFootBallDetailViewHoder);
        } else {
            mFootBallDetailViewHoder = (FootBallDetailViewHoder) convertView.getTag();
        }
        mFootBallDetailViewHoder.mTitle.setText(mList.get(position));
        mFootBallDetailViewHoder.myGridView.setAdapter(new BaseAdapter() {
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

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                FootBallDetailHolder mFootBallDetailHolder;
                if (convertView == null){
                    mFootBallDetailHolder = new FootBallDetailHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gird,null);
                    mFootBallDetailHolder.mBg = (LinearLayout) convertView.findViewById(R.id.linear_bg);
                    mFootBallDetailHolder.mContent = (TextView) convertView.findViewById(R.id.tv_content);
                    mFootBallDetailHolder.mTitle = (TextView) convertView.findViewById(R.id.tv_title);
                    convertView.setTag(mFootBallDetailHolder);

                }else{
                    mFootBallDetailHolder = (FootBallDetailHolder) convertView.getTag();
                }
//                mFootBallDetailHolder.mBg.setBackgroundResource(R.mipmap.foot_bg_nomal);
//                mFootBallDetailHolder.mContent.setTextColor(0x4cffffff);
//                mFootBallDetailHolder.mTitle.setTextColor(0x4cffffff);
//
//                mFootBallDetailHolder.mBg.setBackgroundResource(R.mipmap.foot_bg);
//                mFootBallDetailHolder.mContent.setTextColor(mContext.getResources().getColor(R.color.gold));
//                mFootBallDetailHolder.mTitle.setTextColor(mContext.getResources().getColor(R.color.gold));

                return convertView;
            }
        });
        return convertView;
    }

    class FootBallDetailHolder{
        TextView mTitle;
        TextView mContent;
        LinearLayout mBg;
    }

    class FootBallDetailViewHoder {
        TextView mTitle;
        MyGridView myGridView;
    }
}
