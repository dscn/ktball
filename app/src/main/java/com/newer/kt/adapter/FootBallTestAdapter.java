package com.newer.kt.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.StudentsEvaluation;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.Refactor.view.wheelview.WheelView;
import com.newer.kt.Refactor.view.wheelview.adapter.ArrayWheelAdapter;
import com.newer.kt.entity.User;
import com.newer.kt.event.MainEvent;
import com.newer.kt.myClass.GlideCircleTransform;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 16/10/11.
 */

public class FootBallTestAdapter extends BaseExpandableListAdapter {

    private ArrayList<StudentsEvaluation> mClasList;
    private Context mContext;
    private List<List<User>> mUser = new ArrayList<>();
    private String[] mLvList = new String[]{"优秀", "中等", "一般"};
    private GradeDialog mGradeDialog;

    public FootBallTestAdapter(Context context, ArrayList<StudentsEvaluation> classlist) {
        this.mClasList = classlist;
        this.mContext = context;
        for (int x = 0; x < classlist.size(); x++) {
            StudentsEvaluation studentsEvaluation = new StudentsEvaluation();
            studentsEvaluation.id = classlist.get(x).id;
            studentsEvaluation.grade = classlist.get(x).grade;
            studentsEvaluation.cls = classlist.get(x).cls;
            studentsEvaluation.name = classlist.get(x).name;
            studentsEvaluation.users = new ArrayList<>();
            for (int y = 0; y < classlist.get(x).users.size(); y++) {
                User user = classlist.get(x).users.get(y);
                studentsEvaluation.users.add(user);
            }
            mUser.add(studentsEvaluation.users);
            mGradeDialog = new GradeDialog(context);
        }
    }


    @Override
    public int getGroupCount() {
        return mClasList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mUser.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mClasList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mUser.get(groupPosition).get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ParentViewhodler p;
        if (convertView == null) {
            p = new ParentViewhodler();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_foot_title, null);
            p.mTV_Class_name = (TextView) convertView.findViewById(R.id.class_name);
            p.mTitle = (TextView) convertView.findViewById(R.id.title_name);
            p.mImage = (ImageView) convertView.findViewById(R.id.image_biaoq);
            convertView.setTag(p);
        } else {
            p = (ParentViewhodler) convertView.getTag();
        }
        if (isExpanded) {
            p.mImage.setImageResource(R.drawable.icon_start_class_up);
        } else {
            p.mImage.setImageResource(R.drawable.icon_start_class_down);
        }

        p.mTV_Class_name.setText(mClasList.get(groupPosition).name);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHodler childViewHodler;
        if (convertView == null) {
            childViewHodler = new ChildViewHodler();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_foot_test, null);
            childViewHodler.mHead = (ImageView) convertView.findViewById(R.id.image_head);
            childViewHodler.mTv_sex = (TextView) convertView.findViewById(R.id.tv_sex);
            childViewHodler.tv_lv = (TextView) convertView.findViewById(R.id.tv_lv);
            childViewHodler.mTv_name = (TextView) convertView.findViewById(R.id.tv_username);
            convertView.setTag(childViewHodler);
        } else {
            childViewHodler = (ChildViewHodler) convertView.getTag();
        }
        Glide.with(mContext).load(Constants.KTHOST + mUser.get(groupPosition).get(childPosition).phone).transform(new GlideCircleTransform(convertView.getContext())).error(R.mipmap.default_head).into(childViewHodler.mHead);
        childViewHodler.mTv_name.setText(mUser.get(groupPosition).get(childPosition).nickname);
        childViewHodler.tv_lv.setText(mUser.get(groupPosition).get(childPosition).weight == null ? "请输入测试结果" : mUser.get(groupPosition).get(childPosition).weight + "kg");
        childViewHodler.mTv_sex.setText(mUser.get(groupPosition).get(childPosition).gender.equals("GG") ? "男" : "女");
        if (mUser.get(groupPosition).get(childPosition).weight == null) {
            childViewHodler.tv_lv.setTextColor(0x4cffffff);
        } else {
            childViewHodler.tv_lv.setTextColor(0xffffffff);
        }
        if (mUser.get(groupPosition).get(childPosition).weight == null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGradeDialog.show();
                    mGradeDialog.setonChooseListener(new onChooseListener() {
                        @Override
                        public void onChoose(String content) {
                            childViewHodler.tv_lv.setText(content);
                            //TODO 没有 school_football_skill_id
                            saveUserInfo(content, mUser.get(groupPosition).get(childPosition).user_id, mUser.get(groupPosition).get(childPosition).user_id);
                            EventBus.getDefault().post(new MainEvent(2));
                        }
                    });

                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class ChildViewHodler {
        private TextView mTv_name, mTv_sex, tv_lv;
        private ImageView mHead;
    }

    public class ParentViewhodler {
        private TextView mTV_Class_name;
        private TextView mTitle;
        private ImageView mImage;

    }


    public class GradeDialog
            extends Dialog
            implements View.OnClickListener {

        public GradeDialog(Context context) {
            super(context);
        }

        private WheelView myGrader;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            super.onCreate(savedInstanceState);

            View view = View.inflate(mContext, R.layout.dialog_wheel, null);

            myGrader = (WheelView) view.findViewById(R.id.year);
            view.findViewById(R.id.month).setVisibility(View.GONE);
            view.findViewById(R.id.day).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.selectDialog_txv_title)).setText("选择信息");

            ArrayWheelAdapter<String> s1 = new ArrayWheelAdapter<>(mContext, mLvList);

            myGrader.setViewAdapter(s1);

            myGrader.setCyclic(false);
            myGrader.setVisibleItems(mLvList.length);
            myGrader.setCurrentItem(1);


            view.findViewById(R.id.selectDialog_txv_cacel).setOnClickListener(this);
            view.findViewById(R.id.selectDialog_txv_confirm).setOnClickListener(this);

            setContentView(view);

            setCanceledOnTouchOutside(false);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selectDialog_txv_confirm:
                    int i = myGrader.getCurrentItem();
                    mOnChooseListener.onChoose(mLvList[i]);
                case R.id.selectDialog_txv_cacel:
                    dismiss();
                    break;
            }
        }

        public void setonChooseListener(onChooseListener onChooseListener) {
            if (onChooseListener != null) {
                mOnChooseListener = onChooseListener;
            }
        }


    }

    public interface onChooseListener {
        void onChoose(String content);
    }

    private onChooseListener mOnChooseListener;


    private void saveUserInfo(String content, String school_football_skill_id, String id) {
        ((BaseActivity) mContext).showLoadingDiaglog();
        Request<String> request = NoHttp.createStringRequest(Constants.SAVE_USER_FOOTB_TEST, RequestMethod.POST);
        request.add("user_id", id);
        request.add("school_football_skill_id", school_football_skill_id);
        request.add("content", content);
        request.add("authenticity_token", MD5.getToken(Constants.SAVE_USER_FOOTB_TEST));
        CallServer.getRequestInstance().add((BaseActivity) mContext, 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                ((BaseActivity) mContext).closeLoadingDialog();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                ((BaseActivity) mContext).closeLoadingDialog();

            }
        }, false, false);
    }
}


