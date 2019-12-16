package com.eyesmart.testapplication.android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.eyesmart.testapplication.R;

/**
 * 启动模式（通过AndroidManifest和intent设置）；
 * standard：大多数Activity
 * singleTop：不在栈顶，都会创建新的实例；如新闻类或者阅读类App的内容页面
 * singleTask：重用时，它上面的实例将会被移出栈(会调用实例的onNewIntent())，保证该实例回到栈顶，；如浏览器的主界面
 * singleInstance：任何应用再激活该Activity时都会重用该栈中的实例( 会调用实例的 onNewIntent())；如闹铃提醒
 * <p>
 * 清空任务栈：通过AndroidManifest和intent设置
 *
 * java.lang.Objectl
    ↳    android.content.Context
        ↳    android.content.ContextWrapper
            ↳    android.view.ContextThemeWrapper
                ↳    android.app.Activity
 */

public class TestActivity extends Activity {

    void test() {
        onCreate(null);
        //onRestart();
        onStart();
        onResume();                         //访问网络一般放在此
        onPause();
        onStop();
        onDestroy();

        onConfigurationChanged(null);
        onNewIntent(null);


        int activity = R.drawable.activity;     //activity根View
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置Window属性
        TestWindow.activityWindow(this);

        /**最终调用PhoneWindow的setContentView*/
        //1、得到DecorView，其是Activity的顶级根View，包括标题栏(ActionBar)和内容栏(contentParent)
        //2、设置DecorView内容栏的View，即contentView(FrameLayout)，可通过findViewById(android.R.id.content)获得，content
        //3、在onResume中调用WindowManager的addView方法，接着setVisibility显示出来
        setContentView(R.layout.activity_main);

        //可通过android.R.id.content获得contentView
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        //此为我们为activity设置的View
        content.getChildAt(0);



        if (savedInstanceState != null) {                               //onCreate()中的savedInstanceState有可能为空
            savedInstanceState.getString("key");
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {  //activity被异常终止再启动时，才会被调用；在onStart()之后调用
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getString("key");//savedInstanceState不可能为空
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {               //activity被异常终止时，才会被调用；和onPause()没有时序关系
        super.onSaveInstanceState(outState);
        outState.putString("key", "value");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {       //当系统配置信息发生改变时，且设置configChanges时，系统会调用此方法
        super.onConfigurationChanged(newConfig);
        //设置屏幕旋转不重新创建：android:configChanges="orientation|keyboardHidden|screenSize"
        //当新设置中，屏幕布局模式为横排时
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            //TODO 某些操作
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {                         //配合启动模式
        super.onNewIntent(intent);

    }
}
