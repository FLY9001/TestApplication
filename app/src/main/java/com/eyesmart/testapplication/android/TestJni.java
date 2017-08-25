package com.eyesmart.testapplication.android;

/**
 * JNI：Java Native Interface（java本地接口）
 * NDK：Native Development Kit（原生软件开发工具包）
 *
 * 步骤：
 * 1、声明native方法             //
 * 2、生成.h文件                 //\JniTest\app\src\main\java>javah -d ../jni -jni com.eyesmart.jnitest.JniDemo
 * 3、实现.h文件中的方法
 * 4、编写Android.mk文件
 * 5、NDK编译生成动态库 .so文件
 */

public class TestJni {

    public native int add(int a, int b);
}
