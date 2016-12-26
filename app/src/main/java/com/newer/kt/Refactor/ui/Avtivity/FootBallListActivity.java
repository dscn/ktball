package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.SharedPreferencesUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.Combinations;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinationDetail;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinations;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.Request.SchoolGymCourseCombinationDetailRequest;
import com.newer.kt.Refactor.utils.FootBallListManager;
import com.newer.kt.Refactor.view.CircleProgressView;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by leo on 16/11/10.
 * 足球课模板二级菜单
 */
public class FootBallListActivity extends BaseActivity {
    @Bind(R.id.expanded_menu)
    ExpandableListView mExpandableListView;
    private SchoolGymCourseCombinations bean;
    private List<String> name;
    private List<List<Combinations>> conent = new ArrayList<>();
    private List<CircleProgressView> mList = new ArrayList<>();

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_football_list);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String data = KTApplication.getSchoolGymCourseCombinations();
        bean = GsonTools.changeGsonToBean(data, SchoolGymCourseCombinations.class);
        name = new ArrayList<>();
        for (int x = 0; x < bean.combinations.size(); x++) {
            Combinations combinations = bean.combinations.get(x);
            if (name.size() == 0) {
                name.add(combinations.semester);
                conent.add(new ArrayList<Combinations>());
                conent.get(0).add(combinations);
            } else {
                if (!name.contains(combinations.semester)) {
                    name.add(combinations.semester);
                    conent.add(new ArrayList<Combinations>());
                } else {
                    conent.get(name.indexOf(combinations.semester)).add(combinations);
                }
            }
        }

        mExpandableListView.setAdapter(new BaseExpandableListAdapter() {


            @Override
            public int getGroupCount() {
                return name.size();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return conent.get(groupPosition).size();
            }

            @Override
            public Object getGroup(int groupPosition) {
                return name.get(groupPosition);
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return conent.get(groupPosition).get(childPosition);
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
                return true;
            }


            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                ParentView parentView;
                if (convertView == null) {
                    parentView = new ParentView();
                    convertView = getLayoutInflater().inflate(R.layout.item_foot_parent, null);
                    parentView.mTile = (TextView) convertView.findViewById(R.id.tv_title);
                    parentView.mBag = (ImageView) convertView.findViewById(R.id.img);
                    convertView.setTag(parentView);
                } else {
                    parentView = (ParentView) convertView.getTag();
                }
                parentView.mTile.setText(name.get(groupPosition));
                if (isExpanded) {
                    parentView.mBag.setImageResource(R.mipmap.nomal_up);
                } else {
                    parentView.mBag.setImageResource(R.mipmap.nomal_down);
                }
                return convertView;
            }

            @Override
            public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                final ChikldView parentView;
                if (convertView == null) {
                    parentView = new ChikldView();
                    convertView = getLayoutInflater().inflate(R.layout.item_foot_content, null);
                    parentView.mClass = (TextView) convertView.findViewById(R.id.tv_class);
                    parentView.mTime = (TextView) convertView.findViewById(R.id.tv_timeid);
                    parentView.mQiangdu = (TextView) convertView.findViewById(R.id.tv_qiangdu);
                    parentView.mIsDownlad = (TextView) convertView.findViewById(R.id.tv_download);
                    parentView.mContent = (TextView) convertView.findViewById(R.id.tv_content);
                    parentView.linear_download = (RelativeLayout) convertView.findViewById(R.id.linear_download);
                    parentView.mProgressView = (CircleProgressView) convertView.findViewById(R.id.progress);
                    convertView.setTag(parentView);
                } else {
                    parentView = (ChikldView) convertView.getTag();
                }
                final String data = SharedPreferencesUtils.getString(getThis(), Constants.SCHOOLGYMCOURSECOMBINATIONDETAIL, conent.get(groupPosition).get(childPosition).id, "");
                if (!"".equals(data)) {
                    parentView.linear_download.setVisibility(View.GONE);
                    parentView.mIsDownlad.setVisibility(View.VISIBLE);
                } else {
                    parentView.linear_download.setVisibility(View.VISIBLE);
                    parentView.mIsDownlad.setVisibility(View.GONE);
                }
                parentView.mClass.setText(conent.get(groupPosition).get(childPosition).name);
                parentView.mTime.setText(conent.get(groupPosition).get(childPosition).total_minutes + "分钟");
                parentView.mContent.setText("课程简介 : " + conent.get(groupPosition).get(childPosition).intro);
                parentView.mProgressView.setTag(conent.get(groupPosition).get(childPosition).id);
                parentView.linear_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parentView.mProgressView.setProgress(0);
                        getSchoolGymCourseCombinationDetail(conent.get(groupPosition).get(childPosition).id, parentView.mProgressView, groupPosition);
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), FootBallDetailActivity.class);
                        intent.putExtra("info", conent.get(groupPosition).get(childPosition));
                        startActivity(intent);
                    }
                });
                return convertView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }

            class ParentView {
                TextView mTile;
                ImageView mBag;

            }

            class ChikldView {
                TextView mClass;
                TextView mTime;
                TextView mQiangdu;
                TextView mContent;
                TextView mIsDownlad;
                RelativeLayout linear_download;
                CircleProgressView mProgressView;
            }


        });

//        OkHttpClient okHttpClient = new OkHttpClient();



    }

    public void doBack(View view) {
        finish();

    }


    /**
     *
     * 大课间
     *
     * @param club_id       俱乐部ID
     * @param mProgressView
     */
    private void getSchoolGymCourseCombinationDetail(final String club_id, final CircleProgressView mProgressView, final int groupId) {
        SchoolGymCourseCombinationDetailRequest request = new SchoolGymCourseCombinationDetailRequest(Constants.GET_SCHOOL_GYM_COURSE_COMBINATION_DETAIL, RequestMethod.GET);
        request.add("gym_course_combination", club_id);
        CallServer.getRequestInstance().add((BaseActivity) getThis(), 0, request, new HttpListener<SchoolGymCourseCombinationDetail>() {
            @Override
            public void onSucceed(int what, Response<SchoolGymCourseCombinationDetail> response) {
                if (response != null && response.get() != null && response.get().response.equals("success")) {
                    mList.add(mProgressView);
                    SharedPreferencesUtils.saveString(getThis(), Constants.SCHOOLGYMCOURSECOMBINATIONDETAIL, club_id + "", GsonTools.createGsonString(response.get()));
                    new FootBallListManager(getThis(), response.get(), club_id, groupId, new FootBallListManager.OnDownloadListener() {
                        @Override
                        public void progress(int position, String id) {
                            for (int i = 0; i < mList.size(); i++) {
                                if (mList.get(i).getTag().equals(id)) {
                                    mProgressView.setProgress(position);
                                }
                            }


                        }

                        @Override
                        public void complete(String id, int position) {
                            for (int i = 0; i < mList.size(); i++) {
                                if (mList.get(i).getTag().equals(id)) {
                                    mList.remove(i);
                                }
                            }


                        }
                    });
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }
        }, false, false);
    }


}
