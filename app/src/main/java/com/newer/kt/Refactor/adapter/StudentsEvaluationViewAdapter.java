package com.newer.kt.Refactor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.utils.LogUtils;
import com.ktfootball.www.dao.Users;
import com.newer.kt.Business.BitmapManager;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.StudentsEvaluation;
import com.newer.kt.Refactor.db.UsersDaoHelper;
import com.newer.kt.Refactor.ui.Avtivity.MyClassActivity;
import com.newer.kt.entity.GradeList;
import com.newer.kt.entity.User;
import com.newer.kt.myClass.MyCircleImageView;

import java.util.ArrayList;

/**
 * Created by jy on 16/9/22.
 */
public class StudentsEvaluationViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<StudentsEvaluation> classname;

    String[] nianJi = {"", "小班", "中班", "大班", "一年级", "二年级", "三年级", "四年级",
            "五年级", "六年级", "初一", "初二", "初三"};

    public StudentsEvaluationViewAdapter(Context context, ArrayList<StudentsEvaluation> data) {
        this.context = context;
        this.classname = data;
//        classname = new ArrayList<>();
//        for(int x = 0;x<data.size();x++){
//            for(int y = 0;y< data.get(x).classes.size();y++){
//                StudentsEvaluation studentsEvaluation = new StudentsEvaluation();
//                studentsEvaluation.grade = data.get(x).grade;
//                studentsEvaluation.cls = data.get(x).classes.get(y).cls;
//                studentsEvaluation.id = data.get(x).classes.get(y).id;
//                studentsEvaluation.users = data.get(x).classes.get(y).users;
//                studentsEvaluation.name = nianJi[data.get(x).grade] + data.get(x).classes.get(
//                        y).cls + "班";
//                classname.add(studentsEvaluation);
//            }
//        }
    }

    @Override
    public int getGroupCount() {
        return classname.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return classname.get(groupPosition).users.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return classname.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return classname.get(groupPosition).users.get(childPosition);
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
        StudentsEvaluationGroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_studentsevaluationgroup, null);
            groupHolder = new StudentsEvaluationGroupHolder();
            groupHolder.txt = (TextView) convertView.findViewById(R.id.txt);
            groupHolder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (StudentsEvaluationGroupHolder) convertView.getTag();
        }
        groupHolder.txt.setText(classname.get(groupPosition).name+ "");
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
        StudentsEvaluationItemHolder itemHolder = null;
        if (convertView == null) {
//            convertView = View.inflate(context, R.layout.classexpendlist_item, null);
            convertView = View.inflate(context, R.layout.item_studentclassexpendlist_item, null);
            itemHolder = new StudentsEvaluationItemHolder();
            itemHolder.header = (MyCircleImageView) convertView.findViewById(R.id.item_studentclass_header);
            itemHolder.name = (TextView) convertView.findViewById(R.id.item_studentclass_name);
            itemHolder.sex = (TextView) convertView.findViewById(R.id.item_studentclass_sex);
            itemHolder.sg = (TextView) convertView.findViewById(R.id.item_studentclass_sg);
            itemHolder.tz = (TextView) convertView.findViewById(R.id.item_studentclass_tz);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (StudentsEvaluationItemHolder) convertView.getTag();
        }
//        String url = UsersDaoHelper.getInstance().queryByUserId(classname.get(groupPosition).users.get(childPosition).user_id).get(0).getAvatar();
        BitmapManager.getInstance().displayUserLogo(itemHolder.header,"");
        itemHolder.name.setText(classname.get(groupPosition).users.get(childPosition).nickname);
        if(classname.get(groupPosition).users.get(childPosition).gender.equals("MM")){
            itemHolder.sex.setText("女");
        }else{
            itemHolder.sex.setText("男");
        }
        itemHolder.sg.setText(classname.get(groupPosition).users.get(childPosition).height);
        itemHolder.tz.setText(classname.get(groupPosition).users.get(childPosition).weight);
//        itemHolder.banji.setText(data.get(groupPosition).classes.get(
//                childPosition).cls + "班");
//        BitmapManager.getInstance().displayUserLogo(itemHolder.header, Constants.HOST+classname.get(groupPosition).users.get(childPosition).);
//        ArrayList<User> users = classname.get(groupPosition).users;
//        itemHolder.renshu.setText(users.size() + "学生");
//        itemHolder.rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ArrayList<User> user = data.get(groupPosition).classes.get(childPosition).users;
//                Intent intent = new Intent(context, MyClassActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("users", user);
//                bundle.putSerializable("grade", data.get(groupPosition).grade);
//                bundle.putSerializable("cls", data.get(groupPosition).classes.get(childPosition).cls);
//                bundle.putSerializable("classname", data.get(groupPosition).classes.get(
//                        childPosition).cls + "班");
//                intent.putExtra("bundle", bundle);
//                context.startActivity(intent);
//            }
//        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

class StudentsEvaluationGroupHolder {
    public TextView txt;
    public ImageView img;
}

class StudentsEvaluationItemHolder {
    public MyCircleImageView header;
    public TextView name;
    public TextView sex;
    public TextView sg;
    public TextView tz;
}
