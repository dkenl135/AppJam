package com.ironfactory.appjam.entities;

import com.ironfactory.appjam.Global;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class ImageEntity {

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
            id = object.getString(Global.ID);
            created = object.getLong(Global.CREATED);
            userId = object.getInt(Global.USER_ID);
            subject = object.getString(Global.SUBJECT);
            title = object.getString(Global.TITLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ImageEntity() {
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
