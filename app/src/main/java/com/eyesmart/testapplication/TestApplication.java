package com.eyesmart.testapplication;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Context：描述一个应用程序环境的信息（即上下文）
 * Context数量 = Activity + Service + 1
 * 启动Activity（新建任务栈也可）、注入Layout（会使用主题样式）、弹出Dialog（必须）：最好使用Activity类型的Context
 *
 * 使用建议：
 * 1：优先使用Application的Context
 * 2：不要让生命周期长于Activity的对象持有到Activity的引用
 * 3：尽量不要在Activity中使用非静态内部类，因为非静态内部类会隐式持有外部类实例的引用
 *
 *
 */

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
