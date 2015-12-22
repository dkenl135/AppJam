package com.ironfactory.appjam.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class ImageEntity implements Serializable {

    public static final String PROPERTY_CREATED = "imageCreated";
    public static final String PROPERTY_ID = "imageId";
    public static final String PROPERTY_USER_ID = "imageUserId";
    public static final String PROPERTY_SUBJECT = "imageSubject";
    public static final String PROPERTY_TITLE = "imageTitle";

    private long created;
    private String id;
    private int userId;
    private String subject;
    private String title;

    public ImageEntity(JSONObject object) {
        try {
            id = object.getString(PROPERTY_ID);
            created = object.getLong(PROPERTY_CREATED);
            userId = object.getInt(PROPERTY_USER_ID);
            subject = object.getString(PROPERTY_SUBJECT);
            title = object.getString(PROPERTY_TITLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ImageEntity() {
    }


    public boolean isSame(ImageEntity imageEntity) {
        if (!id.equals(imageEntity.id))
            return false;
        return true;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
