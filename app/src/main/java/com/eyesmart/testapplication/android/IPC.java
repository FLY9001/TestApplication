package com.eyesmart.testapplication.android;

/**
 * IPC：Inter-process Communication，进程间通信
 * 开启多进程：四大组件配置文件配置process
 * android:process=":remote"    (进程名：com.eyesmart.testapplication：remote)
 * android:process="com.eyesmart.testapplication.remote"    (进程名：com.eyesmart.testapplication.remote)
 * 引起问题：
 * 1、静态成员和单例模式完全失效
 * 2、线程同步机制完全失效
 * 3、SP存储可靠性下降
 * 4、Application会多次创建
 */

public class IPC {
    void test() throws Exception {
        UserS.test();   //Serializable，使用简单，开销大，大量IO操作（Java提供的序列化方式，推荐）
        UserP.test();   //Parcelable，使用麻烦，效率高（Android提供的序列化方式，推荐）
    }
}
