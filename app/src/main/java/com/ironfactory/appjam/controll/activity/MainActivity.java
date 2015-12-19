package com.ironfactory.appjam.controll.activity;

import android.os.Bundle;

import com.ironfactory.appjam.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void init() {
    }
}