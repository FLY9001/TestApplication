package com.eyesmart.testapplication.mvp.bean;

/**
 * Created by FLY on 2018/4/10 0010.
 */

public class UserBean {
    private String mFirstName;
    private String mLastName;

    public UserBean(String firstName, String lastName) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }
}
