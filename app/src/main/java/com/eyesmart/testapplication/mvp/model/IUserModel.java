package com.eyesmart.testapplication.mvp.model;

import com.eyesmart.testapplication.mvp.bean.UserBean;

/**
 * Created by FLY on 2018/4/10 0010.
 */

public interface IUserModel {
    void setID(int id);

    void setFirstName(String firstName);

    void setLastName(String lastName);

    int getID();

    UserBean load(int id);// 通过id读取user信息,返回一个UserBean
}
