package com.ironfactory.appjam.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class LikeEntity implements Serializable {
    public static final String PROPERTY_USER_ID = "likeUserId";
    public static final String PROPERTY_IMAGE_ID = "likeImageId";
    public static final String PROPERTY_IMAGE_CREATED = "likeImageCreated";

    private int userId;
    private int imageId;
    private long imageCreated;

    public LikeEntity(JSONObject object) {
        try {
            userId = object.getInt(PROPERTY_USER_ID);
            imageId = object.getInt(PROPERTY_IMAGE_ID);
            imageCreated = object.getLong(PROPERTY_IMAGE_CREATED);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public LikeEntity() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public long getImageCreated() {
        return imageCreated;
    }

    public void setImageCreated(long imageCreated) {
        this.imageCreated = imageCreated;
    }
}
