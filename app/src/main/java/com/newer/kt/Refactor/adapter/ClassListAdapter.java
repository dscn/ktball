package com.newer.kt.Refactor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newer.kt.R;

import java.util.List;

/**
 * Created by leo on 16/10/12.
 */

public class ClassListAdapter extends BaseAdapter {
    Context mContext;
    List<String> mList;

    public ClassListAdapter(Context context, List<String> strings) {
        mContext = context;
        mList = strings;
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
        ClassView classView;
        if (convertView == null) {
            classView = new ClassView();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_class_view, null);
            classView.mLine = convertView.findViewById(R.id.view);
            classView.mConetnt = (TextView) convertView.findViewById(R.id.tv_content);
            classView.mCont = (TextView) convertView.findViewById(R.id.tv_cont);
            convertView.setTag(classView);

        } else {
            classView = (ClassView) convertView.getTag();
        }
        if (position == mList.size() - 1)
            classView.mLine.setVisibility(View.INVISIBLE);
        else
            classView.mLine.setVisibility(View.VISIBLE);
        classView.mConetnt.setText(mList.get(position));
        return convertView;
    }

    class ClassView {
        View mLine;
        TextView mConetnt;
        TextView mCont;
    }
}
