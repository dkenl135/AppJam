package com.ironfactory.appjam.server;

import com.ironfactory.appjam.dtos.PictureDto;
import com.ironfactory.appjam.entities.UserEntity;

import java.util.ArrayList;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class RequestInterface {


    public interface OnGetRankImages {
        void onSuccess(ArrayList<PictureDto> pictureDtos);
        void onException();
    }

    public interface OnGetMyImages {
        void onSuccess(ArrayList<PictureDto> pictureDtos);
        void onException();
    }

    public interface OnLogin {
        void onSuccess(UserEntity userEntity);
        void onException();
    }

    public interface OnSignUp {
        void onSuccess(UserEntity userEntity);
        void onException();
    }
}
