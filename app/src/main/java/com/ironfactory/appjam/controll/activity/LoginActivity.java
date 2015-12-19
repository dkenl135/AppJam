package com.ironfactory.appjam.controll.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ironfactory.appjam.Global;
import com.ironfactory.appjam.R;
import com.ironfactory.appjam.entity.UserEntity;
import com.ironfactory.appjam.server.RequestInterface;
import com.ironfactory.appjam.server.RequestManager;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private Button loginButton;
    private EditText nameEdit;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getData()) {
            Log.d(TAG, "로그인 레이아웃 구성");
            init(R.layout.activity_login);
        }
    }


    private boolean getData() {
        // 기존 데이터 불러오기
        Log.d(TAG, "getData");
        preferences = getSharedPreferences(Global.APP_NAME, MODE_PRIVATE);
        String name = preferences.getString(Global.NAME, null);
        Log.d(TAG, "name = " + name);
        if (name != null) {
            // 이미 로그인 한적이 있다면
            String phone = getPhone();
            hideProgress();
            RequestManager.login(name, phone, new RequestInterface.OnLogin() {
                @Override
                public void onSuccess(UserEntity userEntity) {
                    hideProgress();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(Global.USER, userEntity);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onException() {
                    new MaterialDialog.Builder(getApplicationContext())
                            .title("에러 발생")
                            .content("로그인 중 에러가 발생했습니다")
                            .show();
                    hideProgress();
                }
            });
            return true;
        }
        return false;
    }


    @Override
    protected void init(int resId) {
        setContentView(resId);
        loginButton = (Button) findViewById(R.id.activity_login_button);
        nameEdit = (EditText) findViewById(R.id.activity_login_name);
    }


    @Override
    protected void setListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = getPhone();
                String name = nameEdit.getText().toString();

                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(name)) {
                    showProgress();
                    RequestManager.signUp(name, phone, new RequestInterface.OnSignUp() {
                        @Override
                        public void onSuccess(UserEntity userEntity) {
                            hideProgress();
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(Global.NAME, userEntity.getName());
                            editor.commit();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra(Global.USER, userEntity);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onException() {
                            new MaterialDialog.Builder(getApplicationContext())
                                    .title("에러 발생")
                                    .content("로그인 중 에러가 발생했습니다")
                                    .show();
                            hideProgress();
                        }
                    });
                }
            }
        });
    }


    private String getPhone() {
        TelephonyManager mgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String phone = mgr.getLine1Number();
        return phone;
    }
}
