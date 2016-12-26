package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.frame.app.view.NumberProgressBar.NumberProgressBar;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecord;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecordBoolean;
import com.newer.kt.Business.ServiceLoadBusiness;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.Token;
import com.newer.kt.Refactor.db.UploadBigClassroomCourseRecordBooleanDaoHelper;
import com.newer.kt.Refactor.db.UploadBigClassroomCourseRecordDaoHelper;
import com.newer.kt.Refactor.ui.Avtivity.LoginActivity;
import com.newer.kt.Refactor.ui.Avtivity.UpLessonHistoryActivity;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.adapter.LessonVideoAdapter;
import com.newer.kt.url.VolleyUtil;
import com.youku.uploader.IUploadResponseHandler;
import com.youku.uploader.YoukuUploader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by jy on 16/8/17.
 */
public class VideoUploadActivity extends BaseActivity {

    @Bind(R.id.listView2)
    ListView listView;
    @Bind(R.id.textView70)
    TextView textViewCount;
    @Bind(R.id.activity_video_error_count)
    TextView errorCount;
    @Bind(R.id.buttonUp)
    Button button;

    ArrayList<String> list;
    ArrayList<String> copyList;
    int size;
    private LessonVideoAdapter videoAdapter;

    private String access_token;
    private String CLIENT_ID = "5570b4b24049e570";
    private String CLIENT_SECRET = "c40bcc367334ef63e42ef4562b460e7f";
    private YoukuUploader uploader;
    ServiceLoadBusiness sb;
    private NumberProgressBar progressBar;
    private String club_id;
    private AlertDialog dialog;

    private void doSaveOk(UploadBigClassroomCourseRecord sb) {//上传成功
        UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().addData(initVcrPath(sb, true));
    }

    private void doSaveFailure(UploadBigClassroomCourseRecord sb) {//上传失败
        UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().addData(initVcrPath(sb, false));
    }

    private UploadBigClassroomCourseRecordBoolean initVcrPath(UploadBigClassroomCourseRecord sb, boolean isSuccess) {
        UploadBigClassroomCourseRecordBoolean vcrPath = new UploadBigClassroomCourseRecordBoolean();
        vcrPath.setClub_id(sb.getClub_id());
        vcrPath.setUser_id(sb.getUser_id());
        vcrPath.setClassroom_id(sb.getClassroom_id());
        vcrPath.setClasses(sb.getClasses());
        vcrPath.setIs_finished(sb.getIs_finished());
        vcrPath.setFinished_time(sb.getFinished_time());
        vcrPath.setPath(sb.getPath());
        vcrPath.setIsSuccess(isSuccess);
        return vcrPath;
    }

    @Override
    protected void initHandler(Message msg) {
        switch (msg.what) {
            case 123:
                uploadFinishSuccess(msg);
                break;
            case 456:
                String s = (String) msg.obj;
                uploadFinishFailure(s);
                break;
            case ServiceLoadBusiness.GET_ROLE_SUCCESS:
                closeLoadingDialog();
                Token token = (Token) msg.obj;
                access_token = token.youku_token;
                if (list.size() != 0) {
                    textViewCount.setText("" + list.size());
                    button.setEnabled(false);
                    button.setText("正在上传");
                    size = list.size();
                    copyList = new ArrayList<>(list);
                    doUp(list.get(current));
                }
                break;
            case ServiceLoadBusiness.GET_ROLE_FAILURE:
                closeLoadingDialog();
                showToast((String) msg.obj);
                break;
        }
    }

    private void uploadFinishSuccess(Message msg) {
        queryByPath(((String[]) msg.obj)[0], ((String[]) msg.obj)[1]);
        videoAdapter.deleteItem(((String[]) msg.obj)[0]);//删除listview中的path
        List<UploadBigClassroomCourseRecord> sideAandBsList = UploadBigClassroomCourseRecordDaoHelper.getInstance().queryByPath(((String[]) msg.obj)[0]);
        for (UploadBigClassroomCourseRecord ab : sideAandBsList) {
            doSaveOk(ab);
            UploadBigClassroomCourseRecordDaoHelper.getInstance().deleteByVcrPath(ab);
        }
        vidoeUploadFinish();
    }

    private void uploadFinishFailure(String path) {
        videoAdapter.deleteItem(path);//删除listview中的path
        List<UploadBigClassroomCourseRecord> sideAandBsList = UploadBigClassroomCourseRecordDaoHelper.getInstance().queryByPath(path);
        for (UploadBigClassroomCourseRecord ab : sideAandBsList) {
            doSaveFailure(ab);
            UploadBigClassroomCourseRecordDaoHelper.getInstance().deleteByVcrPath(ab);
        }
        vidoeUploadFinish();
    }

    private void vidoeUploadFinish() {
        int i = list.size() + 1;
        textViewCount.setText(String.valueOf(--i));
        if (++current < copyList.size()) {
            doUp(copyList.get(current));
        } else {
            setUploadButtonClick();
        }
    }

    private void setUploadButtonClick() {
        button.setEnabled(true);
        button.setText("全部上传");
        current = 0;
        getErrorCount();
        uploader = null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_video);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        sb = ServiceLoadBusiness.getInstance();
        list = new ArrayList<>();
        copyList = new ArrayList<>();
        textViewCount.setText("0");
        getErrorCount();
    }

    private void initView() {
        list.clear();
        List<UploadBigClassroomCourseRecord> sideAandBsList = UploadBigClassroomCourseRecordDaoHelper.getInstance().getAllData();
        for (UploadBigClassroomCourseRecord ab : sideAandBsList) {
            String path = ab.getPath();
            File file = new File(path);
            if (file.exists()) {
                list.add(path);
            }
        }
        videoAdapter = new LessonVideoAdapter(list, this);
        listView.setAdapter(videoAdapter);
    }

    private void initData() {
        uploader = YoukuUploader.getInstance(CLIENT_ID, CLIENT_SECRET, getApplicationContext());
        long id = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        club_id = String.valueOf(id);
    }

    public void doBack(View view) {
        finish();
    }

    private int current = 0;

    public void doUploadVideo(View view) {
        Map<String, String> param = new HashMap<>();
        param.put("user_id", club_id);
        LogUtils.e(club_id);
        showLoadingDiaglog();
        if (!sb.getRole(getThis(), getTAG(), param)) {
            if(dialog != null){
                if(!dialog.isShowing()){
                    dialog.show();
                }
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请检查网络");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
            closeLoadingDialog();
        }
    }

    public void doUp(final String s) {
        if (listView != null) {
            progressBar = (NumberProgressBar) listView.getChildAt(0).findViewById(R.id.progressBar20);
        } else {
            progressBar = new NumberProgressBar(getThis());
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", access_token);
        HashMap<String, String> uploadInfo = new HashMap<>();
        uploadInfo.put("title", s);
        uploadInfo.put("tags", "优酷,EVA");
        uploadInfo.put("file_name", s);
        LogUtils.e(s + "");
        uploader = YoukuUploader.getInstance(CLIENT_ID, CLIENT_SECRET, getApplicationContext());
        uploader.upload(params, uploadInfo, new IUploadResponseHandler() {

            @Override
            public void onStart() {
                Log.v(getTAG(), "onStart");
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(JSONObject response) {
                Log.v(getTAG(), "onSuccess    " + response.toString());
                Message msg = Message.obtain();
                msg.what = 123;
                msg.obj = new String[]{s, response.toString()};
                getHandler().sendMessage(msg);
            }

            @Override
            public void onProgressUpdate(int counter) {
                Log.v(getTAG(), "onProgressUpdate    " + counter + "");
                progressBar.setProgress(counter);
            }

            @Override
            public void onFailure(JSONObject errorResponse) {
                Log.v(getTAG(), "onFailure    " + errorResponse.toString());
                Message msg = Message.obtain();
                msg.what = 456;
                msg.obj = s;
                getHandler().sendMessage(msg);
            }

            @Override
            public void onFinished() {
                Log.v(getTAG(), "onFinished");
                progressBar.setProgress(0);
            }
        });
    }

    private void queryByPath(String path, String youKuURI) {
        List<UploadBigClassroomCourseRecord> sideAandBList = UploadBigClassroomCourseRecordDaoHelper.getInstance().queryByPath(path);
        for (UploadBigClassroomCourseRecord sb : sideAandBList) {
            try {
                JSONObject jb = new JSONObject(youKuURI);
                doCommitData(sb, jb.getString("video_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    //    提交数据到服务器
    private void doCommitData(UploadBigClassroomCourseRecord bean,String video_id) {
        String uri = Constants.UPLOAD_BIG_CLASSROOM_COURSE_RECORD;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("club_id", bean.getClub_id());
            jsonObject.put("user_id", bean.getUser_id());
            jsonObject.put("youku_video_url", video_id);
            jsonObject.put("classroom_id", bean.getClassroom_id());
            jsonObject.put("classes", bean.getClasses());
            jsonObject.put("is_finished", bean.getIs_finished());
            jsonObject.put("finished_time", bean.getIs_finished());

            jsonObject.put("authenticity_token", MD5.getToken(uri));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.e(jsonObject.toString());
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                uri,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("onResponse", jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("onErrorResponse", volleyError.toString());
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if (uploader != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("退出将暂停上传视频");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uploader.cancel();
                        finish();
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //上传记录
    public void doUploadHistory(View view) {
        startActivity(new Intent(this, UpLessonHistoryActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uploader != null) {
            uploader.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (access_token == null || "".equals(access_token)) {
            initData();//初始化TOKEN
        }
        getErrorCount();
        initView();
        setUploadButtonClick();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (uploader != null) {
            uploader.cancel();
        }
    }

    private void getErrorCount() {
        List<UploadBigClassroomCourseRecordBoolean> list = UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().queryBySuccess(false);
        if (list == null || list.size() == 0) {
            errorCount.setVisibility(View.GONE);
        } else {
            errorCount.setText(list.size() + "");
            errorCount.setVisibility(View.VISIBLE);
        }
    }
}
