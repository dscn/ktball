package com.newer.kt.Refactor.ui.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.utils.PhoneUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.db.BagsDaoHelper;
import com.newer.kt.Refactor.ui.Avtivity.ListActivity;
import com.newer.kt.Refactor.ui.Avtivity.LoginActivity;
import com.newer.kt.Refactor.ui.Avtivity.Settings.AboutKTActivity;
import com.newer.kt.Refactor.ui.Avtivity.Settings.CampusStatisticsActivity;
import com.newer.kt.Refactor.ui.Avtivity.Settings.HelpActivity;
import com.newer.kt.Refactor.ui.Avtivity.Settings.MineSchoolInfoActivity;
import com.newer.kt.Refactor.ui.Avtivity.Settings.SchoolEquipmentActivity;
import com.newer.kt.Refactor.ui.Avtivity.Settings.SchoolStatisticsActivity;

/**
 * Created by jy on 16/9/14.
 */
public class SettingsFragment extends BaseFragment {

    private RelativeLayout item_1;
    private RelativeLayout item_2;
    private RelativeLayout item_3;
    private RelativeLayout item_5;
    private TextView logout;
    private int mType = 0;
    private ImageView mHead;
    private TextView mSchool,mTime;
    private LinearLayout linear_info;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_settings);
        TextView version = (TextView) getViewById(R.id.layout_settings_bbxx);
        item_1 = (RelativeLayout) getViewById(R.id.layout_settings_item_1);
        item_2 = (RelativeLayout) getViewById(R.id.layout_settings_item_2);
        item_3 = (RelativeLayout) getViewById(R.id.layout_settings_item_3);
        item_5 = (RelativeLayout) getViewById(R.id.layout_settings_item_5);
        logout = (TextView) getViewById(R.id.layout_settings_logout);
        mHead = getViewById(R.id.image_head);
        mSchool = getViewById(R.id.tv_shcool);
        mTime = getViewById(R.id.tv_time);
        linear_info = getViewById(R.id.linear_info);
        long bags = BagsDaoHelper.getInstance().getTotalCount();
//        if (bags == 0) {
//            qc_count.setText("暂无数据");
//        } else {
//            qc_count.setText(String.valueOf(bags));
//        }

        version.setText("V " + PhoneUtils.getVersionName(getThis()));
        mType = PreferenceManager.getDefaultSharedPreferences(getThis()).getInt(LoginActivity.PRE_CURRENT_TAYPE, 0);
    }

    @Override
    protected void setListener() {
        item_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (mType) {
                    case 0:
                        intent.setClass(getThis(), CampusStatisticsActivity.class);
                        intent.putExtra("club_id", PreferenceManager.getDefaultSharedPreferences(getThis()).getLong(LoginActivity.PRE_CURRENT_CLUB_ID, 0) + "");
                        break;
                    case 1:
                        intent.setClass(getThis(), SchoolStatisticsActivity.class);
                        intent.putExtra("from", "4");
                        break;
                    case 2:
                        intent.setClass(getThis(), SchoolStatisticsActivity.class);
                        intent.putExtra("from", "3");
                        break;
                    case 3:
                        intent.setClass(getThis(), SchoolStatisticsActivity.class);
                        intent.putExtra("from", "2");
                        break;
                    case 4:
                        intent.setClass(getThis(), SchoolStatisticsActivity.class);
                        intent.putExtra("from", "1");
                        break;

                }

                startActivityForResult(intent, Constants.TO_CAMPUSSTATISTICSACTIVITY);
            }
        });
        item_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SchoolEquipmentActivity.class);
            }
        });
        item_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AboutKTActivity.class);
            }
        });
        item_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HelpActivity.class);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getThis().finish();
                startActivity(LoginActivity.class);
            }
        });
        linear_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MineSchoolInfoActivity.class);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    /**
     * 点击进入学生or气场or赛事列表页面
     *
     */
    private void doStartActivity() {
        Intent intent = new Intent(getThis(), ListActivity.class);
        intent.putExtra(Constants.EXTRA_LIST_CODE, 2);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.TO_CAMPUSSTATISTICSACTIVITY && resultCode == Constants.CAMPUSSTATISTICS_BACK) {
            getThis().finish();
        }
    }
}
