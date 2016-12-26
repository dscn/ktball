package com.newer.kt.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newer.kt.R;
import com.newer.kt.Refactor.ui.Avtivity.LoginActivity;

public class Start2Fragment extends Fragment {

    public static final String IS_VIEW_APP_GUIDE_PAGE = "is_view_app_guide_page";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start2, container, false);
        TextView tv = new TextView(getActivity());
        tv.setText("asdasdasd");
        //单击立即体验跳转登录页面
        return view;
    }

}
