package com.newer.kt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.newer.kt.R;
import com.newer.kt.Refactor.ui.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leo on 16/10/11.
 */

public class FooBallAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;
    private HashMap<Integer, FootBallGriViewAdapter> mHas = new HashMap<>();

    public FooBallAdapter(Context context, List<String> mList) {
        this.mList = mList;
        this.mContext = context;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_foot_test, null);
            viewHolder.mGridView = (MyGridView) convertView.findViewById(R.id.gridView);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.textview);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView.setText(mList.get(position));
        List<FootBean> mlist = new ArrayList<>();
        mlist.add(new FootBean(1, "脚底踩球", "30位待完成"));
        mlist.add(new FootBean(2, "外脚背停球变向", "全部完成"));
        mlist.add(new FootBean(2, "脚底踩球", "全部完成"));
        mlist.add(new FootBean(1, "外脚背停球变向", "10位待完成"));
        mlist.add(new FootBean(2, "脚底踩球", "全部完成"));
        mlist.add(new FootBean(1, "外脚背停球变向", "20位待完成"));
        viewHolder.mGridView.setAdapter(new FootBallGriViewAdapter(mlist, mContext));
        mHas.put(position, (FootBallGriViewAdapter) viewHolder.mGridView.getAdapter());
        return convertView;
    }


    public void selectAll() {
        for (int i = 0; i < mList.size(); i++) {
            mHas.get(i).setAll();
        }
    }

    public void clearAll() {
        for (int i = 0; i < mList.size(); i++) {
            mHas.get(i).clearAll();
        }
    }

    class ViewHolder {
        MyGridView mGridView;
        TextView mTextView;
    }
}
