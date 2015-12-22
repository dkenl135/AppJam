package com.ironfactory.appjam.controll.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    public static Context context;
    public static MaterialDialog materialDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        context = this;
    }

    protected abstract void init(int resId);

    protected abstract void setListener();

    public static void showProgress() {
        if (materialDialog != null) {
            materialDialog.hide();
        }

        materialDialog = new MaterialDialog.Builder(context)
                .title("잠시만 기다려주세요")
                .progress(true, 0)
                .show();
    }

    public static void hideProgress() {
        if (materialDialog != null) {
            materialDialog.hide();
        }
    }
}
