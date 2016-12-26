package com.newer.kt.Refactor.ui.Fragment.Manager;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.newer.kt.R;
import com.newer.kt.Refactor.ui.MyGridView;
import com.newer.kt.adapter.FootBallGriViewAdapter;
import com.newer.kt.adapter.FootBallManagerAdapter;
import com.newer.kt.adapter.FootBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 16/10/12.
 */

public class FootFramgnet extends BaseFragment {
    private ListView mListView;
    private List<String> mlist = new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.framgnet_manager_foot);
        mListView = getViewById(R.id.listView);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mlist.add("dadasdas");
        mlist.add("dadasdas");
        mlist.add("dadasdas");
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mlist.size();
            }

            @Override
            public Object getItem(int position) {
                return mlist.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder1 viewHolder1;
                if (convertView == null) {
                    viewHolder1 = new ViewHolder1();
                    convertView = LayoutInflater.from(getThis()).inflate(R.layout.item_foot_list, null);
                    viewHolder1.mLine = convertView.findViewById(R.id.view);
                    viewHolder1.mTitle = (TextView) convertView.findViewById(R.id.textview);
                    viewHolder1.myGridView = (MyGridView) convertView.findViewById(R.id.gridView);
                    convertView.setTag(viewHolder1);
                } else {
                    viewHolder1 = (ViewHolder1) convertView.getTag();
                }
                if (position == mlist.size() - 1) {
                    viewHolder1.mLine.setVisibility(View.GONE);
                } else {
                    viewHolder1.mLine.setVisibility(View.VISIBLE);
                }
                viewHolder1.mTitle.setText(mlist.get(position));
                List<FootBean> mlist = new ArrayList<>();
                mlist.add(new FootBean(1, "脚底踩球", "30位待完成"));
                mlist.add(new FootBean(2, "外脚背停球变向", "全部完成"));
                mlist.add(new FootBean(2, "脚底踩球", "全部完成"));
                mlist.add(new FootBean(1, "外脚背停球变向", "10位待完成"));
                mlist.add(new FootBean(2, "脚底踩球", "全部完成"));
                mlist.add(new FootBean(1, "外脚背停球变向", "20位待完成"));
                viewHolder1.myGridView.setAdapter(new FootBallManagerAdapter(mlist, getThis()));
                return convertView;
            }
        });


    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }


    class ViewHolder1 {
        MyGridView myGridView;
        TextView mTitle;
        View mLine;
    }
}
