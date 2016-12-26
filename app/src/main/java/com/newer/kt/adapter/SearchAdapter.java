package com.newer.kt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newer.kt.R;
import com.newer.kt.entity.PoiResult;

import java.util.ArrayList;

/**
 * Created by ww on 2016/3/31.
 */
public class SearchAdapter extends BaseAdapter {

    ArrayList<PoiResult> data;
    Context context;
    LayoutInflater inflater;

    public SearchAdapter(ArrayList<PoiResult> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public PoiResult getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler holer;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.search_list_item,parent,false);
            holer = new ViewHoler();
            holer.name = (TextView) convertView.findViewById(R.id.textView93);
            holer.address = (TextView) convertView.findViewById(R.id.textView103);
            convertView.setTag(holer);
        } else {
            holer = (ViewHoler) convertView.getTag();
        }
        PoiResult result = getItem(position);
        holer.name.setText(result.name);
        holer.address.setText(result.address);
        return convertView;
    }

    class ViewHoler{
        TextView name;
        TextView address;
    }
}
