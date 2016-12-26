package com.newer.kt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ktfootball.www.dao.SideAandB;
import com.ktfootball.www.dao.VcrPath;
import com.newer.kt.R;
import com.newer.kt.Refactor.db.SideAandBDaoHelper;
import com.newer.kt.Refactor.db.VcrPathDaoHelper;
import com.newer.kt.Refactor.ui.Avtivity.UpHistoryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ww on 2016/3/22.
 */
public class UploadHistoryAdapter extends BaseAdapter {

    ArrayList<String> data;
    Context context;
    LayoutInflater inflater;
    int textCode;

    public UploadHistoryAdapter(ArrayList<String> data, Context context,int textCode) {
        this.data = data;
        this.context = context;
        this.textCode = textCode;
        inflater = LayoutInflater.from(context);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoler holer;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.upload_history_list_item,parent,false);
            holer = new ViewHoler();
            holer.textView = (TextView) convertView.findViewById(R.id.textView72);
            holer.button = (Button) convertView.findViewById(R.id.button3);
            convertView.setTag(holer);
        } else {
            holer = (ViewHoler) convertView.getTag();
        }
        final String s = getItem(position);
        String s1 = s.substring(s.indexOf("球") + 2,s.length()-17);
        holer.textView.setText(s1);
        if(textCode == 1){
            holer.button.setText("删除");
            holer.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<VcrPath> vcrPathList = VcrPathDaoHelper.getInstance().queryByPath(s);
                    for (VcrPath cp : vcrPathList){
                        VcrPathDaoHelper.getInstance().deleteByVcrPath(cp);
                    }
                    List<VcrPath> allList = VcrPathDaoHelper.getInstance().queryBySuccess(true);
                    data.clear();
                    for (VcrPath vp : allList){
                        data.add(vp.getPath());
                    }
                    notifyDataSetChanged();
                    ((UpHistoryActivity)context).initSuccessButton();
                }
            });
        }else{
            holer.button.setText("重试");
            holer.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<VcrPath> vcrPathList = VcrPathDaoHelper.getInstance().queryByPath(s);
                    for (VcrPath cp : vcrPathList) {
                        SideAandBDaoHelper.getInstance().addData(initVcrPath(cp));
                        VcrPathDaoHelper.getInstance().deleteByVcrPath(cp);
                    }
                    List<VcrPath> allList = VcrPathDaoHelper.getInstance().queryBySuccess(false);
                    data.clear();
                    for (VcrPath vp : allList) {
                        data.add(vp.getPath());
                    }
                    notifyDataSetChanged();
                    ((UpHistoryActivity)context).initErrorButton();
                }
            });
        }

        return convertView;
    }

    private SideAandB initVcrPath(VcrPath sb){
        SideAandB vcrPath = new SideAandB();
        vcrPath.setUsers(sb.getUsers());
        vcrPath.setAdd_scores(sb.getAdd_scores());
        vcrPath.setResult(sb.getResult());
        vcrPath.setGoals(sb.getGoals());
        vcrPath.setPannas(sb.getPannas());
        vcrPath.setFouls(sb.getFouls());
        vcrPath.setFlagrant_fouls(sb.getFlagrant_fouls());
        vcrPath.setPanna_ko(sb.getPanna_ko());
        vcrPath.setAbstained(sb.getAbstained());
        vcrPath.setUsers_b(sb.getUsers_b());
        vcrPath.setAdd_scores_b(sb.getAdd_scores_b());
        vcrPath.setResult_b(sb.getResult_b());
        vcrPath.setGoals_b(sb.getGoals_b());
        vcrPath.setPannas_b(sb.getPannas_b());
        vcrPath.setFouls_b(sb.getFouls_b());
        vcrPath.setFlagrant_fouls_b(sb.getFlagrant_fouls_b());
        vcrPath.setPanna_ko_b(sb.getPanna_ko_b());
        vcrPath.setAbstained_b(sb.getAbstained_b());
        vcrPath.setTime(sb.getTime());
        vcrPath.setPath(sb.getPath());
        vcrPath.setGame_type(sb.getGame_type());
        return vcrPath;
    }

    class ViewHoler{
        TextView textView;
        Button button;
    }
}
