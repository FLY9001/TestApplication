package com.eyesmart.testapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.testlibrary.TestLib;
import com.eyesmart.testapplication.android.CMD;
import com.eyesmart.testapplication.android.HttpUtils;
import com.eyesmart.testapplication.android.TestActivity;
import com.eyesmart.testapplication.android.TestDatabase;
import com.eyesmart.testapplication.android.TestFragment;
import com.eyesmart.testapplication.android.TestHandler;
import com.eyesmart.testapplication.android.TestJni;
import com.eyesmart.testapplication.android.TestNet;
import com.eyesmart.testapplication.android.TestReceiver;
import com.eyesmart.testapplication.android.TestRxJava;
import com.eyesmart.testapplication.android.TestService;
import com.eyesmart.testapplication.java.APIs;
import com.eyesmart.testapplication.java.MyCollection;
import com.eyesmart.testapplication.java.MyEnum;
import com.eyesmart.testapplication.java.MyGenericity;
import com.eyesmart.testapplication.java.MyIO;
import com.eyesmart.testapplication.java.MyThread;

/**
 * ［干货］2017已来，最全面试总结：
 * http://mp.weixin.qq.com/s?__biz=MzI0MjE3OTYwMg==&mid=2649548612&idx=1&sn=8e46b6dd47bd8577a5f7098aa0889098&chksm=f1180c39c66f852fd955a29a9cb4ffa9dc4d528cab524059bcabaf37954fa3f04bc52c41dae8&mpshare=1&scene=23&srcid=0803Ekz4932sa0JrjO4jR36C#rd
 * <p>
 * 刘望舒知识体系引导
 * http://blog.csdn.net/itachi85/article/details/50705350
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(TestJni.sayHello());

        //java相关
        Class[] java = {
                TestLib.class,
                APIs.class,             //Runtime、System、字符操作、数据日期格式化
                MyIO.class,             //数据流
                MyCollection.class,     //集合、数组、栈、属性类
                MyThread.class,         //线程基础

                MyGenericity.class,     //泛型、注解
                MyEnum.class,           //反射、枚举
        };
        //android基础框架
        Class[] android = {
                TestApplication.class,  //Application、Context
                TestActivity.class,     //四大组件、Fragment、Intent
                TestService.class,      //TODO aidl
                TestReceiver.class,
                //TODO Content Provider
                TestFragment.class,
                TestHandler.class,      //线程及消息通讯 //TODO 原理

                TestDatabase.class,     //数据库
                TestNet.class,          //Socket //TODO http
                TestJni.class,          //Jni的编译、应用
                //TODO 跨进程通讯
                //TODO Android 版本新特性：MD、权限

                CMD.class,              //命令行
                Git.class               //git
        };
        //android代码优化：
        Class[] android2 = {
                //TODO 图片优化
                //TODO UI优化
                HttpUtils.class,        //TODO 网络优化
                TestRxJava.class,       //TODO 原理及应用
                //TODO 内存泄漏、优化

                //TODO 架构：MVP、MVP、MVVM
                //TODO 热修复
                //TODO 混合开发
                //TODO React Native
        };
        Class[] android3 = {
                //TODO 设计模式
                //TODO 算法
        };
    }
}
