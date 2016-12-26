package com.newer.kt.Refactor.ui.Avtivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.frame.app.view.NumberProgressBar.NumberProgressBar;
import com.ktfootball.www.dao.SideAandB;
import com.ktfootball.www.dao.VcrPath;
import com.newer.kt.Business.ServiceLoadBusiness;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.Token;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.adapter.VideoAdapter;
import com.newer.kt.Refactor.db.SideAandBDaoHelper;
import com.newer.kt.Refactor.db.VcrPathDaoHelper;
import com.newer.kt.entity.Side;
import com.newer.kt.myClass.MyAlertDialog;
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

public class VideoActivity extends BaseActivity {

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
    private VideoAdapter videoAdapter;

    private String access_token;
    private String CLIENT_ID = "5570b4b24049e570";
    private String CLIENT_SECRET = "c40bcc367334ef63e42ef4562b460e7f";
    private YoukuUploader uploader;
    ServiceLoadBusiness sb;
    private NumberProgressBar progressBar;
    private String club_id;
    private AlertDialog dialog;

    private void doSaveOk(SideAandB sb) {//上传成功
        VcrPathDaoHelper.getInstance().addData(initVcrPath(sb, true));
    }

    private void doSaveFailure(SideAandB sb) {//上传失败
        VcrPathDaoHelper.getInstance().addData(initVcrPath(sb, false));
    }

    private VcrPath initVcrPath(SideAandB sb, boolean isSuccess) {
        VcrPath vcrPath = new VcrPath();
        vcrPath.setUsers(sb.getUsers());
        vcrPath.setAdd_scores(sb.getAdd_scores());
        vcrPath.setResult(sb.getResult());
        vcrPath.setGoals(sb.getGoals());
        vcrPath.setPannas(sb.getPannas());
        vcrPath.setFouls(sb.getFouls());
        vcrPath.setFlagrant_fouls(sb.getFlagrant_fouls());
        vcrPath.setPanna_ko(sb.getPanna_ko());
        vcrPath.setAbstained(sb.getAbstained());
        vcrPath.setUsers_b(sb.getUsers_b());
        vcrPath.setAdd_scores_b(sb.getAdd_scores_b());
        vcrPath.setResult_b(sb.getResult_b());
        vcrPath.setGoals_b(sb.getGoals_b());
        vcrPath.setPannas_b(sb.getPannas_b());
        vcrPath.setFouls_b(sb.getFouls_b());
        vcrPath.setFlagrant_fouls_b(sb.getFlagrant_fouls_b());
        vcrPath.setPanna_ko_b(sb.getPanna_ko_b());
        vcrPath.setAbstained_b(sb.getAbstained_b());
        vcrPath.setTime(sb.getTime());
        vcrPath.setPath(sb.getPath());
        vcrPath.setGame_type(sb.getGame_type());
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
        List<SideAandB> sideAandBsList = SideAandBDaoHelper.getInstance().queryByPath(((String[]) msg.obj)[0]);
        for (SideAandB ab : sideAandBsList) {
            doSaveOk(ab);
            SideAandBDaoHelper.getInstance().deleteBySideAandB(ab);
        }
        vidoeUploadFinish();
    }

    private void uploadFinishFailure(String path) {
        videoAdapter.deleteItem(path);//删除listview中的path
        List<SideAandB> sideAandBsList = SideAandBDaoHelper.getInstance().queryByPath(path);
        for (SideAandB ab : sideAandBsList) {
            doSaveFailure(ab);
            SideAandBDaoHelper.getInstance().deleteBySideAandB(ab);
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
        List<SideAandB> sideAandBsList = SideAandBDaoHelper.getInstance().getAllData();
        for (SideAandB ab : sideAandBsList) {
            String path = ab.getPath();
            File file = new File(path);
            if (file.exists()) {
                list.add(path);
            }
        }
        videoAdapter = new VideoAdapter(list, this);
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
//            if(dialog == null || !dialog.isShowing()){
//
//            }else{
//                dialog.show();
//            }
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
        uploadInfo.put("title", s.substring(s.indexOf("球") + 2, s.length()));
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
        List<SideAandB> sideAandBList = SideAandBDaoHelper.getInstance().queryByPath(path);
        for (SideAandB sb : sideAandBList) {
            String users = sb.getUsers();
            int add_scores = sb.getAdd_scores();
            int result = sb.getResult();
            int goals = sb.getGoals();
            int pannas = sb.getPannas();
            int fouls = sb.getFouls();
            int flagrant_fouls = sb.getFlagrant_fouls();
            int panna_ko = sb.getPanna_ko();
            int abstained = sb.getAbstained();
            String users_b = sb.getUsers_b();
            int add_scores_b = sb.getAdd_scores_b();
            int result_b = sb.getResult_b();
            int goals_b = sb.getGoals_b();
            int pannas_b = sb.getPannas_b();
            int fouls_b = sb.getFouls_b();
            int flagrant_fouls_b = sb.getFlagrant_fouls_b();
            int panna_ko_b = sb.getPanna_ko_b();
            int abstained_b = sb.getAbstained_b();
            String time = sb.getTime();
            int game_type = sb.getGame_type();
            try {
                JSONObject jb = new JSONObject(youKuURI);
                Side side_a = new Side(users, add_scores, result, goals, pannas, fouls, flagrant_fouls, panna_ko, abstained);
                Side side_b = new Side(users_b, add_scores_b, result_b, goals_b, pannas_b,
                        fouls_b, flagrant_fouls_b, panna_ko_b, abstained_b);
                long user_id = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
                long club_id = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_CLUB_ID, 0);
                long game_id = PreferenceManager.getDefaultSharedPreferences(this).getLong(ListActivity.PRE_CURRENT_GAME_ID, 0);
                String code = PreferenceManager.getDefaultSharedPreferences(this).getString(CaptureActivity.PRE_CURRENT_TWO_DIMENSION_CODE, null);
                doCommitData(club_id, user_id, game_id, code, game_type, jb.getString("video_id"), time, side_a, side_b);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    //    提交数据到服务器
    private void doCommitData(long club_id, long user_id, long game_id, String code, int game_type, String video_id, String time, Side side_a, Side side_b) {
        JSONObject jsonObject = new JSONObject();
        String uri = Constants.KTHOST+"offline/upload_battle";
        try {
            jsonObject.put("club_id", club_id);
            jsonObject.put("user_id", user_id);
            jsonObject.put("game_id", game_id);
            jsonObject.put("code", code);
            jsonObject.put("game_type", game_type - 1);
            jsonObject.put("youku_uri", video_id);
            jsonObject.put("time", time);

            JSONObject side_aa = new JSONObject();
            side_aa.put("users", side_a.users);
            side_aa.put("add_scores", side_a.add_scores);
            side_aa.put("result", side_a.result);
            side_aa.put("goals", side_a.goals);
            side_aa.put("pannas", side_a.pannas);
            side_aa.put("fouls", side_a.fouls);
            side_aa.put("flagrant_fouls", side_a.flagrant_fouls);
            side_aa.put("panna_ko", side_a.panna_ko);
            side_aa.put("abstained", side_a.abstained);

            JSONObject side_bb = new JSONObject();
            side_bb.put("users", side_b.users);
            side_bb.put("add_scores", side_b.add_scores);
            side_bb.put("result", side_b.result);
            side_bb.put("goals", side_b.goals);
            side_bb.put("pannas", side_b.pannas);
            side_bb.put("fouls", side_b.fouls);
            side_bb.put("flagrant_fouls", side_b.flagrant_fouls);
            side_bb.put("panna_ko", side_b.panna_ko);
            side_bb.put("abstained", side_b.abstained);

            jsonObject.put("side_a", side_aa);
            jsonObject.put("side_b", side_bb);

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
        startActivity(new Intent(this, UpHistoryActivity.class));
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
        List<VcrPath> list = VcrPathDaoHelper.getInstance().queryBySuccess(false);
        if (list == null || list.size() == 0) {
            errorCount.setVisibility(View.GONE);
        } else {
            errorCount.setText(list.size() + "");
            errorCount.setVisibility(View.VISIBLE);
        }
    }
}
