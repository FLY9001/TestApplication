package com.eyesmart.testapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eyesmart.testapplication.android.CMD;
import com.eyesmart.testapplication.android.HttpUtils;
import com.eyesmart.testapplication.android.TestActivity;
import com.eyesmart.testapplication.android.TestDatabase;
import com.eyesmart.testapplication.android.TestFragment;
import com.eyesmart.testapplication.android.TestHandler;
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
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Class[] java = {
                APIs.class,             //Runtime、System、字符操作、数据日期格式化
                MyIO.class,             //数据流
                MyCollection.class,     //集合、数组、栈、属性类
                MyThread.class,         //线程基础

                MyGenericity.class,     //泛型、注解
                MyEnum.class,           //反射、枚举
        };

        Class[] android = {
                TestActivity.class,     //四大组件、Fragment、Intent
                TestService.class,
                TestReceiver.class,
                TestFragment.class,
                TestHandler.class,      //线程及消息通讯

                TestDatabase.class,     //数据库
                TestNet.class,          //Socket
                HttpUtils.class,        //Http

                TestRxJava.class,
                CMD.class,               //命令行
                Git.class
        };
    }
}
