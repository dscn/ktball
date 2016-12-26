package com.newer.kt.Refactor.ui.Avtivity.RankingList;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.GsonTools;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.ui.Fragment.RankingList.FightingPowerFragment;
import com.newer.kt.Refactor.ui.Fragment.RankingList.ScoreFragment;
import com.newer.kt.entity.AddClassData;
import com.newer.kt.entity.ClassData;

import java.util.List;

/**
 * Created by jy on 16/8/23.
 */
public class RankingListActivity extends BaseActivity {

    private FragmentTransaction transaction;
    private FightingPowerFragment fragment1;
    private ScoreFragment fragment2;
    private ImageView img_2;
    private ImageView img_1;
    private TextView tv_1;
    private TextView tv_2;
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private ImageView back;
    private LinearLayout select;
    private TextView grend;
    private TextView cls;

    private int gradeid;
    private int clsid;
    private List<ClassData> grade_list;

    String[] nianJi = {"", "小班", "中班", "大班", "一年级", "二年级", "三年级", "四年级",
            "五年级", "六年级", "初一", "初二", "初三"};

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        // 选择支持半透明模式,在有surfaceview的activity中使用。
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.layout_rankinglist);
        img_2 = (ImageView) findViewById(R.id.layout_rankinglist_2_img);
        img_1 = (ImageView) findViewById(R.id.layout_rankinglist_1_img);
        back = (ImageView) findViewById(R.id.icon_ranking_back);
        tv_1 = (TextView) findViewById(R.id.layout_rankinglist_1_tv);
        tv_2 = (TextView) findViewById(R.id.layout_rankinglist_2_tv);
        ll_1 = (LinearLayout) findViewById(R.id.layout_rankinglist_1_ll);
        ll_2 = (LinearLayout) findViewById(R.id.layout_rankinglist_2_ll);
        select = (LinearLayout) findViewById(R.id.icon_ranking_nianji_select);
        grend = (TextView) findViewById(R.id.icon_ranking_nianji_grend);
        cls = (TextView) findViewById(R.id.icon_ranking_nianji_cls);
    }

    @Override
    protected void setListener() {
        ll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fightingPower();
            }
        });
        ll_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        grend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addclassdata = KTApplication.getClassDetailsInfo();
                final AddClassData addClassData = GsonTools.changeGsonToBean(addclassdata, AddClassData.class);
                final String[] name = new String[addClassData.grade_list.size()+1];
                name[0] = "全部年级";
                for (int x = 0; x < addClassData.grade_list.size(); x++) {
                    name[x+1] = nianJi[addClassData.grade_list.get(x).grade];
                }
                new AlertDialog.Builder(getThis()).setTitle("选择年级")
                        .setItems(name, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                grend.setText(name[which]);
                                if(which ==0){
                                    fragment1 = new FightingPowerFragment();
                                    cls.setVisibility(View.GONE);
                                }else{
                                    gradeid = addClassData.grade_list.get(which-1).grade;
                                    grade_list = addClassData.grade_list.get(which-1).classes;
                                    cls.setVisibility(View.VISIBLE);
                                    cls.setText("选择班级");
                                    fragment1 = new FightingPowerFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(Constants.GRADEID, gradeid);
                                    fragment1.setArguments(bundle);
                                }
                                fightingPower();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });
        cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] name = new String[grade_list.size()+1];
                name[0] = "全部班级";
                for (int x = 0; x < grade_list.size(); x++) {
                    name[x+1] = grade_list.get(x).cls+"班";
                }
                new AlertDialog.Builder(getThis()).setTitle("选择班级")
                        .setItems(name, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                cls.setText(name[which]);
                                if(which ==0){
                                    fragment1 = new FightingPowerFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(Constants.GRADEID, gradeid);
                                    fragment1.setArguments(bundle);
                                }else{
                                    clsid = grade_list.get(which-1).cls;
                                    fragment1 = new FightingPowerFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(Constants.GRADEID, gradeid);
                                    bundle.putInt(Constants.CLSID, clsid);
                                    fragment1.setArguments(bundle);
                                }
                                fightingPower();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fragment1 = new FightingPowerFragment();
        fragment2 = new ScoreFragment();
        fightingPower();
    }

    private void changeFragment(Fragment fragment) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void fightingPower() {
        tv_1.setTextColor(Color.parseColor("#feee35"));
        tv_2.setTextColor(Color.parseColor("#f9c143"));
        img_1.setImageDrawable(getResources().getDrawable(R.drawable.icon_1_yes));
        img_2.setImageDrawable(getResources().getDrawable(R.drawable.icon_2_no));
        ll_1.setBackground(getResources().getDrawable(R.drawable.bg_rankinglist_yes));
        ll_2.setBackground(getResources().getDrawable(R.drawable.bg_rankinglist_no));
        select.setVisibility(View.VISIBLE);
        changeFragment(fragment1);
    }

    private void score() {
        tv_2.setTextColor(Color.parseColor("#feee35"));
        tv_1.setTextColor(Color.parseColor("#f9c143"));
        img_1.setImageDrawable(getResources().getDrawable(R.drawable.icon_1_no));
        img_2.setImageDrawable(getResources().getDrawable(R.drawable.icon_2_yes));
        ll_1.setBackground(getResources().getDrawable(R.drawable.bg_rankinglist_no));
        ll_2.setBackground(getResources().getDrawable(R.drawable.bg_rankinglist_yes));
        select.setVisibility(View.GONE);
        changeFragment(fragment2);
    }
}
