package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.www.dao.Games;
import com.newer.kt.R;
import com.newer.kt.Refactor.db.GamesDaoHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

public class GameResultActivity extends BaseActivity {

    @Bind(R.id.textView60)
    TextView textViewLeftGrade;//左分
    @Bind(R.id.textView61)
    TextView textViewRightGrade;//右分
    @Bind(R.id.imageView78)
    ImageView imageViewLeftKT;//左KT
    @Bind(R.id.imageView79)
    ImageView imageViewRightKT;//右KT
    @Bind(R.id.imageView76)
    ImageView imageViewLeftWin;//左赢
    @Bind(R.id.imageView77)
    ImageView imageViewRightWin;//右赢
    @Bind(R.id.textView62)
    TextView textViewLeftUser;//左用户
    @Bind(R.id.textView63)
    TextView textViewRightUser;//右用户
    @Bind(R.id.textView65)
    TextView textViewShang;//上地址
    @Bind(R.id.textView64)
    TextView textViewZhong;//中时间
    @Bind(R.id.textView66)
    TextView textViewXia;//下地址

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_game_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        int ktCode = intent.getIntExtra(UploadingActivity.KT_CODE, 0);
        String leftUser = intent.getStringExtra(UploadingActivity.LEFT_USER);
        String rightUser = intent.getStringExtra(UploadingActivity.RIGHT_USER);
        int leftGrade = intent.getIntExtra(UploadingActivity.LEFT_GRADE, 0);
        int rightGrade = intent.getIntExtra(UploadingActivity.RIGHT_GRADE,0);
        textViewLeftGrade.setText(String.valueOf(leftGrade));
        textViewRightGrade.setText(String.valueOf(rightGrade));
        switch (ktCode){
            case 1:
                imageViewLeftKT.setVisibility(View.VISIBLE);
                imageViewLeftWin.setVisibility(View.VISIBLE);
                break;
            case 2:
                imageViewRightKT.setVisibility(View.VISIBLE);
                imageViewRightWin.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        textViewLeftUser.setText(leftUser);
        textViewRightUser.setText(rightUser);
        long id = PreferenceManager.getDefaultSharedPreferences(this).getLong(ListActivity.PRE_CURRENT_GAME_ID, 0);
        Games games = null;
        List<Games> list = GamesDaoHelper.getInstance().queryByGameId(id + "");
        if(list.size()>0){
            games = list.get(0);
        }
        String gamePlace = games.getName();
        textViewShang.setText(gamePlace);
        textViewXia.setText(gamePlace);
        textViewZhong.setText(refFormatNowDate());
    }

    public String refFormatNowDate() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("MM-dd HH:mm");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }

    public void doBack(View view) {
        finish();
    }
}
