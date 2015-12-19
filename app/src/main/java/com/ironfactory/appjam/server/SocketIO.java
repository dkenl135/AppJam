package com.ironfactory.appjam.server;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.ironfactory.appjam.Global;
import com.ironfactory.appjam.dtos.PictureDto;
import com.ironfactory.appjam.entities.UserEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ironFactory on 2015-08-03.
 */
public class SocketIO {

    private static Handler handler = new Handler();
    private static final String SERVER_URL = "http://appjam-server.herokuapp.com";
    private static final String TAG = "SocketIO";


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
        int code = object.getInt(Global.CODE);
        return code;
    }



    public static void login(String name, String phone, final RequestInterface.OnLogin onLogin) {
        try {
            JSONObject reqObject = new JSONObject();
            reqObject.put(Global.NAME, name);
            reqObject.put(Global.PHONE, phone);
            Log.d(TAG, "로그인 요청 Object = " + reqObject);
            socket.emit(Global.LOGIN, reqObject);
            socket.once(Global.LOGIN, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONObject resObject = getJson(args);
                        final int code = getCode(resObject);
                        Log.d(TAG, "로그인 응답 Object = " + resObject);
                        if (code == 200) {
                            JSONObject userObject = resObject.getJSONObject(Global.USER);
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
            reqObject.put(Global.NAME, name);
            reqObject.put(Global.PHONE, phone);
            Log.d(TAG, "회원가입 요청 Object = " + reqObject);
            socket.emit(Global.SIGN_UP, reqObject);
            socket.once(Global.SIGN_UP, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONObject resObject = getJson(args);
                        final int code = getCode(resObject);
                        Log.d(TAG, "회원가입 응답 Object = " + resObject);
                        if (code == 200) {
                            JSONObject userObject = resObject.getJSONObject(Global.USER);
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


    public static void getMyImages(int userId, final RequestInterface.OnGetMyImages onGetMyImages) {
        try {
            JSONObject reqObject = new JSONObject();
            reqObject.put(Global.USER_ID, userId);
            socket.emit(Global.GET_MY_IMAGES, reqObject);
            Log.d(TAG, "내 그림 요청 Object = " + reqObject);
            socket.once(Global.GET_MY_IMAGES, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONObject resObject = getJson(args);
                        final int code = getCode(resObject);
                        Log.d(TAG, "내 그림 응답 Object = " + resObject);
                        if (code == 200) {
                            JSONArray imageArray = resObject.getJSONArray(Global.IMAGE);
                            final ArrayList<PictureDto> pictureDtos = new ArrayList<PictureDto>();
                            for (int i = 0; i < imageArray.length(); i++) {
                                JSONObject object = imageArray.getJSONObject(i);
                                PictureDto pictureDto = new PictureDto(object);
                                pictureDtos.add(pictureDto);
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onGetMyImages.onSuccess(pictureDtos);
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onGetMyImages.onException();
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


    public static void getRankImages(final RequestInterface.OnGetRankImages onGetRankImages) {
        try {
            JSONObject reqObject = new JSONObject();
            socket.emit(Global.GET_RANK_IMAGES, "");
            Log.d(TAG, "그림 랭크 요청 Object = " + reqObject);
            socket.once(Global.GET_RANK_IMAGES, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONObject resObject = getJson(args);
                        final int code = getCode(resObject);
                        Log.d(TAG, "그림 랭크 응답 Object = " + resObject);
                        if (code == 200) {
                            JSONArray imageArray = resObject.getJSONArray(Global.IMAGE);
                            final ArrayList<PictureDto> pictureDtos = new ArrayList<PictureDto>();
                            for (int i = 0; i < imageArray.length(); i++) {
                                JSONObject object = imageArray.getJSONObject(i);
                                PictureDto pictureDto = new PictureDto(object);
                                pictureDtos.add(pictureDto);
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onGetRankImages.onSuccess(pictureDtos);
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onGetRankImages.onException();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }























}
