package com.newer.kt.fragment.peixun;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.kt.R;
import com.newer.kt.Refactor.Entitiy.BigClassIndexData;
import com.newer.kt.adapter.BigClassAdater;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by leo on 16/11/9.
 */

public class BigClassFragment extends BaseFragment {
    @Bind(R.id.tv_all_class)
    TextView mAll_class;
    @Bind(R.id.tv_all_people)
    TextView mAll_people;
    @Bind(R.id.tv_all)
    TextView mAll;
    @Bind(R.id.tv_pingjun)
    TextView mAll_pingjun;
    @Bind(R.id.tv_all_nale)
    TextView tv_all_nale;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private Typeface mTf;
    String user;
    String password;
    public static final String EXTRA_CLUB_ID = "club_id";
    public static final String EXTRA_CLUB_NAME = "club_name";
    public static final String PRE_CURRENT_USER_ID = "current_user_id";//提交比分时数据
    public static final String PRE_CURRENT_CLUB_ID = "current_club_id";
    public static final String PRE_CURRENT_CLUB_NAME = "current_club_name";
    public static final String PRE_CURRENT_TAYPE = "current_type";
    private BigClassIndexData indexData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_big_class);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTf = Typeface.createFromAsset(getActivity().getAssets(), "BEBAS.ttf");
        //mAll.setTypeface(mTf);
        //mAll.setText(indexData.getSchool_big_classroom_count());
       // mAll_people.setText(indexData.getUsers_count());
       // mAll_class.setText(indexData.getClass_count());
        //mAll_pingjun.setTypeface(mTf);
        //mAll_class.setTypeface(mTf);
        //mAll_people.setTypeface(mTf);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> mList = new ArrayList<>();
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mRecyclerView.setAdapter(new BigClassAdater(getActivity(),mList));

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
    public void initData(){
        RequestParams p = new RequestParams("http://api.ktfootball.com/school_big_class_rooms/home_tongji");
        p.addQueryStringParameter("authenticity_token","K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
        p.addQueryStringParameter("club_id","89");
        x.http().get(p, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                indexData = new Gson().fromJson(result.toString(),new TypeToken<BigClassIndexData>(){}.getType());
                mAll.setText(indexData.getSchool_big_classroom_count());
                 mAll_people.setText(indexData.getUsers_count());
                 mAll_class.setText(indexData.getClass_count());

    }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


            } });
}

    @Override
    public void onDestroy() {
        super.onDestroy();
        indexData=null;
    }
}