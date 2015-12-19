package com.ironfactory.appjam;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static Context context;
    public static MaterialDialog materialDialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        context = getApplicationContext();
    }


    @Override
    protected void onResume() {
        super.onResume();
        context = getApplicationContext();
    }

    protected abstract void init();

    public static void showProgress() {
        if (materialDialog != null) {
            materialDialog.hide();
        }

        materialDialog = new MaterialDialog.Builder(BaseActivity.context)
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
