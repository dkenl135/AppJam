package com.ironfactory.appjam.server;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.ironfactory.appjam.entity.UserEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ironFactory on 2015-08-03.
 */
public class SocketIO {

    private static Handler handler = new Handler();
    private static final String SERVER_URL = "http://appjam-server.herokuapp.com";
    private static final String TAG = "SocketIO";

    public static final String LOGIN = "login";
    public static final String SIGN_UP = "signUp";

    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String PHONE = "phone";


    public static final String USER = "user";


    public static Socket socket;
    private Context context;

    public SocketIO(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        Log.d(TAG, "init");
        try {
            socket = IO.socket(SERVER_URL);
        } catch (Exception e) {
            Log.e(TAG, "init 에러 = " + e.getMessage());
        }

        if (socket != null) {
            socketConnect();
        }
    }

    private void socketConnect() {
        socket.open();
        socket.connect();
    }


    private static JSONObject getJson(Object... args) {
        JSONObject object = (JSONObject) args[0];
        return object;
    }


    private static int getCode(JSONObject object) throws JSONException{
        int code = object.getInt(CODE);
        return code;
    }


    public static void login(String name, String phone, final RequestInterface.OnLogin onLogin) {
        try {
            JSONObject reqObject = new JSONObject();
            reqObject.put(NAME, name);
            reqObject.put(PHONE, phone);
            socket.emit(LOGIN, reqObject);
            socket.once(LOGIN, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONObject resObject = getJson(args);
                        final int code = getCode(resObject);
                        if (code == 200) {
                            JSONObject userObject = resObject.getJSONObject(USER);
                            final UserEntity userEntity = new UserEntity(userObject);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onLogin.onSuccess(userEntity);
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onLogin.onException();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public static void signUp(String name, String phone, final RequestInterface.OnSignUp onSignUp) {
        try {
            JSONObject reqObject = new JSONObject();
            reqObject.put(NAME, name);
            reqObject.put(PHONE, phone);
            socket.emit(SIGN_UP, reqObject);
            socket.once(SIGN_UP, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONObject resObject = getJson(args);
                        final int code = getCode(resObject);
                        if (code == 200) {
                            JSONObject userObject = resObject.getJSONObject(USER);
                            final UserEntity userEntity = new UserEntity(userObject);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onSignUp.onSuccess(userEntity);
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onSignUp.onException();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

























}
