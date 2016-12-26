package com.newer.kt.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ktfootball.www.dao.SideAandB;
import com.ktfootball.www.dao.VcrPath;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.ui.Avtivity.CaptureActivity;
import com.newer.kt.Refactor.ui.Avtivity.ListActivity;
import com.newer.kt.Refactor.ui.Avtivity.LoginActivity;
import com.newer.kt.Refactor.db.SideAandBDaoHelper;
import com.newer.kt.Refactor.db.VcrPathDaoHelper;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.entity.Side;
import com.newer.kt.Refactor.Entitiy.Token;
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

public class UploadService extends Service {

    private String access_token;
    private String CLIENT_ID = "5570b4b24049e570";
    private String CLIENT_SECRET = "c40bcc367334ef63e42ef4562b460e7f";
    private YoukuUploader uploader;

    ArrayList<String> list = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        uploader = YoukuUploader.getInstance(CLIENT_ID, CLIENT_SECRET, getApplicationContext());

        List<SideAandB> adList = SideAandBDaoHelper.getInstance().getAllData();
        for (SideAandB s : adList) {
            String path = s.getPath();
            File file = new File(path);
            if (file.exists()) {
                list.add(path);
            }
        }
        new MyThered(0).start();
    }

    class MyThered extends Thread {

        int current = 0;

        public MyThered(int current) {
            this.current = current;
            Log.d("aaaaaaaaaa", "aaaaaaaaaaa");
        }

        @Override
        public void run() {
            initData();
        }

        private void initData() {
            long id = PreferenceManager.getDefaultSharedPreferences(UploadService.this).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
            String club_id = String.valueOf(id);
            String url = Constants.KTHOST + "users/get_role?user_id=" +
                    club_id + "&authenticity_token=" + MD5.getToken(Constants.KTHOST + "users/get_role");
            JsonRequest jsonRequest = new JsonObjectRequest(url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Token token = new Gson().fromJson(jsonObject.toString(), new TypeToken<Token>() {
                            }.getType());
                            access_token = token.youku_token;
                            Log.d("access_token", access_token);

                            doUp(list.get(current));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    return headers;
                }
            };
            VolleyUtil.getInstance(getApplicationContext()).addRequest(jsonRequest);
        }

        public void doUp(final String s) {

            HashMap<String, String> params = new HashMap<>();
            params.put("access_token", access_token);
            HashMap<String, String> uploadInfo = new HashMap<>();
            uploadInfo.put("title", s.substring(s.indexOf("球") + 2, s.length()));
            uploadInfo.put("tags", "优酷,EVA");
            uploadInfo.put("file_name", s);
            uploader.upload(params, uploadInfo, new IUploadResponseHandler() {

                @Override
                public void onStart() {
                    Log.d("Mainupload", "onStart");
                }

                @Override
                public void onSuccess(JSONObject response) {
                    Log.d("MainuploadonSuccess", response.toString());
//                Message msg = Message.obtain();
//                msg.what = 123;
//                msg.obj = new String[]{s, response.toString()};
//                handler.sendMessage(msg);

                    queryByPath(s, response.toString());
                    List<SideAandB> sideAandBsList = SideAandBDaoHelper.getInstance().queryByPath(s);
                    for (SideAandB ab : sideAandBsList) {
                        doSaveOk(ab);
                    }
                    Intent i = new Intent("shangchuanshipin");
                    i.putExtra("s", s);
                    i.putExtra("response", response.toString());
                    sendBroadcast(i);

                    if (++current < list.size()) {
//                        doUp(list.get(current));
                        new MyThered(current).start();
                    }

                }

                @Override
                public void onProgressUpdate(int counter) {
                    Log.d("MainuploadonProgress", counter + "");

                }

                @Override
                public void onFailure(JSONObject errorResponse) {
                    Log.d("onFailureJsonObject", errorResponse.toString());
//                Message msg = Message.obtain();
//                msg.what = 456;
//                msg.obj = new String[]{s};
//                if (handler!=null)handler.sendMessage(msg);

                    Intent i = new Intent("shangchuanshibai");
                    i.putExtra("s", s);
                    sendBroadcast(i);
                }

                @Override
                public void onFinished() {
                    Log.d("Mainupload", "onFinished");
                }
            });
        }

        private void queryByPath(String path, String youKuURI) {
//            Cursor cursor = getContentResolver().query(ClubDataProvider.DATA_URI, SideAandB._ALL, "path=?", new String[]{path}, null);
            List<SideAandB> queryList = SideAandBDaoHelper.getInstance().queryByPath(path);
            for (SideAandB ab : queryList) {
                String users = ab.getUsers();
                int add_scores = ab.getAdd_scores();
                int result = ab.getResult();
                int goals = ab.getGoals();
                int pannas = ab.getPannas();
                int fouls = ab.getFouls();
                int flagrant_fouls = ab.getFlagrant_fouls();
                int panna_ko = ab.getPanna_ko();
                int abstained = ab.getAbstained();
                String users_b = ab.getUsers_b();
                int add_scores_b = ab.getAdd_scores_b();
                int result_b = ab.getResult_b();
                int goals_b = ab.getGoals_b();
                int pannas_b = ab.getPannas_b();
                int fouls_b = ab.getFouls_b();
                int flagrant_fouls_b = ab.getFlagrant_fouls_b();
                int panna_ko_b = ab.getPanna_ko_b();
                int abstained_b = ab.getAbstained_b();
                String time = ab.getTime();
                int game_type = ab.getGame_type();
                try {
                    JSONObject jb = new JSONObject(youKuURI);
                    Side side_a = new Side(users, add_scores, result, goals, pannas, fouls, flagrant_fouls, panna_ko, abstained);
                    Side side_b = new Side(users_b, add_scores_b, result_b, goals_b, pannas_b,
                            fouls_b, flagrant_fouls_b, panna_ko_b, abstained_b);
                    long user_id = PreferenceManager.getDefaultSharedPreferences(UploadService.this).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
                    long club_id = PreferenceManager.getDefaultSharedPreferences(UploadService.this).getLong(LoginActivity.PRE_CURRENT_CLUB_ID, 0);
                    long game_id = PreferenceManager.getDefaultSharedPreferences(UploadService.this).getLong(ListActivity.PRE_CURRENT_GAME_ID, 0);
                    String code = PreferenceManager.getDefaultSharedPreferences(UploadService.this).getString(CaptureActivity.PRE_CURRENT_TWO_DIMENSION_CODE, null);
                    doCommitData(club_id, user_id, game_id, code, game_type, jb.getString("video_id"), time, side_a, side_b);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        //提交数据到服务器
        private void doCommitData(long club_id, long user_id, long game_id, String code, int game_type, String video_id, String time, Side side_a, Side side_b) {
            JSONObject jsonObject = new JSONObject();
            String uri = Constants.KTHOST + "offline/upload_battle";
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
            JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    uri,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            }

            );

            VolleyUtil.getInstance(UploadService.this).addRequest(jsonRequest);
        }

        private void doSaveOk(SideAandB sb) {//上传成功
            VcrPathDaoHelper.getInstance().addData(initVcrPath(sb, true));
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

    }

    public class MyBind extends Binder {
        public UploadService getService() {
            return UploadService.this;
        }
    }
}
