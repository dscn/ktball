package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.os.Environment;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.ktfootball.www.dao.Games;
import com.newer.kt.Business.BitmapManager;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.R;
import com.newer.kt.Refactor.db.GamesDaoHelper;
import com.newer.kt.myClass.MyAlertDialog;

import java.io.File;
import java.util.List;

import butterknife.Bind;

public class GamesDetailsActivity extends BaseActivity {

    @Bind(R.id.textView24)
    TextView gameName;
    @Bind(R.id.imageView36)
    ImageView avatar;
    @Bind(R.id.textView26)
    TextView time;
    @Bind(R.id.textView28)
    TextView place;
    @Bind(R.id.textView30)
    TextView introduction;
    @Bind(R.id.imageView34)
    ImageView imageViewEdit;
    File p;//根目录
    MyAlertDialog myAlertDialog;
    String id;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_games_details);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        p = Environment.getExternalStorageDirectory();
        myAlertDialog = new MyAlertDialog(this);
        initView();
    }

    private void initView() {
        if (myAlertDialog.isNetworkAvailable(this)){
            imageViewEdit.setVisibility(View.VISIBLE);
        } else {
            imageViewEdit.setVisibility(View.GONE);
        }
        Intent intent = getIntent();
        id = intent.getStringExtra(ListActivity.GAME_ID);
        Games games = null;
        List<Games> list = GamesDaoHelper.getInstance().queryByGameId(id + "");
        for(Games g : list){
            games = g;
        }
        if(games != null){
            gameName.setText(games.getName());
            String s = games.getTime_start().substring(2, games.getTime_start().lastIndexOf("T"))
                    + " - " + games.getTime_end().substring(2,games.getTime_end().lastIndexOf("T"));
            time.append(s);
            place.append(games.getPlace());
            introduction.append(games.getIntroduction() != null && !games.getIntroduction().isEmpty() ? games.getIntroduction() : "暂无介绍");
            String path = Constants.Head_HOST + games.getAvatar();
            LogUtils.e(path);
//            Glide.with(this).load(new File(path)).into(avatar);
            BitmapManager.getInstance().display(avatar,
                    path);
        }else{
            LogUtils.e("games = null");
        }
//        String path = p.toString() + "/KT足球/" + String.valueOf(id) + ".png";
    }

    public void doBack(View view) {
        finish();
    }

    public void doEdit(View view) {//编辑
        Intent intent = new Intent(this,EditGameActivity.class);
        intent.putExtra(ListActivity.GAME_ID,id);
        startActivity(intent);
    }
}
