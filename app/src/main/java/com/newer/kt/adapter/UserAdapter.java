package com.newer.kt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ktfootball.www.dao.Users;
import com.newer.kt.Business.BitmapManager;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.R;

import java.util.List;

/**
 * Created by ww on 2016/3/7.
 */
public class UserAdapter extends BaseAdapter {

    Context context;
    List<Users> students;
    LayoutInflater inflater;

    public UserAdapter(Context context, List<Users> students) {
        this.context = context;
        this.students = students;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Users getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_list_item, parent, false);
            holder = new ViewHolder();
            holder.imageViewAvatar = (ImageView) convertView.findViewById(R.id.imageView29);
            holder.textViewName = (TextView) convertView.findViewById(R.id.textView9);
            holder.textViewAge = (TextView) convertView.findViewById(R.id.textView13);
            holder.imageViewGender = (TextView) convertView.findViewById(R.id.imageView32);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Users student = getItem(position);
        String path = Constants.Head_HOST + student.getAvatar();
        BitmapManager.getInstance().displayUserLogo(holder.imageViewAvatar,
                path);
        holder.textViewName.setText(student.getNickname());
        holder.textViewAge.setText("0".equals(student.getAge()) ? "" : student.getAge() + "岁");

        String gender = student.getGender();

        if (gender != null)
            holder.imageViewGender.setText(gender.equals("GG") ? "男" : "女");
        return convertView;
    }

    class ViewHolder {
        ImageView imageViewAvatar;
        TextView textViewName;
        TextView textViewAge;
        TextView imageViewGender;
    }
}
