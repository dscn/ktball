package com.newer.kt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ktfootball.www.dao.SideAandB;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecord;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecordBoolean;
import com.ktfootball.www.dao.UploadGymCourseRecord;
import com.ktfootball.www.dao.VcrPath;
import com.newer.kt.R;
import com.newer.kt.Refactor.db.SideAandBDaoHelper;
import com.newer.kt.Refactor.db.UploadBigClassroomCourseRecordBooleanDaoHelper;
import com.newer.kt.Refactor.db.UploadBigClassroomCourseRecordDaoHelper;
import com.newer.kt.Refactor.db.VcrPathDaoHelper;
import com.newer.kt.Refactor.ui.Avtivity.UpHistoryActivity;
import com.newer.kt.Refactor.ui.Avtivity.UpLessonHistoryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ww on 2016/3/22.
 */
public class UploadLessonHistoryAdapter extends BaseAdapter {

    ArrayList<String> data;
    Context context;
    LayoutInflater inflater;
    int textCode;

    public UploadLessonHistoryAdapter(ArrayList<String> data, Context context, int textCode) {
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
        String s1 = s.substring(s.lastIndexOf("/")+1);
        holer.textView.setText(s1);
        if(textCode == 1){
            holer.button.setText("删除");
            holer.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<UploadBigClassroomCourseRecordBoolean> vcrPathList = UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().queryByPath(s);
                    for (UploadBigClassroomCourseRecordBoolean cp : vcrPathList){
                        UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().deleteByVcrPath(cp);
                    }
                    List<UploadBigClassroomCourseRecordBoolean> allList = UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().queryBySuccess(true);
                    data.clear();
                    for (UploadBigClassroomCourseRecordBoolean vp : allList){
                        data.add(vp.getPath());
                    }
                    notifyDataSetChanged();
                    ((UpLessonHistoryActivity)context).initSuccessButton();
                }
            });
        }else{
            holer.button.setText("重试");
            holer.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<UploadBigClassroomCourseRecordBoolean> vcrPathList = UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().queryByPath(s);
                    for (UploadBigClassroomCourseRecordBoolean cp : vcrPathList) {
                        UploadBigClassroomCourseRecordDaoHelper.getInstance().addData(initVcrPath(cp));
                        UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().deleteByVcrPath(cp);
                    }
                    List<UploadBigClassroomCourseRecordBoolean> allList = UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().queryBySuccess(false);
                    data.clear();
                    for (UploadBigClassroomCourseRecordBoolean vp : allList) {
                        data.add(vp.getPath());
                    }
                    notifyDataSetChanged();
                    ((UpLessonHistoryActivity)context).initErrorButton();
                }
            });
        }

        return convertView;
    }

    private UploadBigClassroomCourseRecord initVcrPath(UploadBigClassroomCourseRecordBoolean sb){
        UploadBigClassroomCourseRecord vcrPath = new UploadBigClassroomCourseRecord();
        vcrPath.setClub_id(sb.getClub_id());
        vcrPath.setClasses(sb.getClasses());
        vcrPath.setClassroom_id(sb.getClassroom_id());
        vcrPath.setUser_id(sb.getUser_id());
        vcrPath.setYouku_video_url(sb.getYouku_video_url());
        vcrPath.setIs_finished(sb.getIs_finished());
        vcrPath.setFinished_time(sb.getFinished_time());
        vcrPath.setPath(sb.getPath());
        return vcrPath;
    }

    class ViewHoler{
        TextView textView;
        Button button;
    }
}
