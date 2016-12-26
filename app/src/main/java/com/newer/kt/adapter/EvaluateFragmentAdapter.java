package com.newer.kt.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.StudentsEvaluation;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.ui.Avtivity.ListActivity;
import com.newer.kt.Refactor.ui.Avtivity.UserInfoActivity;
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
 * Created by leo on 16/10/10.
 */

public class EvaluateFragmentAdapter extends BaseExpandableListAdapter {

    private ArrayList<StudentsEvaluation> mClasList;
    private Context mContext;
    private List<List<User>> mUser = new ArrayList<>();
    private Integer[] mHeight = new Integer[90];
    private Integer[] mWeight = new Integer[90];
    private GradeDialog mGradeDialog;

    public EvaluateFragmentAdapter(Context context, ArrayList<StudentsEvaluation> classlist) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ecaluate_title, null);
            p.mTv_All = (TextView) convertView.findViewById(R.id.tv_all);
            p.mTv_Nan = (TextView) convertView.findViewById(R.id.tv_nan);
            p.mTv_Nv = (TextView) convertView.findViewById(R.id.tv_nv);
            p.mAll = convertView.findViewById(R.id.view_all);
            p.mNan = convertView.findViewById(R.id.view_nam);
            p.mNv = convertView.findViewById(R.id.view_nv);
            p.mTV_Class_name = (TextView) convertView.findViewById(R.id.class_name);
            p.mLinear_all = (LinearLayout) convertView.findViewById(R.id.linear_all);
            p.mLinear_nv = (LinearLayout) convertView.findViewById(R.id.linear_nv);
            p.mLinear_nan = (LinearLayout) convertView.findViewById(R.id.linear_nan);
            convertView.setTag(p);
        } else {
            p = (ParentViewhodler) convertView.getTag();
        }
        p.mTV_Class_name.setText(mClasList.get(groupPosition).name);
        p.mLinear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.mAll.setVisibility(View.VISIBLE);
                p.mNan.setVisibility(View.INVISIBLE);
                p.mNv.setVisibility(View.INVISIBLE);
                p.mTv_All.setTextColor(v.getContext().getResources().getColor(R.color.gold));
                p.mTv_Nan.setTextColor(v.getContext().getResources().getColor(R.color.white));
                p.mTv_Nv.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mUser.get(groupPosition).clear();
                mUser.get(groupPosition).addAll(mClasList.get(groupPosition).users);
                notifyDataSetChanged();
            }
        });
        p.mLinear_nan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.mAll.setVisibility(View.INVISIBLE);
                p.mNan.setVisibility(View.VISIBLE);
                p.mNv.setVisibility(View.INVISIBLE);
                p.mTv_All.setTextColor(v.getContext().getResources().getColor(R.color.white));
                p.mTv_Nan.setTextColor(v.getContext().getResources().getColor(R.color.gold));
                p.mTv_Nv.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mUser.get(groupPosition).clear();
                for (int i = 0; i < mClasList.get(groupPosition).users.size(); i++) {
                    if (mClasList.get(groupPosition).users.get(i).gender.equals("GG"))
                        mUser.get(groupPosition).add(mClasList.get(groupPosition).users.get(i));
                }
                notifyDataSetChanged();
            }
        });
        p.mLinear_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.mAll.setVisibility(View.INVISIBLE);
                p.mNan.setVisibility(View.INVISIBLE);
                p.mNv.setVisibility(View.VISIBLE);
                p.mTv_All.setTextColor(v.getContext().getResources().getColor(R.color.white));
                p.mTv_Nan.setTextColor(v.getContext().getResources().getColor(R.color.white));
                p.mTv_Nv.setTextColor(v.getContext().getResources().getColor(R.color.gold));
                mUser.get(groupPosition).clear();
                for (int i = 0; i < mClasList.get(groupPosition).users.size(); i++) {
                    if (mClasList.get(groupPosition).users.get(i).gender.equals("MM"))
                        mUser.get(groupPosition).add(mClasList.get(groupPosition).users.get(i));
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHodler childViewHodler;
        if (convertView == null) {
            childViewHodler = new ChildViewHodler();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ecaluate_content, null);
            childViewHodler.mHead = (ImageView) convertView.findViewById(R.id.image_head);
            childViewHodler.mTv_sex = (TextView) convertView.findViewById(R.id.tv_sex);
            childViewHodler.mTv_height = (TextView) convertView.findViewById(R.id.tv_height);
            childViewHodler.mTv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
            childViewHodler.mTv_name = (TextView) convertView.findViewById(R.id.tv_username);
            convertView.setTag(childViewHodler);
        } else {
            childViewHodler = (ChildViewHodler) convertView.getTag();
        }
        Glide.with(mContext).load(Constants.KTHOST + mUser.get(groupPosition).get(childPosition).phone).transform(new GlideCircleTransform(convertView.getContext())).error(R.mipmap.default_head).into(childViewHodler.mHead);
        childViewHodler.mTv_name.setText(mUser.get(groupPosition).get(childPosition).nickname);
        childViewHodler.mTv_weight.setText(mUser.get(groupPosition).get(childPosition).weight == null ? "体重kg" : mUser.get(groupPosition).get(childPosition).weight + "kg");
        childViewHodler.mTv_height.setText(mUser.get(groupPosition).get(childPosition).height == null ? "身高cm" : mUser.get(groupPosition).get(childPosition).height + "cm");
        childViewHodler.mTv_sex.setText(mUser.get(groupPosition).get(childPosition).gender.equals("GG") ? "男" : "女");
        if (mUser.get(groupPosition).get(childPosition).weight == null){
            childViewHodler.mTv_weight.setTextColor(0x4cffffff);
            childViewHodler.mTv_height.setTextColor(0x4cffffff);
        }else{
            childViewHodler.mTv_weight.setTextColor(0xffffffff);
            childViewHodler.mTv_height.setTextColor(0xffffffff);
        }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUser.get(groupPosition).get(childPosition).weight == null) {
                        mGradeDialog.show();
                        mGradeDialog.setonChooseListener(new onChooseListener() {
                            @Override
                            public void onChoose(int height, int weight) {
                                childViewHodler.mTv_height.setText(height + "cm");
                                childViewHodler.mTv_weight.setText(weight + "kg");
                                saveUserInfo(height, weight, mUser.get(groupPosition).get(childPosition).user_id);
                                EventBus.getDefault().post(new MainEvent(1));
                            }
                        });
                    }else{
                        Intent intent1 = new Intent(mContext, UserInfoActivity.class);
                        String userId = mUser.get(groupPosition).get(childPosition).user_id;
                        intent1.putExtra("userId", userId);
                        intent1.putExtra(ListActivity.EXTRA_USER_CODE, 2);
                        intent1.putExtra("grade", mClasList.get(groupPosition).grade);
                        intent1.putExtra("cls", Integer.valueOf(mClasList.get(groupPosition).cls));
                        mContext.startActivity(intent1);
                    }

                }
            });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class ChildViewHodler {
        private TextView mTv_name, mTv_sex, mTv_height, mTv_weight;
        private ImageView mHead;
    }

    public class ParentViewhodler {
        private View mAll, mNan, mNv;
        private TextView mTv_All, mTv_Nan, mTv_Nv;
        private TextView mTV_Class_name;
        private LinearLayout mLinear_nan, mLinear_nv, mLinear_all;

    }


    public class GradeDialog
            extends Dialog
            implements View.OnClickListener {

        public GradeDialog(Context context) {
            super(context);
            for (int i = 0; i < 90; i++) {
                mHeight[i] = i + 50;
                mWeight[i] = i + 10;
            }
        }

        private WheelView myGrader;
        private WheelView myCls;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            super.onCreate(savedInstanceState);

            View view = View.inflate(mContext, R.layout.dialog_wheel, null);

            myGrader = (WheelView) view.findViewById(R.id.year);
            myCls = (WheelView) view.findViewById(R.id.month);

            view.findViewById(R.id.day).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.selectDialog_txv_title)).setText("选择信息");

            ArrayWheelAdapter<Integer> s1 = new ArrayWheelAdapter<>(mContext, mHeight);
            ArrayWheelAdapter<Integer> s2 = new ArrayWheelAdapter<>(mContext, mWeight);

            myGrader.setViewAdapter(s1);
            myCls.setViewAdapter(s2);

            myGrader.setCyclic(false);
            myGrader.setVisibleItems(mHeight.length);
            myGrader.setCurrentItem(1);

            myCls.setCyclic(false);
            myCls.setVisibleItems(mWeight.length);
            myCls.setCurrentItem(1);

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
                    int j = myCls.getCurrentItem();
                    mOnChooseListener.onChoose(mHeight[i], mWeight[j]);
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
        void onChoose(int height, int weight);
    }

    private onChooseListener mOnChooseListener;


    private void saveUserInfo(int height, int weight, String id) {
        ((BaseActivity) mContext).showLoadingDiaglog();
        Request<String> request = NoHttp.createStringRequest(Constants.SAVE_USER_HEIGHT_WEIGHT, RequestMethod.POST);
        request.add("user_id", id);
        request.add("height", height);
        request.add("weight", weight);
        request.add("authenticity_token", MD5.getToken(Constants.SAVE_USER_HEIGHT_WEIGHT));
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