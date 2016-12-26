package com.newer.kt.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newer.kt.R;

public class Start3Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TextView tv = new TextView(getActivity());
        tv.setText("asdasdasd");
        return inflater.inflate(R.layout.fragment_start3, container, false);
    }

}
