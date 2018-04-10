package com.eyesmart.testapplication.mvp.presenter;

import com.eyesmart.testapplication.mvp.view.IUserView;
import com.eyesmart.testapplication.mvp.bean.UserBean;
import com.eyesmart.testapplication.mvp.model.IUserModel;
import com.eyesmart.testapplication.mvp.model.UserModel;

/**
 * Created by FLY on 2018/4/10 0010.
 */

public class UserPresenter {
    private IUserView mUserView;
    private IUserModel mUserModel;

    public UserPresenter(IUserView view) {
        mUserView = view;
        mUserModel = new UserModel();
    }

    public void saveUser(int id, String firstName, String lastName) {
        mUserModel.setID(id);
        mUserModel.setFirstName(firstName);
        mUserModel.setLastName(lastName);
    }

    public void loadUser(int id) {
        UserBean user = mUserModel.load(id);
        mUserView.setFirstName(user.getFirstName()); // 通过调用IUserView的方法来更新显示
        mUserView.setLastName(user.getLastName());
    }
}
