package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.newer.kt.R;

public class Main2Activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    public void doStart(View view) {
        startActivity(new Intent(this,GameSelectorActivity.class));
    }
}
