package com.ironfactory.appjam.controll.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ironfactory.appjam.R;
import com.ironfactory.appjam.controll.fragment.MyPictureFragment;
import com.ironfactory.appjam.controll.fragment.PictureFragment;
import com.ironfactory.appjam.controll.fragment.RankFragment;

public class MainActivity extends BaseActivity {

    private Button pictureBtn;
    private Button myPictureBtn;
    private Button rankBtn;

    private PictureFragment pictureFragment;
    private MyPictureFragment myPictureFragment;
    private RankFragment rankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.activity_main);
    }

    @Override
    protected void init(int resId) {
        setContentView(resId);

        pictureBtn = (Button) findViewById(R.id.activity_main_picture);
        myPictureBtn = (Button) findViewById(R.id.activity_main_myPicture);
        rankBtn = (Button) findViewById(R.id.activity_main_rank);

        pictureFragment = new PictureFragment();
        myPictureFragment = new MyPictureFragment();
        rankFragment = new RankFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main_container, pictureFragment)
                .commit();

        setListener();
    }

    @Override
    protected void setListener() {
        pictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_container, pictureFragment)
                        .commit();
            }
        });

        myPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_container, myPictureFragment)
                        .commit();
            }
        });

        rankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_container, rankFragment)
                        .commit();
            }
        });
    }
}