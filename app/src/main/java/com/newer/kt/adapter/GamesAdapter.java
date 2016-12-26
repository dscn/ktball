package com.newer.kt.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.utils.LogUtils;
import com.ktfootball.www.dao.Games;
import com.newer.kt.Business.BitmapManager;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.engine.GlideHelper;

import java.io.File;
import java.util.List;

/**
 * Created by ww on 2016/3/7.
 */
public class GamesAdapter extends BaseAdapter {

    Context context;
    List<Games> games;
    LayoutInflater inflater;
    File p;//根目录

    public GamesAdapter(Context context, List<Games> games) {
        this.context = context;
        this.games = games;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Games getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler holer;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.games_list_item,parent,false);
            holer = new ViewHoler();
            holer.textViewId = (TextView) convertView.findViewById(R.id.textView15);
            holer.imageViewAvatar = (ImageView) convertView.findViewById(R.id.imageView26);
            holer.textViewGameName = (TextView) convertView.findViewById(R.id.textView18);
            holer.textViewTime = (TextView) convertView.findViewById(R.id.textView19);
            convertView.setTag(holer);
        } else {
            holer = (ViewHoler) convertView.getTag();
        }

        p = Environment.getExternalStorageDirectory();
        Games games = getItem(position);
        LogUtils.e(games.toString());
//        String path = p.toString() + "/KT足球/" + String.valueOf(games.getGame_id()) + ".png";
//        GlideHelper.displayCirlcle(context, path, holer.imageViewAvatar);
        BitmapManager.getInstance().displayKTItem( holer.imageViewAvatar, Constants.Head_HOST+games.getAvatar());
//        Glide.with(context).load(new File(path)).transform(new GlideCircleTransform(context)).into(holer.imageViewAvatar);
        holer.textViewId.setText(String.valueOf(position + 1));
        holer.textViewGameName.setText(games.getName());
        String s = games.getTime_start().substring(0,games.getTime_start().lastIndexOf("T"))
                + " - " + games.getTime_end().substring(0,games.getTime_end().lastIndexOf("T"));
        holer.textViewTime.setText(s);

        return convertView;
    }

    class ViewHoler{
        TextView textViewId;
        ImageView imageViewAvatar;
        TextView textViewGameName;
        TextView textViewTime;
    }
}
