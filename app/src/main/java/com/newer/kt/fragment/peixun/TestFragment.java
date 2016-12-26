package com.newer.kt.fragment.peixun;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.kt.R;
import com.newer.kt.Refactor.Entitiy.TestIndexData;
import com.newer.kt.Refactor.Entitiy.TestPaiMingBean;
import com.newer.kt.Refactor.ui.Avtivity.TestListActivity;
import com.newer.kt.Refactor.view.LoadMoreRecyclerView;
import com.newer.kt.adapter.TestAdater;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
/**
 * Created by leo on 16/11/9.
 */

public class TestFragment extends BaseFragment implements LoadMoreRecyclerView.LoadMoreListener {
    @Bind(R.id.tv_all_people)
    TextView mAll_people;
    @Bind(R.id.tv_all)
    TextView mAll;
    @Bind(R.id.tv_pingjun)
    TextView mAll_pingjun;
    @Bind(R.id.tv_all_nale)
    TextView tv_all_nale;
    @Bind(R.id.recyclerview)
    LoadMoreRecyclerView mRecyclerView;
    private Typeface mTf;
    @Bind(R.id.image_nomal)
    ImageView mImageView;
    List<String> mList = new ArrayList<>();
    //List<TestPaiMingBean> mList1 = new ArrayList<TestPaiMingBean>();
    @Bind(R.id.appbar)
    AppBarLayout appbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDa();
        initData2();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_test);
    }

    private void initDa() {
        RequestParams p = new RequestParams("http://api.ktfootball.com/shool_user_tests/school_football_skill_test_home_page_tongji");
        p.addQueryStringParameter("authenticity_token","K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
        p.addQueryStringParameter("club_id","89");
        x.http().get(p, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {
                TestIndexData testIndexData = new Gson().fromJson(result.toString(),new TypeToken<TestIndexData>(){}.getType());
                mAll_people.setText(testIndexData.getUsers_count());
                mAll_pingjun.setText(testIndexData.getAverage_scores());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
       /* mTf = Typeface.createFromAsset(getActivity().getAssets(), "BEBAS.ttf");
        mAll.setTypeface(mTf);
        mAll_pingjun.setTypeface(mTf);
        mAll_people.setTypeface(mTf);*/


        mRecyclerView.setLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mRecyclerView.setAdapter(new TestAdater(mList));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appbar.setOutlineProvider(null);
        }
    }
    //加载测评页面排名数据
    private void initData2() {
        RequestParams p = new RequestParams("http://api.ktfootball.com/shool_user_tests/school_football_skill_test_ranking");
        p.addQueryStringParameter("authenticity_token","K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
        p.addQueryStringParameter("club_id","89");
        x.http().get(p, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {
                TestPaiMingBean testPaiMingBean = new Gson().fromJson(result.toString(),new TypeToken<TestPaiMingBean>(){}.getType());
                System.out.println("----123"+testPaiMingBean.toString());
                /*mAll_people.setText(testIndexData.getUsers_count());
                mAll_pingjun.setText(testIndexData.getAverage_scores());*/
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


            }
        });
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    @OnClick(R.id.tv_start)
    public void start() {
        Intent intent = new Intent(getThis(), TestListActivity.class);
        startActivity(intent);

    }

    @Override
    public void onLoadMore() {
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
