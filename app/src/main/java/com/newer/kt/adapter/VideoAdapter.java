package com.newer.kt.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.frame.app.view.NumberProgressBar.NumberProgressBar;
import com.newer.kt.R;

import java.util.ArrayList;

/**
 * Created by ww on 2016/3/21.
 */
public class VideoAdapter  extends BaseAdapter{

    ArrayList<String> data;
    Context context;
    LayoutInflater inflater;


    public VideoAdapter(ArrayList<String> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void deleteItem(String path){
        for (int i=0;i<data.size();i++){
            if (data.get(i).equals(path)){
                data.remove(i);
                notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.video_list_item,parent,false);
            viewHoler = new ViewHoler();
            viewHoler.textView = (TextView) convertView.findViewById(R.id.textView71);
            viewHoler.progressBar = (NumberProgressBar) convertView.findViewById(R.id.progressBar20);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        String s = getItem(position);
        String s1 = s.substring(s.indexOf("球") + 2,s.length()-17);
        viewHoler.textView.setText(s1);
        return convertView;
    }

    class ViewHoler{
        TextView textView;
        NumberProgressBar progressBar;
    }
}
