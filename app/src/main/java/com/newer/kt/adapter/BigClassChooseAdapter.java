package com.newer.kt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.utils.LogUtils;
import com.newer.kt.R;
import com.newer.kt.entity.ClassData;
import com.newer.kt.entity.GradeList;
import com.newer.kt.entity.User;

import java.util.ArrayList;

/**
 * Created by jy on 16/7/12.
 */
public class BigClassChooseAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<GradeList> data;
    private CheckBox mOldCheckBox;
    public ClassData mClassData;

    String[] nianJi = {"", "小班", "中班", "大班", "一年级", "二年级", "三年级", "四年级",
            "五年级", "六年级", "初一", "初二", "初三"};

    public BigClassChooseAdapter(Context context, ArrayList<GradeList> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
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
        ClassGroupHolder groupHolder = null;
        convertView = View.inflate(context, R.layout.expendlist_group, null);
        groupHolder = new ClassGroupHolder();
        groupHolder.txt = (TextView) convertView.findViewById(R.id.txt);
        groupHolder.img = (ImageView) convertView.findViewById(R.id.img);
        groupHolder.txt.setText(nianJi[data.get(groupPosition).grade] + "");
        if (isExpanded) {
            groupHolder.img.setImageResource(R.mipmap.nomal_up);
        } else {
            groupHolder.img.setImageResource(R.mipmap.nomal_down);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.expendlist_item, null);
        ClassItemHolder itemHolder = new ClassItemHolder();
        itemHolder.dizhi = (CheckBox) convertView.findViewById(R.id.layout_pay_dizhi);
//        itemHolder.banji = (TextView) convertView.findViewById(R.id.layout_pay_banji);
        itemHolder.renshu = (TextView) convertView.findViewById(R.id.layout_pay_renshu);
        final ClassData classData = data.get(groupPosition).classes.get(
                childPosition);
//        itemHolder.banji.setText(classData.cls + "班");
        ArrayList<User> users = classData.users;
        itemHolder.renshu.setText(users.size() + "学生");
        LogUtils.e(classData.isChecked + "");
        if (classData.isChecked) {
            itemHolder.dizhi.setChecked(true);
        } else {
            itemHolder.dizhi.setChecked(false);
        }
        final ClassItemHolder finalItemHolder = itemHolder;
        itemHolder.dizhi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mOldCheckBox!=null){
                    mOldCheckBox.setChecked(false);
                }
                mOldCheckBox = finalItemHolder.dizhi;
                classData.isChecked = isChecked;
                mClassData = data.get(groupPosition).classes.get(childPosition);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

class ClassGroupHolder {
    public TextView txt;
    public ImageView img;
}

class ClassItemHolder {
    public CheckBox dizhi;
    public TextView banji;
    public TextView renshu;
}
