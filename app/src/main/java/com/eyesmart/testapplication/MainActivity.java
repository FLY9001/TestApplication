package com.eyesmart.testapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eyesmart.testapplication.android.CMD;
import com.eyesmart.testapplication.android.HttpUtils;
import com.eyesmart.testapplication.android.SensorActivity;
import com.eyesmart.testapplication.android.TestActivity;
import com.eyesmart.testapplication.android.TestDatabase;
import com.eyesmart.testapplication.android.TestFragment;
import com.eyesmart.testapplication.android.TestHandler;
import com.eyesmart.testapplication.android.TestIO;
import com.eyesmart.testapplication.android.TestJni;
import com.eyesmart.testapplication.android.TestMedia;
import com.eyesmart.testapplication.android.TestMemory;
import com.eyesmart.testapplication.android.TestNet;
import com.eyesmart.testapplication.android.TestParse;
import com.eyesmart.testapplication.android.TestProvider;
import com.eyesmart.testapplication.android.TestReceiver;
import com.eyesmart.testapplication.android.TestRxJava;
import com.eyesmart.testapplication.android.TestService;
import com.eyesmart.testapplication.java.APIs;
import com.eyesmart.testapplication.java.MyCollection;
import com.eyesmart.testapplication.java.MyEnum;
import com.eyesmart.testapplication.java.MyGenericity;
import com.eyesmart.testapplication.java.MyIO;
import com.eyesmart.testapplication.java.MyThread;
import com.eyesmart.testapplication.java.TestDesignPattern;
import com.eyesmart.testapplication.project.ProjectCatalog;
import com.eyesmart.testapplication.project.Resource;
import com.eyesmart.testapplication.ui.TestAnim;
import com.eyesmart.testapplication.ui.TestView;
import com.eyesmart.testapplication.ui.viewprinciple.AnalogClock;
import com.eyesmart.testapplication.ui.viewprinciple.StaggerLayout;
import com.eyesmart.testapplication.ui.viewprinciple.ViewPrincipleActivity;
import com.eyesmart.testapplication.ui.viewwidget.ListViewFragment;
import com.eyesmart.testapplication.ui.viewwidget.MyView;
import com.eyesmart.testapplication.ui.viewwidget.RecyclerFragment;
import com.eyesmart.testapplication.ui.viewwidget.ViewWidgetActivity;

/**
 * ［干货］最全知识总结：
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
        /**java*/
        Class[] java = {
                APIs.class,             //Runtime、System、字符操作、数据日期格式化
                MyIO.class,             //数据流
                MyCollection.class,     //集合、数组、栈、属性类
                MyThread.class,         //线程基础

                MyGenericity.class,     //泛型、注解
                MyEnum.class,           //反射、枚举
        };
        /**android工程*/
        Class[] project = {
                ProjectCatalog.class,   //工程目录
                Resource.class          //资源文件
        };
        /**android基础框架*/
        Class[] android = {
                TestApplication.class,  //Application、Context
                TestActivity.class,     //Activity
                TestService.class,      //Service、通知
                TestReceiver.class,     //BroadcastReceiver
                TestProvider.class,     //ContentProvider
                TestFragment.class,     //Fragment
                TestHandler.class,      //线程及消息通讯

                TestIO.class,           //存储
                TestDatabase.class,     //数据库
                TestNet.class,          //Socket
                TestParse.class,        //TODO 数据解析

                TestMedia.class,        //多媒体：音频、视频、相机
                SensorActivity.class,   //传感器

                TestJni.class,          //Jni的编译、应用
                //TODO Android 版本新特性：MD、权限
        };

        /**android代码优化*/
        Class[] android2 = {
                //TODO UI优化
                //TODO 图片优化
                TestMemory.class,       //TODO 内存泄漏、优化
                HttpUtils.class,        //Http TODO 组合框架
                TestRxJava.class,       //TODO 原理及应用

                //TODO 架构：MVP、MVP、MVVM
                //TODO 热修复
                //TODO 混合开发
                //TODO React Native

                CMD.class,              //命令行
                Git.class               //git
        };
        Class[] android3 = {
                TestDesignPattern.class,//TODO 设计模式
                //TODO 算法
        };
        /**UI*/
        Class[] view = {
                TestView.class,         //View的基本参数、初始化、绘制流程、事件体系
                MyView.class,
                AnalogClock.class,      //完全自定义View(重写onDraw)
                StaggerLayout.class,    //完全自定义ViewGroup(重写)
                //扩展已有View(如密码输入EditText)
                //扩展已有ViewGroup(inflate)

                TestAnim.class,         //动画

                ListViewFragment.class,
                RecyclerFragment.class,
        };
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_view_widget:
                ViewWidgetActivity.actionStart(MainActivity.this);
                break;
            case R.id.btn_view_principle:
                ViewPrincipleActivity.actionStart(MainActivity.this);
                break;
            default:
                //startActivity(new Intent(this, SensorActivity.class));
                try {
                    new TestParse().testPullCreate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
