package com.ironfactory.appjam.server;

import android.util.Log;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class RequestManager {
    private static final String TAG = "RequestManager";


    public static void login(String name, String phone, RequestInterface.OnLogin onLogin) {
        SocketIO.login(name, phone, onLogin);
        Log.d(TAG, "로그인 ");
    }


    public static void signUp(String name, String phone, RequestInterface.OnSignUp onSignUp) {
        SocketIO.signUp(name, phone, onSignUp);
        Log.d(TAG, "회원가입 ");
    }
}
