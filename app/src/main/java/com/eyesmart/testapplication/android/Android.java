package com.eyesmart.testapplication.android;

import com.eyesmart.testapplication.R;

/**
 * Created by FLY on 2018/9/17 0017.
 */

public class Android {
    void test() {
        int framwork = R.drawable.framwork; //架构图
        int source = R.drawable.source;     //源码目录结构

    }
    /**
     ******************架构********************************************
     Application：               应用程序层              app
     Framework：                 应用程序框架层          APIS
     Libraries(Android Runtime)：系统运行库运行环境层    各种C、C++本地库和虚拟机（Dalvik、ART）
     Linux Kernel：              Linux内核层            驱动程序

     Android 4.4以上 时虚拟机由 Dalvik 变为 ART
     Dalvik：运行时才编译
     ART：   安装时就编译

     ******************Android源码********************************************
     目录网站:http://androidxref.com
     源码目录结构：（见图）
     Makefile：指导自动化编译
     */
}
