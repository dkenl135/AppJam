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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
                                if (i != 0) {
                                    int size = pictureDtos.size() - 1;
                                    if (pictureDtos.get(size).isSame(pictureDto)) {
                                        pictureDtos.get(size).add(pictureDto);
                                    }
                                }
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
                                if (i != 0) {
                                    int size = pictureDtos.size() - 1;
                                    if (pictureDtos.get(size).isSame(pictureDto)) {
                                        pictureDtos.get(size).add(pictureDto);
                                    }
                                }
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


    public static void insertImage(String fileUrl, String imageId, int userId, String subject, String title, RequestInterface.OnInsertImage onInsertImage) {
        upload(fileUrl, imageId, userId, subject, title, onInsertImage);
    }

    public static void getImages(final RequestInterface.OnGetImages onGetImages) {
        try {
            JSONObject reqObject = new JSONObject();
            socket.emit(Global.GET_IMAGES, "");
            Log.d(TAG, "그림 랭크 요청 Object = " + reqObject);
            socket.once(Global.GET_IMAGES, new Emitter.Listener() {
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
                                if (i != 0) {
                                    int size = pictureDtos.size() - 1;
                                    if (pictureDtos.get(size).isSame(pictureDto)) {
                                        pictureDtos.get(size).add(pictureDto);
                                    }
                                }
                                pictureDtos.add(pictureDto);
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onGetImages.onSuccess(pictureDtos);
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onGetImages.onException();
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


















    private static void upload(final String fileUrl, final String imageId, final int userId, final String imageSubject, final String imageTitle, final RequestInterface.OnInsertImage onInsertImage) {
        Log.d(TAG, "fileUrl = " + fileUrl);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                DataOutputStream dos = null;

                final String LINE_END = "\r\n";
                final String TWO_HYPHENS = "--";
                final String BOUNDARY = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                final int MAX_BUFFER_SIZE = 1024 * 1024;
                File file = new File(fileUrl);

                if (!file.isFile()) {
                    Log.e(TAG, "파일아님 = " + fileUrl);
                    return;
                }

                try {
                    Log.d(TAG, "파일은 맞음");
                    FileInputStream fis = new FileInputStream(file);
                    String serverUrl = "http://appjam-server.herokuapp.com/insertImage";
                    URL url = new URL(serverUrl);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary=" + BOUNDARY);
                    conn.setRequestProperty("uploaded_file", fileUrl);
                    conn.setRequestProperty("imageid", imageId);
                    conn.setRequestProperty("userid", String.valueOf(userId));
                    conn.setRequestProperty("subject", new String(imageSubject.getBytes("euc-kr"), "utf-8"));
                    conn.setRequestProperty("title", new String(imageTitle.getBytes("euc-kr"), "utf-8"));

                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + imageId + "\"" + LINE_END);
                    dos.writeBytes(LINE_END);

                    bytesAvailable = fis.available();

                    bufferSize = Math.min(bytesAvailable, MAX_BUFFER_SIZE);
                    buffer = new byte[bufferSize];

                    bytesRead = fis.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fis.available();
                        bufferSize = Math.min(bytesAvailable, MAX_BUFFER_SIZE);
                        bytesRead = fis.read(buffer, 0, bufferSize);
                    }

                    dos.writeBytes(LINE_END);
                    dos.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_END);
                    Log.d(TAG, "연결");
                    final int serverResCode = conn.getResponseCode();
                    String serverResMsg = conn.getResponseMessage();

                    if (serverResCode == 200) {
                        Log.d(TAG, "서버 메세지 = " + serverResMsg);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        final StringBuilder sb = new StringBuilder();
                        String str = null;
                        while ((str = bufferedReader.readLine()) != null) {
                            sb.append(str);
                        }
                        serverResMsg = sb.toString();
                        Log.d(TAG, "서버 메세지2 = " + serverResMsg);
                        bufferedReader.close();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onInsertImage.onSuccess();
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onInsertImage.onException();
                            }
                        });
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
