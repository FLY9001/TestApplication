package com.eyesmart.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eyesmart.testapplication.android.ADB;
import com.eyesmart.testapplication.android.Android;
import com.eyesmart.testapplication.android.AppInfoUtils;
import com.eyesmart.testapplication.android.HttpUtils;
import com.eyesmart.testapplication.android.HybridActivity;
import com.eyesmart.testapplication.android.IPC;
import com.eyesmart.testapplication.android.PermissionActivity;
import com.eyesmart.testapplication.android.Safety;
import com.eyesmart.testapplication.android.SensorActivity;
import com.eyesmart.testapplication.android.SystemInfoUtils;
import com.eyesmart.testapplication.android.TestActivity;
import com.eyesmart.testapplication.android.TestBitmap;
import com.eyesmart.testapplication.android.TestButterKnife;
import com.eyesmart.testapplication.android.TestDatabase;
import com.eyesmart.testapplication.android.TestEventBus;
import com.eyesmart.testapplication.android.TestFragment;
import com.eyesmart.testapplication.android.TestHandler;
import com.eyesmart.testapplication.android.TestIO;
import com.eyesmart.testapplication.android.TestJni;
import com.eyesmart.testapplication.android.TestMedia;
import com.eyesmart.testapplication.android.TestMemory;
import com.eyesmart.testapplication.android.TestNet;
import com.eyesmart.testapplication.android.TestProvider;
import com.eyesmart.testapplication.android.TestReceiver;
import com.eyesmart.testapplication.android.TestRxJava;
import com.eyesmart.testapplication.android.TestService;
import com.eyesmart.testapplication.android.TestUI;
import com.eyesmart.testapplication.android.TestWindow;
import com.eyesmart.testapplication.android.TestXmlJson;
import com.eyesmart.testapplication.android.Version;
import com.eyesmart.testapplication.java.APIs;
import com.eyesmart.testapplication.java.Java;
import com.eyesmart.testapplication.java.MyCollection;
import com.eyesmart.testapplication.java.MyGenericity;
import com.eyesmart.testapplication.java.MyIO;
import com.eyesmart.testapplication.java.MyReflect;
import com.eyesmart.testapplication.java.MyThread;
import com.eyesmart.testapplication.java.TestArchitecture;
import com.eyesmart.testapplication.java.TestArithmetic;
import com.eyesmart.testapplication.java.TestDesignPattern;
import com.eyesmart.testapplication.project.Resource;
import com.eyesmart.testapplication.ui.CameraTextureView;
import com.eyesmart.testapplication.ui.TestAnim;
import com.eyesmart.testapplication.ui.TestView;
import com.eyesmart.testapplication.ui.viewprinciple.AnalogClock;
import com.eyesmart.testapplication.ui.viewprinciple.StaggerLayout;
import com.eyesmart.testapplication.ui.viewprinciple.ViewPrincipleActivity;
import com.eyesmart.testapplication.ui.viewwidget.DrawView;
import com.eyesmart.testapplication.ui.viewwidget.ListViewFragment;
import com.eyesmart.testapplication.ui.viewwidget.RecyclerFragment;
import com.eyesmart.testapplication.ui.viewwidget.ViewWidgetActivity;

/**
 * 基础教程：https://www.kancloud.cn/kancloud/android-tutorial/87287
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
                Java.class,             //JVM、内存
                APIs.class,             //Runtime、System、字符操作、数据日期格式化
                MyIO.class,             //数据流
                MyCollection.class,     //集合、数组、栈、属性类
                MyThread.class,         //线程基础

                MyGenericity.class,     //枚举、泛型
                MyReflect.class,        //反射、注解
        };
        /**android工程*/
        Class[] project = {
                Android.class,          //Android系统架构、开发框架
                Safety.class,           //安全机制、反编译
                ADB.class,              //adb
                Git.class,              //git

                SystemInfoUtils.class,  //硬件、系统信息
                AppInfoUtils.class,     //各个应用信息

                PermissionActivity.class,//权限
                Version.class
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
                IPC.class,              //多进程

                TestIO.class,           //存储
                TestDatabase.class,     //数据库
                TestNet.class,          //Socket
                HttpUtils.class,        //Http TODO 组合框架
                TestXmlJson.class,      //数据解析

                TestMedia.class,        //多媒体：音频、视频
                CameraTextureView.class,//相机
                SensorActivity.class,   //传感器

                TestJni.class,          //Jni的编译、应用
        };
        /**UI*/
        Class[] view = {
                DisplayUtil.class,
                Resource.class,         //资源文件

                TestWindow.class,       //显示窗口
                TestView.class,         //坐标系、初始化；绘制流程、事件体系
                TestAnim.class,         //动画

                /**自定义控件*/
                //继承已有View(如密码输入EditText)
                //继承已有ViewGroup(组合内部控件，inflate)
                AnalogClock.class,      //直接继承View(自定义属性，重写onDraw)
                StaggerLayout.class,    //直接继承ViewGroup(重写onLayout)

                /**图像显示及处理*/
                //Resource.drawable();  (drawable)图片设置、层叠、变换
                DrawView.class,         //Paint、Canvas绘制图形
                TestBitmap.class,       //ColorMatrix、Pixels颜色效果，Matrix、Mesh变换扭曲

                ListViewFragment.class,
                RecyclerFragment.class,

                HybridActivity.class,   //混合开发
        };
        /**android代码优化*/
        //https://www.jianshu.com/p/a4c3c32fa5ab
        Class[] android2 = {
                TestUI.class,           //布局优化
                TestMemory.class,       //TODO 内存泄漏、优化、图片优化

                TestRxJava.class,       //TODO 原理及应用
                TestEventBus.class,     //事件总线
                TestButterKnife.class,  //依赖注入
                //TODO 热修复
        };
        Class[] android3 = {
                TestDesignPattern.class,//设计模式
                TestArchitecture.class, //架构模式
                TestArithmetic.class    //TODO 算法
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
                startActivity(new Intent(this, TestButterKnife.class));
                break;
        }
    }
}
