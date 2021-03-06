package com.ironfactory.appjam.server;

import android.util.Log;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class RequestManager {
    private static final String TAG = "RequestManager";


    public static void getImages(RequestInterface.OnGetImages onGetImages) {
        SocketIO.getImages(onGetImages);
    }


    public static void insertImage(String fileUrl, String imageId, int userId, String subject, String title, RequestInterface.OnInsertImage onInsertImage) {
        SocketIO.insertImage(fileUrl, imageId, userId, subject, title, onInsertImage);
        Log.d(TAG, "그림 입력 요청");
    }

    // 포지션 별로 분할해서 받는거 해야함
    public static void getRankImages(RequestInterface.OnGetRankImages onGetRankImages) {
        SocketIO.getRankImages(onGetRankImages);
        Log.d(TAG, "그림 랭킹 받아오기 요청");
    }

    public static void getMyImages(int userId, RequestInterface.OnGetMyImages onGetMyImages) {
        SocketIO.getMyImages(userId, onGetMyImages);
        Log.d(TAG, "내 그림 받아오기 요청");
    }

    public static void login(String name, String phone, RequestInterface.OnLogin onLogin) {
        SocketIO.login(name, phone, onLogin);
        Log.d(TAG, "로그인 요청");
    }


    public static void signUp(String name, String phone, RequestInterface.OnSignUp onSignUp) {
        SocketIO.signUp(name, phone, onSignUp);
        Log.d(TAG, "회원가입 요청");
    }
}
