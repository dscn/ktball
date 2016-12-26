package com.newer.kt.Refactor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.utils.LogUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.ui.Avtivity.Manager.ClassManagerActivity;
import com.newer.kt.Refactor.ui.Avtivity.MyClassActivity;
import com.newer.kt.entity.GradeList;
import com.newer.kt.entity.User;

import java.util.ArrayList;

/**
 * Created by jy on 16/7/12.
 */
public class ClassMyExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<GradeList> data;

    String[] nianJi = {"", "小班", "中班", "大班", "一年级", "二年级", "三年级", "四年级",
            "五年级", "六年级", "初一", "初二", "初三"};

    public ClassMyExpandableListViewAdapter(Context context,  ArrayList<GradeList> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        LogUtils.e(data.get(groupPosition).classes.size() + "");
        System.out.print(data.get(groupPosition).classes.size() + "");
        return data.get(groupPosition).classes.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).classes.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.expendlist_group, null);
            groupHolder = new GroupHolder();
            groupHolder.txt = (TextView) convertView.findViewById(R.id.txt);
            groupHolder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.txt.setText(nianJi[data.get(groupPosition).grade] + "");
        if (isExpanded) {
            groupHolder.img.setImageResource(R.drawable.icon_start_class_up);
        } else {
            groupHolder.img.setImageResource(R.drawable.icon_start_class_down);
        }
//        if (classesData.classes.get(groupPosition).isChecked) {
//            groupHolder.txt.setChecked(true);
//        } else {
//            groupHolder.txt.setChecked(false);
//        }
//        groupHolder.txt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                classesData.classes.get(groupPosition).isChecked = isChecked;
//                List<Clazz2> list = item_list.get(groupPosition);
//                for (int x = 0; x < list.size(); x++) {
//                    list.get(x).isChecked = isChecked;
//                    notifyDataSetChanged();
//                }
//            }
//        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.classexpendlist_item, null);
            itemHolder = new ItemHolder();
            itemHolder.go_right = (CheckBox) convertView.findViewById(R.id.layout_pay_dizhi);
            itemHolder.banji = (TextView) convertView.findViewById(R.id.layout_pay_banji);
            itemHolder.renshu = (TextView) convertView.findViewById(R.id.layout_pay_renshu);
            itemHolder.rl = (RelativeLayout) convertView.findViewById(R.id.layout_pay_rl);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        itemHolder.banji.setText(data.get(groupPosition).classes.get(
                childPosition).cls + "班");
        ArrayList<User> users = data.get(groupPosition).classes.get(childPosition).users;
        itemHolder.renshu.setText(users.size() +"学生");
        itemHolder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<User> user = data.get(groupPosition).classes.get(childPosition).users;
//                Intent intent = new Intent(context, MyClassActivity.class);
                Intent intent = new Intent(context, ClassManagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("users",user);
                bundle.putSerializable("grade", data.get(groupPosition).grade);
                bundle.putSerializable("cls",data.get(groupPosition).classes.get(childPosition).cls);
                bundle.putSerializable("classname",data.get(groupPosition).classes.get(
                        childPosition).cls + "班");
                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

class GroupHolder {
    public TextView txt;
    public ImageView img;
}

class ItemHolder {
    public CheckBox go_right;
    public TextView banji;
    public TextView renshu;
    public RelativeLayout rl;
}
