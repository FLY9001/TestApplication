package com.eyesmart.testapplication.mvp.presenter;

import com.eyesmart.testapplication.mvp.model.FindItemsModel;
import com.eyesmart.testapplication.mvp.view.MVPView;

import java.util.List;

/**
 * Created by FLY on 2018/4/10 0010.
 */

public class MVPPresenterImpl implements MVPPresenter {

    private MVPView mMVPView;
    private FindItemsModel mFindItemsModel;

    public MVPPresenterImpl(MVPView mainView, FindItemsModel findItemsModel) {
        this.mMVPView = mainView;
        this.mFindItemsModel = findItemsModel;
    }

    public void onResume() {
        if (mMVPView != null) {
            mMVPView.showProgress();
        }

        mFindItemsModel.findItems(new FindItemsModel.OnFinishedListener() {
            @Override
            public void onFinished(List<String> items) {
                if (mMVPView != null) {
                    mMVPView.setItems(items);
                    mMVPView.hideProgress();
                }
            }
        });
    }

    @Override
    public void onItemClicked(String item) {
        if (mMVPView != null) {
            mMVPView.showMessage(String.format("%s clicked", item));
        }
    }

    public void onDestroy() {
        mMVPView = null;
    }
}