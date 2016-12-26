package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.Base.BaseRecyclerViewNoRefresh;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.BigClassroomRecords;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.adapter.BigRoomListAdapter;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.Refactor.view.DividerItemDecoration;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jy on 16/8/25.
 */
public class BigRoomListActivity extends BaseRecyclerViewNoRefresh {

    private BigRoomListAdapter adapter;
    private List<BigClassroomRecords> list;

    @Override
    protected void initToolBar() {
        setToolBarTitle("完成列表");
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {

    }

    private void getBigClassroomRecord(final String id) {
        showLoadingDiaglog();
        Request<String> request = NoHttp.createStringRequest(Constants.BIG_CLASSROOM_RECORD, RequestMethod.GET);
        request.add("club_id",getIntent().getStringExtra("club_id")+"");
        request.add("big_classroom_record_id", id);
        request.add("authenticity_token", MD5.getToken(Constants.BIG_CLASSROOM_RECORD));
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                closeLoadingDialog();
                LogUtils.w("getBigClassroomRecord");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    KTApplication.setBigClassroomRecord(response.get());
                    Intent intent = new Intent(getThis(),PlayerActivity.class);
                    intent.putExtra(Constants.BIGROOMLESSONID, KTApplication.getBigClassroomRecord());
                    startActivity(intent);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.w("getBigClassroomRecord = onFailed");
                closeLoadingDialog();
            }
        }, false, false);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showLoadingDiaglog();
        Request<String> request = NoHttp.createStringRequest(Constants.BIG_CLASSROOM_RECORDS, RequestMethod.GET);
        request.add("club_id", getIntent().getStringExtra("club_id"));
        request.add("authenticity_token", MD5.getToken(Constants.BIG_CLASSROOM_RECORDS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                String data = response.get();
                list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("big_classroom_records");
                    for (int x = 0; x < jsonArray.length(); x++) {
                        BigClassroomRecords bean = GsonTools.changeGsonToBean(jsonArray.get(x).toString(), BigClassroomRecords.class);
                        list.add(bean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LinearLayoutManager ll = new LinearLayoutManager(getThis());
                adapter = new BigRoomListAdapter(getRecyclerView(), list);
                ll.setOrientation(LinearLayoutManager.VERTICAL);
                setLayoutManager(ll);
                getRecyclerView().addItemDecoration(new DividerItemDecoration(getThis(), ll.getOrientation()));
                setAdapter(adapter);
                closeLoadingDialog();
                adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(ViewGroup parent, View view, int position) {
                        getBigClassroomRecord(list.get(position).id);
//                showDialogToast(list.get(position).id);
                    }
                });
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.w("getBigClassroomRecords = onFailed");
                closeLoadingDialog();
            }
        }, false, false);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackgroundResource(R.drawable.judge_background);
    }
}
