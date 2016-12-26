package com.newer.kt.Refactor.ui.Avtivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.base.activity.BaseBottomNavigationFrame;
import com.frame.app.base.activity.BaseBottomNoToolBarNavigationFrame;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.SharedPreferencesUtils;
import com.newer.kt.Business.ServiceLoadBusiness;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.ServiceDataResult;
import com.newer.kt.Refactor.db.BagsDaoHelper;
import com.newer.kt.Refactor.db.GamesDaoHelper;
import com.newer.kt.Refactor.db.UsersDaoHelper;
import com.newer.kt.Refactor.ui.Fragment.RefereeFragment;
import com.newer.kt.Refactor.ui.Fragment.ShcoolInfoFragment;
import com.newer.kt.entity.AddClassData;
import com.newer.kt.entity.ClubDataCount;
import com.newer.kt.myClass.MyAlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;

public class ClubDataActivity2 extends BaseBottomNoToolBarNavigationFrame {


    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {ShcoolInfoFragment.class,
            RefereeFragment.class,};

    //定义数组来存放按钮图片
//    private int mImageViewArray[] = {R.drawable.xiaoyuanxinxi_a,
//            R.drawable.caipanxintong_a};

    private int mImageViewArray[] = {R.drawable.homepager_button_school_image,
            R.drawable.homepager_button_referee_image};

    //Tab选项卡的文字
    private String mTextviewArray[] = {"校园信息", "裁判系统"};

    @Override
    protected String[] getTextviewArray() {
        return mTextviewArray;
    }

    @Override
    protected int[] getImageViewArray() {
        return mImageViewArray;
    }

    @Override
    protected Class[] getFragmentArray() {
        return fragmentArray;
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {

    }

}
