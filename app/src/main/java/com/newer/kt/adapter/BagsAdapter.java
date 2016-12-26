package com.newer.kt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ktfootball.www.dao.Bags;
import com.newer.kt.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ww on 2016/3/7.
 */
public class BagsAdapter extends BaseAdapter {

    Context context;
    List<Bags> bagses;
    LayoutInflater inflater;

    public BagsAdapter(Context context, List<Bags> bagses) {
        this.context = context;
        this.bagses = bagses;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bagses.size();
    }

    @Override
    public Bags getItem(int position) {
        return bagses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.bags_list_item,parent,false);
            holder = new ViewHolder();
            holder.textViewName = (TextView) convertView.findViewById(R.id.textView14);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Bags bags = getItem(position);
        holder.textViewName.setText(bags.getName());
        return convertView;
    }

    class ViewHolder{
        TextView textViewName;
    }
}
