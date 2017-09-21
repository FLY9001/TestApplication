package com.eyesmart.testapplication.android;

/**
 * JNI：Java Native Interface（java本地接口）
 * NDK：Native Development Kit（原生软件开发工具包）
 * ---------------------------------------------------
 * 前期准备：
 * 工具配置：在SDK Tools下载：NDK、CMAKE(C/C++的外部构建工具，可以提示代码)、LLDB(调试本地代码的工具)
 * 环境变量配置：变量名：NDK_HOME 变量值：“...\ndk-bundle”
 * Studio NDK路径配置
 * android.useDeprecatedNdk=true
 *----------------------------------------------------
 * 编译步骤：
 * 1、声明native方法             //若找不到类添加：-classpath D:\AndroidStudioProjects\TestApplication\app\build\intermediates\classes\flavor\debug
 * 2、生成.h文件                 //生成放在jni目录下：>javah com.eyesmart.testapplication.android.TestJni
 * 3、实现.h文件中的方法         //新建c或cpp文件，并include .h;
 * 4、编写Android.mk文件         //.h、.c/cpp、.mk都在jni目录下
 * 5、NDK编译生成动态库 .so文件  //在jni上层目录执行：\main>ndk-build ，会自动生成obj、libs目录，所需so在libs目录下
 * ----------------------------------------------------
 * 应用步骤：
 *
 */

public class TestJni {
    static {
        System.loadLibrary("TestJni");
    }

    public static native String sayHello();
}
