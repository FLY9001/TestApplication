package com.eyesmart.testapplication.mvp.view;

import java.util.List;

/**
 * Created by FLY on 2018/4/10 0010.
 */

public interface MVPView {

    void showProgress();

    void hideProgress();

    void setItems(List<String> items);

    void showMessage(String message);

}