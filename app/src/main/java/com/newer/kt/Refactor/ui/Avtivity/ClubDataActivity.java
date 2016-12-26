package com.newer.kt.Refactor.ui.Avtivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.SharedPreferencesUtils;
import com.newer.kt.Business.ServiceLoadBusiness;
import com.newer.kt.R;
import com.newer.kt.Refactor.Entitiy.ServiceDataResult;
import com.newer.kt.Refactor.db.BagsDaoHelper;
import com.newer.kt.Refactor.db.GamesDaoHelper;
import com.newer.kt.Refactor.db.UsersDaoHelper;
import com.newer.kt.entity.AddClassData;
import com.newer.kt.entity.ClubDataCount;
import com.newer.kt.myClass.MyAlertDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;

public class ClubDataActivity extends BaseActivity {

    public static final String EXTRA_LIST_CODE = "list_code";
    public static final String UPDATA_TIME = "updata_time";
    public final String ADD_CLASS_DATA_NAME = "addclassdata";
    public final int PROGRESSBAR_FINISH = 11;

    @Bind(R.id.xiaoyuan_textView)
    TextView textViewXiaoYuan;
    @Bind(R.id.caipan_textView)
    TextView textViewCaiPan;
    @Bind(R.id.xiaoyuan_imageView)
    ImageView imageViewXiaoYuan;
    @Bind(R.id.caipan_imageView)
    ImageView imageViewCaiPan;
    @Bind(R.id.XiaoYuanRelativeLayout)
    RelativeLayout relativeLayoutXiaoYuan;
    @Bind(R.id.CaiPan_RelativeLayout)
    RelativeLayout relativeLayoutCaiPan;
    @Bind(R.id.club_name_textView)
    TextView textViewClubName;
    @Bind(R.id.textView10)
    TextView textViewUserCount;
    @Bind(R.id.textView100)
    TextView textViewBagCount;
    @Bind(R.id.textView110)
    TextView textViewGameCount;
    @Bind(R.id.textView12)
    TextView textViewHint;
    @Bind(R.id.relativeLayout_hint)
    RelativeLayout relativeLayoutHint;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.button)
    Button imageViewUpdataButton;
    @Bind(R.id.textView11)
    TextView textViewTime;
    @Bind(R.id.textView1100)
    TextView textViewClass;

    @Bind(R.id.relativeLayout8)
    RelativeLayout relativeLayout1;
    @Bind(R.id.relativeLayout9)
    RelativeLayout relativeLayout2;
    @Bind(R.id.relativeLayout100)
    RelativeLayout relativeLayout3;

    Message message;
    private int progressBarNum = 0;
    AddClassData addClassData;
    ArrayList<String> avatarList;
    String club_id;
    MyAlertDialog myAlertDialog;
    File path;

    //获取服务器数据数量变量
    long graderId;
    long bagsId;
    long userId;
    long gameId;
    //服务器数据总量
    long oldDataCount;

    //查询服务器数据数量变量
    private int graderNum;
    private int gameNum;
    private int userNum;
    private int bagsNum;
    //查询服务器数据总量
    long dataCount;

    @Override
    protected void initHandler(Message msg) {
        switch (msg.what) {
            case ServiceLoadBusiness.GET_CLUB_DATA_SUCCESS:
                ServiceDataResult serviceDataResult = (ServiceDataResult) msg.obj;
                doDeleteData();
                doLocalSave(serviceDataResult);
                loadAddClassData(club_id);
                setProgressNum();
                break;
            case ServiceLoadBusiness.GET_CLUB_DATA_FAILURE:
                String msgE = (String) msg.obj;
                showUpdataButton();
                break;
            case ServiceLoadBusiness.GET_CLUB_SCHOOL_CLASS_DATA_SUCCESS:
                String data = (String) msg.obj;
                SharedPreferencesUtils.saveString(getThis(), ADD_CLASS_DATA_NAME, data);
                graderId = getClassCount(data);
                setProgressNum();
                break;
            case ServiceLoadBusiness.GET_CLUB_DATA_COUNT_SUCCESS:
                ClubDataCount clubDataCount = (ClubDataCount) msg.obj;
                showDataToast(clubDataCount);
                break;
            case ServiceLoadBusiness.GET_CLUB_DATA_COUNT_FAILURE:
                LogUtils.e("GET_CLUB_DATA_COUNT_FAILURE");
                break;
            case PROGRESSBAR_FINISH:
                updateFinish();
                break;
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_club_data);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        avatarList = new ArrayList<>();
        message = Message.obtain();

        myAlertDialog = new MyAlertDialog(this);

        //设置club_name,updata_time
        String club_name = PreferenceManager.getDefaultSharedPreferences(this).getString(LoginActivity.PRE_CURRENT_CLUB_NAME, null);
        textViewClubName.setText(club_name);

        String upTime = PreferenceManager.getDefaultSharedPreferences(this).getString(UPDATA_TIME, null);
        if (upTime != null) textViewTime.setText(upTime);

        long id = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_CLUB_ID, 0);
        club_id = String.valueOf(id);
        path = Environment.getExternalStorageDirectory();
    }

    @Override
    protected void onStart() {
        super.onStart();
        doQuery();//本地查询
        doCount();//club数据计算
    }

    /**
     * 本地查询
     */
    private void doQuery() {

        long bags = BagsDaoHelper.getInstance().getTotalCount();

        long users = UsersDaoHelper.getInstance().getTotalCount();

        long games = GamesDaoHelper.getInstance().getTotalCount();

        long claCount = 0;
        String addclassdata = SharedPreferencesUtils.getString(getThis(), ADD_CLASS_DATA_NAME, "");
        if (!"".equals(addclassdata)) {
            LogUtils.e(addclassdata + "");

            claCount = getClassCount(addclassdata);
            textViewClass.setText("" + claCount);
        } else {
            textViewClass.setText("暂无数据");
        }

        if (bags == 0) {
            textViewBagCount.setText("暂无数据");
        } else {
            textViewBagCount.setText(String.valueOf(bags));
        }

        if (users == 0) {
            textViewUserCount.setText("暂无数据");
        } else {
            textViewUserCount.setText(String.valueOf(users));
        }

        if (games == 0) {
            textViewGameCount.setText("暂无数据");
        } else {
            textViewGameCount.setText(String.valueOf(games));
        }

        oldDataCount = games + users + bags + claCount;
        LogUtils.e(oldDataCount + "");
    }

    /**
     * 根据json数据获得班级数量
     * @param data  服务器返回数据
     * @return
     */
    private int getClassCount(String data) {
        addClassData = GsonTools.changeGsonToBean(data, AddClassData.class);
        int k = 0;
        if (addClassData != null) {
            for (int i = 0; i < addClassData.grade_list.size(); i++) {
                k += addClassData.grade_list.get(i).classes.size();
            }
            LogUtils.e(k + "");
        }
        return k;
    }

    /**
     * 获取俱乐部数据计数
     */
    private void doCount() {//获取学生，气场，赛事计数
//        loadClassCount(club_id);
        loadClubDataCount(club_id);
    }

    /**
     * 点击更新数据
     * @param view
     */
    public void doUpdata(View view) {
        if (!myAlertDialog.isNetworkAvailable(ClubDataActivity.this)) {
            myAlertDialog.doAlertDialog("当前没有可用网络");
        } else {
            doGetData();
        }
    }

    private void showDataToast(ClubDataCount clubDataCount) {
        bagsNum = clubDataCount.bag_count;
        LogUtils.e("bagsNum = "+ bagsNum);
        userNum = clubDataCount.user_count;
        LogUtils.e("userNum = " + userNum);
        gameNum = clubDataCount.game_count;
        LogUtils.e("gameNum = " + gameNum);
        graderNum = clubDataCount.school_class_count;
        LogUtils.e("graderNum = " + graderNum);

        dataCount = bagsNum + userNum + gameNum + graderNum;
        LogUtils.e("oldDataCount = " + oldDataCount + ".....dataCount=" + dataCount);
        if (dataCount > oldDataCount) {
            relativeLayoutHint.setVisibility(View.VISIBLE);
            textViewHint.setText(Math.abs(dataCount - oldDataCount) + "条数据更新");
            LogUtils.e("" + Math.abs(dataCount - oldDataCount));
        } else {
            relativeLayoutHint.setVisibility(View.GONE);
        }
    }

    /**
     * 从服务器取数据
     */
    private void doGetData() {
        relativeLayoutHint.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        imageViewUpdataButton.setVisibility(View.GONE);
        relativeLayout1.setEnabled(false);
        relativeLayout2.setEnabled(false);
        relativeLayout3.setEnabled(false);
        //获取俱乐部数据
        loadServiceData(club_id);
    }

    /**
     * 获取俱乐部数据
     *
     * @param club_id 俱乐部ID
     */
    private void loadServiceData(String club_id) {
        ServiceLoadBusiness.getInstance().getClubData(getThis(), club_id);
    }

    /**
     * 获得学生数、气场数、赛事数数量
     *
     * @param club_id
     */
    private void loadClubDataCount(String club_id) {
        LogUtils.e("获得学生数、气场数、赛事数数量");
        ServiceLoadBusiness.getInstance().getClubDataCount(getThis(), club_id);
    }

    /**
     * 获取添加班级数据
     *
     * @param club_id 俱乐部ID
     */
    private void loadAddClassData(String club_id) {
        ServiceLoadBusiness.getInstance().getClubSchoolClassData(getThis(), club_id);
    }

    /**
     * 删除数据库数据
     */
    private void doDeleteData() {
        BagsDaoHelper.getInstance().deleteAll();
        //6.存储赛事数据
        GamesDaoHelper.getInstance().deleteAll();
        //5.存储学生数据
        UsersDaoHelper.getInstance().deleteAll();
    }

    /**
     * 本地数据存储
     *
     * @param result
     */
    private void doLocalSave(ServiceDataResult result) {
        //4.存储气场数据
        BagsDaoHelper.getInstance().addListData(result.bags);
        bagsId = BagsDaoHelper.getInstance().getTotalCount();
        //6.存储赛事数据
        GamesDaoHelper.getInstance().addListData(result.games);
        gameId = GamesDaoHelper.getInstance().getTotalCount();
        //5.存储学生数据
        UsersDaoHelper.getInstance().addListData(result.users);
        userId = UsersDaoHelper.getInstance().getTotalCount();
        LogUtils.e(BagsDaoHelper.getInstance().getTotalCount() + "..." + UsersDaoHelper.getInstance().getTotalCount() + "..." + GamesDaoHelper.getInstance().getTotalCount() + "");
    }

    /**
     * 退出登录dialog
     *
     * @param view
     */
    public void doQuit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认退出");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferenceManager.getDefaultSharedPreferences(ClubDataActivity.this)
                        .edit()
                        .putLong(LoginActivity.PRE_CURRENT_USER_ID, 0)
                        .commit();
                startActivity(new Intent(ClubDataActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 校园系统
     *
     * @param view
     */
    public void doXiaoyuan(View view) {
        textViewXiaoYuan.setTextColor(this.getResources().getColor(R.color.colorYellow));
        textViewCaiPan.setTextColor(this.getResources().getColor(R.color.colorHui));
        relativeLayoutXiaoYuan.setVisibility(View.VISIBLE);
        relativeLayoutCaiPan.setVisibility(View.GONE);
        imageViewXiaoYuan.setImageResource(R.drawable.xiaoyuanxinxi_b);
        imageViewCaiPan.setImageResource(R.drawable.caipanxintong_a);
    }

    /**
     * 裁判系统
     *
     * @param view
     */
    public void doCaipan(View view) {
        textViewXiaoYuan.setTextColor(this.getResources().getColor(R.color.colorHui));
        textViewCaiPan.setTextColor(this.getResources().getColor(R.color.colorYellow));
        relativeLayoutXiaoYuan.setVisibility(View.GONE);
        relativeLayoutCaiPan.setVisibility(View.VISIBLE);
        imageViewXiaoYuan.setImageResource(R.drawable.xiaoyuanxinxi_a);
        imageViewCaiPan.setImageResource(R.drawable.caipanxintong_b);
    }

    /**
     * 点击学生数
     * @param view
     */
    public void doStudent(View view) {
        int code = 1;
        doStartActivity(code);
    }

    /**
     * 点击气场数
     * @param view
     */
    public void doBags(View view) {
        int code = 2;
        doStartActivity(code);
    }

    /**
     * 点击赛事数
     * @param view
     */
    public void doGames(View view) {
        int code = 3;
        doStartActivity(code);
    }

    /**
     * 点击进入学生or气场or赛事列表页面
     * @param code
     */
    private void doStartActivity(int code) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(EXTRA_LIST_CODE, code);
        startActivity(intent);
    }


    /**
     * 点击进入班级数
     * @param view
     */
    public void doClass(View view) {//班级数
        Intent intent = new Intent(this, AddClassActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bundle", addClassData);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 设置进度条进度
     */
    private synchronized void setProgressNum() {
        ++progressBarNum;
        LogUtils.e(progressBarNum + "");
        if (progressBarNum == 2) {

            new Thread(new Runnable() {
                int x = 0;

                @Override
                public void run() {
                    while (x < 10) {
                        ++x;
                        progressBar.setProgress(++x);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    getHandler().sendEmptyMessage(PROGRESSBAR_FINISH);
                }
            }).start();
        }
    }

    /**
     * 更新完成调用方法
     */
    private void updateFinish() {
        doQuery();
        showUpdataButton();
        myAlertDialog.doAlertDialog("更新完成");
        //更新club数据的计数
        textViewUserCount.setText(String.valueOf(userId));
        textViewBagCount.setText(String.valueOf(bagsId));
        textViewGameCount.setText(String.valueOf(gameId));
        textViewClass.setText(String.valueOf(graderId));

        //存储上次更新的时间，获取当前的时间
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int sec = c.get(Calendar.SECOND);

        String t = String.format("上一次更新时间:%04d年%02d月%02d日 %02d:%02d:%02d", year, month, day, hour, minute, sec);

        PreferenceManager.getDefaultSharedPreferences(ClubDataActivity.this).edit()
                .putString(UPDATA_TIME, t).commit();
        textViewTime.setVisibility(View.VISIBLE);
        textViewTime.setText(t);
    }

    /**
     * 进度条变为更新按钮
     */
    private void showUpdataButton() {
        progressBar.setVisibility(View.GONE);
        progressBarNum = 0;
        progressBar.setProgress(0);
        imageViewUpdataButton.setVisibility(View.VISIBLE);
        relativeLayout1.setEnabled(true);
        relativeLayout2.setEnabled(true);
        relativeLayout3.setEnabled(true);
    }

//=================================================================================================

    //人工执裁
    public void doJudgment(View view) {
        doStartActivity(4);
    }

    //视频上传
    public void doUploading(View view) {
        startActivity(new Intent(this, VideoActivity.class));
    }
}
