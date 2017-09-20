package com.eyesmart.testapplication.android;

/**
 * JNI：Java Native Interface（java本地接口）
 * NDK：Native Development Kit（原生软件开发工具包）
 * <p>
 * 步骤：
 * 1、声明native方法             //若找不到类添加：-classpath D:\AndroidStudioProjects\TestApplication\app\build\intermediates\classes\flavor\debug
 * 2、生成.h文件                 //生成放在jni目录下：>javah com.eyesmart.testapplication.android.TestJni
 * 3、实现.h文件中的方法
 * 4、编写Android.mk文件
 * 5、NDK编译生成动态库 .so文件
 */

public class TestJni {
    static {
        System.loadLibrary("TestJni");
    }

    public static native String sayHello();
}
