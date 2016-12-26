package com.newer.kt.Refactor.ui.Avtivity.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.adapter.FootBallDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/10/12.
 */

public class FootBallDetailActivity extends BaseActivity {
    private View mAll, mNan, mNv;
    private TextView mTv_All, mTv_Nan, mTv_Nv;
    private LinearLayout mLinear_nan, mLinear_nv, mLinear_all;
    /**
     * 内容list
     */
    private ListView mListView_Content;
    /**
     * 视频list
     */
    private ListView mList_Video;

    /**
     * 说明test
     * @param msg
     */
    private TextView mTv_Shuom;

    /**
     * 指标
     * @param msg
     */
    private LinearLayout mLinear;
    private Button button;
    private TextView mTv_foot_tab1,mTv_foot_tab2,mTv_foot_tab3;
    private View mView_tab1,mView_tab2,mView_tab3;
    private LinearLayout mLiear_tan1,mLiear_tan2,mLiear_tan3;
    private GridView mGridView;
    @Bind(R.id.image_selecet)
    ImageView mImage_Selceted;
    @Bind(R.id.tv_title)
    TextView mTv_Title;
    private List<String> mTabList = new ArrayList<>();
    private int isCheck = 0;


    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_football_detail);
        mTv_All = getViewById(R.id.tv_all);
        mTv_Nan = getViewById(R.id.tv_nan);
        mTv_Nv = getViewById(R.id.tv_nv);
        mAll = getViewById(R.id.view_all);
        mNan = getViewById(R.id.view_nam);
        mNv = getViewById(R.id.view_nv);
        mLinear_all = getViewById(R.id.linear_all);
        mLinear_nv = getViewById(R.id.linear_nv);
        mLinear_nan = getViewById(R.id.linear_nan);
        mListView_Content = getViewById(R.id.listview_content);
        mList_Video = getViewById(R.id.listView_tab3);
        mTv_Shuom = getViewById(R.id.tv_content_tab1);
        mLinear = getViewById(R.id.linear_tab2);
        button = getViewById(R.id.btn);
        mTv_foot_tab1 = getViewById(R.id.tv_foot_tab1);
        mTv_foot_tab2 = getViewById(R.id.tv_foot_tab2);
        mTv_foot_tab3 = getViewById(R.id.tv_foot_tab3);
        mView_tab1 = getViewById(R.id.view1);
        mView_tab2 = getViewById(R.id.view2);
        mView_tab3 = getViewById(R.id.view3);
        mLiear_tan1 = getViewById(R.id.linear_shuom);
        mLiear_tan2 = getViewById(R.id.linear_zhibiao);
        mLiear_tan3 = getViewById(R.id.linear_video);
        mGridView = getViewById(R.id.gridView);
        mTabList.add("d12e21");
        mTabList.add("d12e21");
        mTabList.add("d12e21");
        mTabList.add("d12e21");
        mTabList.add("d12e21");
        mTabList.add("d12e21");
        mGridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mTabList.size();
            }

            @Override
            public Object getItem(int position) {
                return mTabList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                TabView mTabView;
                if (convertView == null){
                    mTabView = new TabView();
                    convertView = getLayoutInflater().inflate(R.layout.item_foot_tab,null);
                    mTabView.mTitle = (TextView) convertView.findViewById(R.id.mTv_title);
                    mTabView.mTabLine = convertView.findViewById(R.id.view_tab);
                    mTabView.mTabV = convertView.findViewById(R.id.view);
                    convertView.setTag(mTabView);
                }else{
                    mTabView = (TabView) convertView.getTag();
                }
                mTabView.mTitle.setText(mTabList.get(position));
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isCheck = position;
                        notifyDataSetChanged();
                    }
                });
                if (position == isCheck){
                    mTabView.mTabLine.setVisibility(View.VISIBLE);
                }else{
                    mTabView.mTabLine.setVisibility(View.INVISIBLE);
                }
                if ((position+1)%3 == 0){
                    mTabView.mTabV.setVisibility(View.INVISIBLE);
                }else{
                    mTabView.mTabV.setVisibility(View.VISIBLE);
                }

                return convertView;
            }
        });
        mListView_Content.setAdapter(new FootBallDetailAdapter(getThis(),mTabList));


    }

    @Override
    protected void setListener() {
        mLinear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAll.setVisibility(View.VISIBLE);
                mNan.setVisibility(View.INVISIBLE);
                mNv.setVisibility(View.INVISIBLE);
                mTv_All.setTextColor(v.getContext().getResources().getColor(R.color.gold));
                mTv_Nan.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_Nv.setTextColor(v.getContext().getResources().getColor(R.color.white));
            }
        });
        mLinear_nan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAll.setVisibility(View.INVISIBLE);
                mNan.setVisibility(View.VISIBLE);
                mNv.setVisibility(View.INVISIBLE);
                mTv_All.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_Nan.setTextColor(v.getContext().getResources().getColor(R.color.gold));
                mTv_Nv.setTextColor(v.getContext().getResources().getColor(R.color.white));
            }
        });
        mLinear_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAll.setVisibility(View.INVISIBLE);
                mNan.setVisibility(View.INVISIBLE);
                mNv.setVisibility(View.VISIBLE);
                mTv_All.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_Nan.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_Nv.setTextColor(v.getContext().getResources().getColor(R.color.gold));
            }
        });
        mLiear_tan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView_tab1.setVisibility(View.VISIBLE);
                mView_tab2.setVisibility(View.GONE);
                mView_tab3.setVisibility(View.GONE);
                mTv_foot_tab1.setTextColor(v.getContext().getResources().getColor(R.color.gold));
                mTv_foot_tab2.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_foot_tab3.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_Shuom.setVisibility(View.VISIBLE);
                mList_Video.setVisibility(View.GONE);
                mLinear.setVisibility(View.GONE);

            }
        });
        mLiear_tan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView_tab2.setVisibility(View.VISIBLE);
                mView_tab1.setVisibility(View.GONE);
                mView_tab3.setVisibility(View.GONE);
                mTv_foot_tab2.setTextColor(v.getContext().getResources().getColor(R.color.gold));
                mTv_foot_tab1.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_foot_tab3.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mLinear.setVisibility(View.VISIBLE);
                mList_Video.setVisibility(View.GONE);
                mTv_Shuom.setVisibility(View.GONE);
            }
        });
        mLiear_tan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView_tab3.setVisibility(View.VISIBLE);
                mView_tab2.setVisibility(View.GONE);
                mView_tab1.setVisibility(View.GONE);
                mTv_foot_tab3.setTextColor(v.getContext().getResources().getColor(R.color.gold));
                mTv_foot_tab2.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_foot_tab1.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mList_Video.setVisibility(View.VISIBLE);
                mTv_Shuom.setVisibility(View.GONE);
                mLinear.setVisibility(View.GONE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(),RecordingActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.image_back)
    public void back(){
        finish();
    }

    @OnClick(R.id.linear_select)
    public void  seleted(){
        if (mGridView.getVisibility() == View.VISIBLE){
            mGridView.setVisibility(View.GONE);
            mImage_Selceted.setImageResource(R.mipmap.foot_up);
        }else{
            mGridView.setVisibility(View.VISIBLE);
            mImage_Selceted.setImageResource(R.mipmap.foot_down);
        }

    }
    private class TabView{
        TextView mTitle;
        View mTabLine;
        View mTabV;
    }
}
