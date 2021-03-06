package com.ironfactory.appjam.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class UserEntity implements Serializable {
    public static final String PROPERTY_NAME = "userName";
    public static final String PROPERTY_PHONE = "userPhone";
    public static final String PROPERTY_ID = "userId";

    private int id;
    private String name;
    private String phone;

    public UserEntity() {
    }

    public UserEntity(JSONObject object) {
        try {
            id = object.getInt(PROPERTY_ID);
            name = object.getString(PROPERTY_NAME);
            phone = object.getString(PROPERTY_PHONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isSame(UserEntity userEntity) {
        if (id != userEntity.id)
            return false;
        if (!name.equals(userEntity.name))
            return false;
        if (!phone.equals(userEntity.phone))
            return false;
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
