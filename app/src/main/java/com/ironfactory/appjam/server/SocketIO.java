package com.ironfactory.appjam.server;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

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
}
