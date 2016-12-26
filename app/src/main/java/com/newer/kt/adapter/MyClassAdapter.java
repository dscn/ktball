package com.newer.kt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.frame.app.utils.DateUtils;
import com.frame.app.utils.LogUtils;
import com.newer.kt.R;
import com.newer.kt.entity.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ARTHUR on 2016/4/22.
 */
public class MyClassAdapter extends BaseAdapter {

    ArrayList<User> data;
    Context context;
    LayoutInflater inflater;

    public MyClassAdapter(ArrayList<User> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public User getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_class_list_item, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView109);
            holder.age = (TextView) convertView.findViewById(R.id.textView111);
            holder.sex = (TextView) convertView.findViewById(R.id.textView112);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User user = getItem(position);
        holder.name.setText(user.nickname);
        //获取当前年份
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date date = new Date(System.currentTimeMillis());
        int year = Integer.parseInt(format.format(date));
        String str = DateUtils.stringTOString(user.birthday, "yyyy-MM-dd", "yyyy");
        int age = year - Integer.parseInt(str);
//        int age = year - Integer.parseInt(user.birthday.substring(0, 4));
        holder.age.setText(age + "岁");
        LogUtils.e(user.birthday + "str = " + str);
        holder.sex.setText(user.gender.equals("MM") ? "女" : "男");
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView age;
        TextView sex;
    }
}
