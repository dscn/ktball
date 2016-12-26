package com.newer.kt.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.newer.kt.R;
import com.newer.kt.entity.ClassData;
import com.newer.kt.entity.GradeList;

import java.util.ArrayList;

/**
 * Created by ww on 2016/4/22.
 */
public class ClassAdapter extends BaseAdapter {
    Context context;
    ArrayList<ClassData> data = new ArrayList<>();
    LayoutInflater inflater;

    public ClassAdapter(Context context, ArrayList<ClassData> data) {
        this.context = context;
        if (data == null) {
            this.data = null;
        } else {
            this.data = data;
        }
        inflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<ClassData> data) {
        if (data == null) {
            this.data = null;
        } else {
            this.data = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.class_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(String.valueOf(data.get(position).cls+"班"));
        int userCount = data.get(position).users.size();
//        if(userCount == 0){
//            viewHolder.userCount.setVisibility(View.GONE);
//        }else{
            viewHolder.userCount.setText(userCount+" 人");
//        }
        return convertView;
    }

    class ViewHolder {
        TextView textView;
        TextView userCount;

        public ViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.textView108);
            userCount = (TextView) convertView.findViewById(R.id.class_list_item_usercount);
//            listView = (ListView) convertView.findViewById(R.id.listView5);
        }
    }
}
