package com.eyesmart.testapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 1 on 2017/7/2 0002.
 */

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {   //onCreate()中的savedInstanceState有可能为空
            savedInstanceState.getString("key");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {  //activity被异常终止时，才会被调用；在onStart()之后调用
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getString("key");  //savedInstanceState不可能为空
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {  //不要做耗时操作，尽量放在onStop()方法中；（因为onPause()执行完成后才能启动新的activity）
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {   //activity被异常终止时，才会被调用；和onPause()没有时序关系
        super.onSaveInstanceState(outState);
        outState.putString("key", "value");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {   //当系统配置信息发生改变时，且设置configChanges时，系统会调用此方法
        super.onConfigurationChanged(newConfig);
        //设置屏幕旋转不重新创建：android:configChanges="orientation|screenSize"
        // 当新设置中，屏幕布局模式为横排时
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            //TODO 某些操作
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {     //配合启动模式
        super.onNewIntent(intent);
    }
}
