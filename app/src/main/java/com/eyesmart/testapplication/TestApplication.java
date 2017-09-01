package com.eyesmart.testapplication;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by FLY on 2017/9/1.
 */

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
