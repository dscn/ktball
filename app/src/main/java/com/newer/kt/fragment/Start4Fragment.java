package com.newer.kt.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.frame.app.utils.SharedPreferencesUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.ui.Avtivity.LoginActivity;
import com.newer.kt.Refactor.utils.CommonUtil;

public class Start4Fragment extends Fragment {

    //立即体验图标
    ImageView imageView;
    public static final String IS_VIEW_APP_GUIDE_PAGE = "is_view_app_guide_page";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start4, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView_login);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(IS_VIEW_APP_GUIDE_PAGE,true).commit();
                SharedPreferencesUtils.saveInt(getActivity(), "currentVersion", CommonUtil.getVersion(getActivity()));
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }

}
