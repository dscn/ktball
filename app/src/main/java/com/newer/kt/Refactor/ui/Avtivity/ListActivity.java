package com.newer.kt.Refactor.ui.Avtivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.FileSizeUtil;
import com.frame.app.utils.LogUtils;
import com.ktfootball.www.dao.Bags;
import com.ktfootball.www.dao.Games;
import com.ktfootball.www.dao.Users;
import com.newer.kt.Business.ServiceLoadBusiness;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.adapter.BagsAdapter;
import com.newer.kt.adapter.GamesAdapter;
import com.newer.kt.adapter.UserAdapter;
import com.newer.kt.Refactor.db.BagsDaoHelper;
import com.newer.kt.Refactor.db.GamesDaoHelper;
import com.newer.kt.Refactor.db.UsersDaoHelper;
import com.newer.kt.entity.ClubDataCount;
import com.newer.kt.myClass.MyAlertDialog;
import com.newer.kt.myClass.MyDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

public class ListActivity extends BaseActivity {

    public static final String GAME_ID = "game_id";
    public static final String PRE_CURRENT_GAME_ID = "current_game_id";//提交比分时数据
    public static final String EXTRA_USER_CODE = "user_code";
    public static final String NO_UPDATA_USER = "未更新学生数： ";
    public static final int TO_USERINFOAVTIVITY = 1231;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.count_textView)
    TextView textViewCount;
    @Bind(R.id.count_textView_todo)
    TextView textViewCountToDo;
    @Bind(R.id.textView20)
    TextView textViewNewGame;
    @Bind(R.id.textView4)
    TextView textViewList;
    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @Bind(R.id.imageView89)
    ImageView imageViewScanUser;

    //适配器

    BagsAdapter bagsAdapter;
    GamesAdapter gamesAdapter;

    MyDialog myDialog;
    long clubId;
    private int code;
    private int dataSize;
    private int size;

    @Override
    protected void initHandler(Message msg) {
        switch (msg.what) {
            case ServiceLoadBusiness.GET_CLUB_DATA_COUNT_SUCCESS:
                ClubDataCount clubDataCount = (ClubDataCount) msg.obj;
                showDataToast(clubDataCount);
                break;
            case ServiceLoadBusiness.GET_CLUB_DATA_COUNT_FAILURE:
                LogUtils.e("GET_CLUB_DATA_COUNT_FAILURE");
                break;
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        clubId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_CLUB_ID, 0);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        code = intent.getIntExtra(ClubDataActivity.EXTRA_LIST_CODE, 0);
        switch (code) {
            case 1:
                //查询本地User数据
                List<Users> students = UsersDaoHelper.getInstance().getAllData();
                dataSize = students.size();
                if (new MyAlertDialog(this).isNetworkAvailable(this))
                    imageViewScanUser.setVisibility(View.VISIBLE);
                imageViewScanUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListActivity.this, CaptureActivity.class);
                        intent.putExtra(CaptureActivity.CAPTURE_CODE, 3);
                        startActivityForResult(intent, 3);
                    }
                });
                textViewCount.setVisibility(View.VISIBLE);
                textViewList.setText("学生列表");
                textViewCount.setText("目前学生数:" + students.size());
                textViewNewGame.setVisibility(View.GONE);
                final UserAdapter userAdapter = new UserAdapter(this, students);
                listView.setAdapter(userAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Users student = userAdapter.getItem(position);
                        Intent intent = new Intent(ListActivity.this, UserInfoActivity.class);
                        intent.putExtra(MyClassActivity.EXTRA_USER_ID, student.getUser_id());
                        intent.putExtra(EXTRA_USER_CODE, 1);
                        intent.putExtra(Constants.FORM_STUDENTLIST_STR, Constants.FORM_STUDENTLIST);
                        startActivityForResult(intent, TO_USERINFOAVTIVITY);
                    }
                });
                imageViewScanUser.setVisibility(View.VISIBLE);
                loadClubDataCount(clubId + "");
                break;
            case 2:
                //查询本地bags数据
                List<Bags> bagses = BagsDaoHelper.getInstance().getAllData();
                dataSize = bagses.size();
                textViewCount.setVisibility(View.VISIBLE);
                textViewList.setText("气场列表");
                textViewCount.setText("目前气场数:" + bagses.size());
                textViewNewGame.setVisibility(View.GONE);
                bagsAdapter = new BagsAdapter(this, bagses);
                listView.setAdapter(bagsAdapter);
                imageViewScanUser.setVisibility(View.GONE);
                loadClubDataCount(clubId + "");
                break;
            case 3:
                //查询本地game数据
                final List<Games> games = GamesDaoHelper.getInstance().getAllData();
                dataSize = games.size();
                imageViewScanUser.setVisibility(View.GONE);
                textViewCount.setVisibility(View.VISIBLE);
                textViewList.setText("赛事列表");
                textViewCount.setText("目前赛事数:" + games.size());
                textViewNewGame.setVisibility(View.VISIBLE);
                textViewNewGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyAlertDialog myAlertDialog = new MyAlertDialog(ListActivity.this);
                        if (myAlertDialog.isNetworkAvailable(ListActivity.this)) {
                            startActivity(new Intent(ListActivity.this, NewGamesActivity.class));
                        }
                    }
                });
                gamesAdapter = new GamesAdapter(this, games);
                listView.setAdapter(gamesAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent1 = new Intent(ListActivity.this, GamesDetailsActivity.class);
                        intent1.putExtra(GAME_ID, games.get(position).getGame_id());
                        startActivity(intent1);
                    }
                });
                loadClubDataCount(clubId + "");
                break;
            case 4:
                showDialogToast("当前设备储存剩余：" + FileSizeUtil.formatFileSize(FileSizeUtil.getAvailableInternalMemorySize(), false), "前往清理", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
                        startActivity(intent);
                    }
                }, "知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                //本地查询比赛地点
                long nowTime = System.currentTimeMillis();
                List<Games> games1 = GamesDaoHelper.getInstance().getAllData();
                List<Games> games2 = new ArrayList<>();
                Games game = null;
                for (int x = 0; x < games1.size(); x++) {
                    game = games1.get(x);
                    String start_time = game.getTime_start();
                    String end_time = game.getTime_end();
                    long startTime = parseStringToDate(start_time, "yyyy-MM-dd'T'HH:mm:ss.sss");
                    long endTime = parseStringToDate(end_time, "yyyy-MM-dd'T'HH:mm:ss.sss");
                    if (startTime < nowTime && nowTime < endTime) {
                        games2.add(game);
                    }
                }
                imageViewScanUser.setVisibility(View.GONE);
                textViewCount.setVisibility(View.GONE);
                gamesAdapter = new GamesAdapter(this, games2);
                listView.setAdapter(gamesAdapter);
                textViewList.setText("比赛地点");
                relativeLayout.setBackgroundResource(R.drawable.title_bg);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Games games2 = gamesAdapter.getItem(position);
                        PreferenceManager.getDefaultSharedPreferences(ListActivity.this).edit().putLong(PRE_CURRENT_GAME_ID, Long.parseLong(games2.getGame_id())).commit();
                        startActivity(new Intent(ListActivity.this, BagsScannerActivity.class));
                    }
                });
                break;
        }
        listView.setDivider(new ColorDrawable(Color.WHITE));
        listView.setDividerHeight(1);
    }

    //把String时间格式转换成long类型
    public static long parseStringToDate(String time, String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long t = date.getTime();
        return t;
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

    private void showDataToast(ClubDataCount clubDataCount) {
        int bagsNum = clubDataCount.bag_count;
        LogUtils.e("bagsNum = " + bagsNum);
        int userNum = clubDataCount.user_count;
        LogUtils.e("userNum = " + userNum);
        int gameNum = clubDataCount.game_count;
        LogUtils.e("gameNum = " + gameNum);
        String str = "";
        size = 0;
        switch (code) {
            case 1:
                str = NO_UPDATA_USER;
                size = userNum - dataSize;
                break;
            case 2:
                str = "未更新气场数： ";
                size = bagsNum - dataSize;
                break;
            case 3:
                str = "未更新赛事数： ";
                size = gameNum - dataSize;
                break;
        }
        if(size != 0){
            setNoUpdataText(str, size);
        }
    }

    private void setNoUpdataText(String string, int size) {
        if (textViewCountToDo != null) {
            textViewCountToDo.setText(string + size);
        }
    }

    //退出当前Activity
    public void doBack(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("onActivityResult");
        if (requestCode == 3) {
            LogUtils.e("requestCode == 3");
            if (resultCode == 3) {
                myDialog = new MyDialog(this, "正在查询");
                myDialog.show();
                Bundle bundle = data.getBundleExtra("bundle");
                final String result = bundle.getString(CaptureActivity.BUNDLE_SCAN_RESULT);
                Intent intent = new Intent(ListActivity.this, UserInfoActivity.class);
                intent.putExtra(MyClassActivity.EXTRA_USER_ID, result);
                intent.putExtra(EXTRA_USER_CODE, 1);
                myDialog.dismiss();
                startActivity(intent);
            }
        }
        if(requestCode == TO_USERINFOAVTIVITY && resultCode == UserInfoActivity.UPDATA_CODE){
            LogUtils.e("UserInfoActivity.UPDATA_CODE");
            textViewCount.setVisibility(View.VISIBLE);
            size += 1;
            setNoUpdataText(NO_UPDATA_USER,size);
        }
    }
}
