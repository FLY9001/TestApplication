package com.eyesmart.testapplication;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
/**
 * 进程 是程序的一个运行实例；线程 是CPU调度的基本单位
 *
 * 程序主入口都是main函数；其工作过程：1、初始化，2、进入“死循环”（在循环中处理各种事件，直到进程退出）
 * 1、创建循环处理消息的环境；2、循环处理消息
 * 不停循环处理本进程和其他进程传递过来的消息，是APP应用“动起来”的原因
 *
 * 四大组件并不是程序（进程）的全部，而只是它的“零件”
 * 同一个包的组件将运行在相同的进程空间中
 * 不同包中的组件可以通过一定的方式运行在一个进程空间中
 * 程序启动后，将组建ActivityThread主线程
 * 一个Activity启动后至少会有3个线程，1个主线程、2个Binder线程
 */

/**
 * Context：描述一个应用程序环境的信息（即上下文）
 * Context数量 = Activity + Service + 1
 * 弹出Dialog（必须）、启动Activity（新建任务栈也可）、注入Layout（会使用主题样式）：最好使用Activity类型的Context
 * <p>
 * 使用建议：
 * 1：优先使用Application的Context
 * 2：不要让生命周期长于Activity的对象持有到Activity的引用
 * 3：尽量不要在Activity中使用非静态内部类，因为非静态内部类会隐式持有外部类实例的引用
 */

public class TestApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        context = this;

        LeakCanary.install(this);
    }
}
