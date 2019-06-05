package com.eyesmart.testapplication.android;

import com.eyesmart.testapplication.R;

/**
 * Android系统的架构、源码、目录
 * 开发的SDK目录、工程目录
 */

public class Android {
    void test() {
        /**
         ******************架构********************************************
         Application：               应用层                 app
         Framework：                 应用框架层             APIS
         Libraries(Android Runtime)  系统运行库层           各种C、C++本地库和虚拟机（Dalvik、ART）
         HAL                         硬件抽象层             硬件控制接口层
         Linux Kernel：              Linux内核层            驱动程序

         Android 4.4以上 时虚拟机由 Dalvik 变为 ART
         Dalvik：运行时才编译
         ART：   安装时就编译
         */
        int framwork = R.drawable.framwork; //架构图

        /**
         ******************Android源码********************************************
         目录网站:http://androidxref.com
         源码目录结构：（见图）
         Makefile：指导自动化编译
         */
        int manager = R.drawable.manager;       //应用框架层组件
        int libraries = R.drawable.libraries;   //C、C++本地库


        int source = R.drawable.source;         //源码目录结构
        int source2 = R.drawable.source20;      //源码目录结构

        int app_source = R.drawable.source_packages;        //packages目录；存放着系统核心应用程序、第三方的应用程序和输入法等
        int frameworks_base = R.drawable.source_frameworks; //frameworks/base目录，对应系统框架层；向上提供接口给应用层调用，向下与C/C++程序库以及硬件抽象层等进行衔接
        int lib_source = R.drawable.source_lib;             //部分C、C++本地库目录
        //硬件抽象层的代码放在 hardware/目录中，这是手机厂商改动最大的一部分，根据手机终端所采用的硬件平台会有不同的实现

        /**
         ******************sdk目录********************************************
         sdk目录：
         add-ons                 附加库，一般为第三方公司的附加库，供开发者使用
         build-tools             编译工具：aapt、aidl、dexdump、dx等
         docs                    开发文档
         emulator                模拟器
         extras                  拓展开发包：v4、v7、Intel硬件加速程序等
         platforms               开发平台，SDK真正文件
         platform-tools          开发工具：adb、aapt、aidl、dx等
         samples                 示例工程
         sources                 SDK源码
         system-images           系统镜像(模拟器的镜像文件)
         tools                   开发和调试的工具：ddms、logcat、draw9patch
         */
        int sdk_ = R.drawable.sdk_manager;  //SDK管理器详情

        /**
         ******************工程目录********************************************
         app                  ***Module目录。
         <p>
         .gradle和.idea          自动生成的文件
         build
         <p>
         gradle                  gradle wrapper的配置文件。Android Studio默认没有启动gradle wrapper的方式，如果需要打开，可以点击Android Studio导航栏 --> File --> Settings --> Build，Execution，Deployment --> Gradle，进行配置更改。
         gradle.properties       配置gradle全局参数
         settings.gradle      ***配置项目Module。
         build.gradle         ***配置gradle构建脚本。
         gradlew和gradlew.bat    可执行文件，其中gradlew是在Linux或Mac系统中使用的，gradlew.bat是在Windows系统中使用的。
         <p>
         local.properties     ***设置Android SDK、NDK路径。
         TestApplication.iml     iml用于标识这是一个IntelliJ IDEA项目。
         .gitignore              git使用的ignore文件。
         */

        /**
         ******************系统system目录********************************************
         system目录：
         app                     系统app
         bin                     linux自带组件
         fonts                   字体
         framework               系统框架层
         lib                     存放so文件
         media                   系统声音
         usr                     用户配置文件

         build.prop              系统信息
         */

        /**
         ******************系统目录********************************************
         /acct:
         系统回收站

         /cache:
         缓存

         /config：
         计算机系统配置

         /data：
         程序目录

         /dev：
         设备文件，dev是Device(设备)的缩写, 该目录下存放的是Linux的外部设备，在Linux中访问设备的方式和访问文件的方式是相同的。

         /etc：
         系统主要配置文件

         /mnt：
         挂载点目录

         /proc：
         运行时文件，数据都在内存中，如系统核心，外部设备，网络状态

         /root：
         该目录为系统管理员，也称作超级权限者的用户主目录。

         /sbin：
         s就是Super User的意思，这里存放的是系统管理员使用的系统管理程序。

         /sys：
         Linux 内核文件

         /init.rc：
         启动脚本
         */
    }
}
