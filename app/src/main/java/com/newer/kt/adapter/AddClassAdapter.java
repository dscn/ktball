package com.newer.kt.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.app.utils.LogUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.ui.Avtivity.MyClassActivity;
import com.newer.kt.entity.ClassData;
import com.newer.kt.entity.GradeList;
import com.newer.kt.entity.User;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ww on 2016/4/22.
 */
public class AddClassAdapter extends BaseAdapter {

    Context context;
    ArrayList<GradeList> data = new ArrayList<>();
    LayoutInflater inflater;
    ClassAdapter classAdapter;

//    1-3小班 中班 大班   4-9 一年级-六年级   10-12  初一-初三
    String[] nianJi = {"","小班","中班","大班","一年级","二年级","三年级","四年级",
                        "五年级","六年级","初一","初二","初三"};


    public AddClassAdapter(Context context, ArrayList<GradeList> data) {
        Collections.sort(data);
//        Collections.sort(listA);
        this.context = context;
        if (data == null) {
            this.data = null;
        } else {
            this.data = data;
        }
        inflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<GradeList> data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grade_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String nianjiText = nianJi[data.get(position).grade];
        ArrayList<ClassData> classes = data.get(position).classes;
        int userCount = 0;
        for(ClassData classData : classes){
            userCount += classData.users.size();
        }
        LogUtils.e(nianjiText+"（"+ userCount +"人）");
        viewHolder.textView.setText(nianjiText+"（"+ userCount +"人）");
        classAdapter = new ClassAdapter(context, data.get(position).classes);
        viewHolder.listView.setAdapter(classAdapter);
        setListViewAdapter(context,viewHolder.listView);
        viewHolder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positions, long id) {
                ArrayList<User> user = data.get(position).classes.get(positions).users;
                Intent intent = new Intent(context, MyClassActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("users",user);
                bundle.putSerializable("grade", data.get(position).grade);
                bundle.putSerializable("cls",data.get(position).classes.get(positions).cls);
                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
            }
        });
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.listView.getVisibility()==View.GONE){
                    viewHolder.listView.setVisibility(View.VISIBLE);
                    viewHolder.xiala.setRotation(180);
//                    Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.rotating_anim);
//                    LinearInterpolator lin = new LinearInterpolator();
//                    operatingAnim.setInterpolator(lin);
//                    viewHolder.xiala.clearAnimation();
//                    viewHolder.xiala.startAnimation(operatingAnim);
                }else {
                    viewHolder.listView.setVisibility(View.GONE);
                    viewHolder.xiala.setRotation(0);
//                    Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.rotating_anim_2);
//                    LinearInterpolator lin = new LinearInterpolator();
//                    operatingAnim.setInterpolator(lin);
//                    viewHolder.xiala.clearAnimation();
//                    viewHolder.xiala.startAnimation(operatingAnim);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView textView;
        ImageView xiala;
        ListView listView;

        public ViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.textView_text);
            xiala = (ImageView) convertView.findViewById(R.id.imageView90);
            listView = (ListView) convertView.findViewById(R.id.listView5);
        }
    }

    public void setListViewAdapter(Context context, ListView listView) {
        ClassAdapter listAdapter = (ClassAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }
}
