package com.ironfactory.appjam.server;

import com.ironfactory.appjam.entity.UserEntity;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class RequestInterface {


    public interface OnLogin {
        void onSuccess(UserEntity userEntity);
        void onException();
    }

    public interface OnSignUp {
        void onSuccess(UserEntity userEntity);
        void onException();
    }
}
