package com.eyesmart.testapplication.mvp.view;

/**
 * Created by FLY on 2018/4/10 0010.
 */

public interface IUserView {
    int getID();

    String getFristName();

    String getLastName();

    void setFirstName(String firstName);

    void setLastName(String lastName);
}
