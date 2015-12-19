package com.ironfactory.appjam.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class UserEntity {
    public static final String PROPERTY_NAME = "userName";
    public static final String PROPERTY_PHONE = "userPhone";


    private String name;
    private String phone;

    public UserEntity(JSONObject object) {
        try {
            name = object.getString(PROPERTY_NAME);
            phone = object.getString(PROPERTY_PHONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
