package com.ironfactory.appjam.dtos;

import com.ironfactory.appjam.entities.ImageEntity;
import com.ironfactory.appjam.entities.LikeEntity;
import com.ironfactory.appjam.entities.UserEntity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class PictureDto implements Serializable {
    public ArrayList<UserEntity> userEntities;
    public ArrayList<ImageEntity> imageEntities;
    public ArrayList<LikeEntity> likeEntities;


    public PictureDto(JSONObject object) {
        userEntities = new ArrayList<>();
        imageEntities = new ArrayList<>();
        likeEntities = new ArrayList<>();

        UserEntity userEntity = new UserEntity(object);
        ImageEntity imageEntity = new ImageEntity(object);
        LikeEntity likeEntity = new LikeEntity(object);

        userEntities.add(userEntity);
        imageEntities.add(imageEntity);
        likeEntities.add(likeEntity);
    }


    public boolean isSame(PictureDto pictureDto) {
        int size = userEntities.size() - 1;
        if (!imageEntities.get(size).isSame(pictureDto.imageEntities.get(0)))
            return false;
        return true;
    }


    public void add(PictureDto pictureDto) {
        userEntities.addAll(pictureDto.userEntities);
        imageEntities.addAll(pictureDto.imageEntities);
        likeEntities.addAll(pictureDto.likeEntities);
    }
}
