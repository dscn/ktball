package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.newer.kt.Refactor.Base.BaseRecyclerViewNoRefresh;

/**
 * Created by jy on 16/8/25.
 */
public class SaiShiListActivity extends BaseRecyclerViewNoRefresh {

    @Override
    protected void initToolBar() {
        setToolBarTitle("赛事列表");
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
