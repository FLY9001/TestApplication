package com.eyesmart.testapplication.mvp.model;

import java.util.List;

/**
 * Created by FLY on 2018/4/10 0010.
 */

public interface FindItemsModel {


    interface OnFinishedListener {
        void onFinished(List<String> items);
    }

    void findItems(OnFinishedListener listener);

}
