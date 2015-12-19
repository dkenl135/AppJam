package com.ironfactory.appjam.dtos;

import com.ironfactory.appjam.entities.LikeEntity;
import com.ironfactory.appjam.entities.ImageEntity;
import com.ironfactory.appjam.entities.UserEntity;

import org.json.JSONObject;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class PictureDto {
    public UserEntity userEntity;
    public ImageEntity imageEntity;
    public LikeEntity likeEntity;


    public PictureDto(JSONObject object) {
        userEntity = new UserEntity(object);
        imageEntity = new ImageEntity(object);
        likeEntity = new LikeEntity(object);
    }
}
