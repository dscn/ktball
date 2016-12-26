package com.newer.kt.Refactor.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.ui.Avtivity.ListActivity;
import com.newer.kt.Refactor.ui.Avtivity.MyClassActivity;
import com.newer.kt.Refactor.ui.Avtivity.UserInfoActivity;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.Refactor.view.wheelview.WheelView;
import com.newer.kt.Refactor.view.wheelview.adapter.ArrayWheelAdapter;
import com.newer.kt.adapter.EvaluateFragmentAdapter;
import com.newer.kt.entity.User;
import com.newer.kt.event.MainEvent;
import com.newer.kt.myClass.GlideCircleTransform;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by leo on 16/10/12.
 */

public class ClassManagerAdapter extends RecyclerView.Adapter<ClassManagerAdapter.ViewHoder> {
    private Context mContext;
    private ArrayList<User> mList;
    private Integer[] mHeight = new Integer[90];
    private Integer[] mWeight = new Integer[90];
    private GradeDialog mGradeDialog;
    int cls;
    int grade;

    public ClassManagerAdapter(ArrayList<User> mList, Context mContext, int grade, int cls) {
        this.mList = mList;
        this.mContext = mContext;
        this.cls = cls;
        this.grade = grade;
        mGradeDialog = new GradeDialog(mContext);
    }

    @Override
    public ClassManagerAdapter.ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(mContext).inflate(R.layout.item_ecaluate_content, null));
    }

    @Override
    public void onBindViewHolder(final ClassManagerAdapter.ViewHoder childViewHodler, final int childPosition) {
        Glide.with(mContext).load(Constants.KTHOST + mList.get(childPosition).phone).transform(new GlideCircleTransform(mContext)).error(R.mipmap.default_head).into(childViewHodler.mHead);
        childViewHodler.mTv_name.setText(mList.get(childPosition).nickname);
        childViewHodler.mTv_weight.setText(mList.get(childPosition).weight == null ? "体重kg" : mList.get(childPosition).weight + "kg");
        childViewHodler.mTv_height.setText(mList.get(childPosition).height == null ? "身高cm" : mList.get(childPosition).height + "cm");
        childViewHodler.mTv_sex.setText(mList.get(childPosition).gender.equals("GG") ? "男" : "女");
        if (mList.get(childPosition).weight == null) {
            childViewHodler.mTv_weight.setTextColor(0x4cffffff);
            childViewHodler.mTv_height.setTextColor(0x4cffffff);
        } else {
            childViewHodler.mTv_weight.setTextColor(0xffffffff);
            childViewHodler.mTv_height.setTextColor(0xffffffff);
        }

        childViewHodler.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.get(childPosition).weight == null) {
                    mGradeDialog.show();
                    mGradeDialog.setonChooseListener(new EvaluateFragmentAdapter.onChooseListener() {
                        @Override
                        public void onChoose(int height, int weight) {
                            childViewHodler.mTv_height.setText(height + "cm");
                            childViewHodler.mTv_weight.setText(weight + "kg");
                            saveUserInfo(height, weight, mList.get(childPosition).user_id);
                            EventBus.getDefault().post(new MainEvent(1));
                        }
                    });
                } else {
                    Intent intent1 = new Intent(mContext, UserInfoActivity.class);
                    String userId = mList.get(childPosition).user_id;
                    intent1.putExtra("userId", userId);
                    intent1.putExtra(ListActivity.EXTRA_USER_CODE, 2);
                    intent1.putExtra("grade", grade);
                    intent1.putExtra("cls", cls);
                    mContext.startActivity(intent1);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 筛选
     *
     * @param mTag
     * @param user
     */
    public void setCheck(int mTag, ArrayList<User> user) {
        mList.clear();
        for (int i = 0; i < user.size(); i++) {
            switch (mTag) {
                case 0:
                    mList.add(user.get(i));
                    break;
                case 1:
                    if (user.get(i).gender.equals("GG"))
                        mList.add(user.get(i));
                    break;
                case 2:
                    if (user.get(i).gender.equals("MM"))
                        mList.add(user.get(i));
                    break;
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        private TextView mTv_name, mTv_sex, mTv_height, mTv_weight;
        private ImageView mHead;
        private View view;

        public ViewHoder(View itemView) {
            super(itemView);
            mHead = (ImageView) itemView.findViewById(R.id.image_head);
            mTv_sex = (TextView) itemView.findViewById(R.id.tv_sex);
            mTv_height = (TextView) itemView.findViewById(R.id.tv_height);
            mTv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            mTv_name = (TextView) itemView.findViewById(R.id.tv_username);
            view = itemView;

        }
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

        public void setonChooseListener(EvaluateFragmentAdapter.onChooseListener onChooseListener) {
            if (onChooseListener != null) {
                mOnChooseListener = onChooseListener;
            }
        }


    }

    public interface onChooseListener {
        void onChoose(int height, int weight);
    }

    private EvaluateFragmentAdapter.onChooseListener mOnChooseListener;


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
