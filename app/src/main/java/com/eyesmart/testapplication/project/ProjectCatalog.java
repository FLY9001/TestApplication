package com.eyesmart.testapplication.project;

import com.eyesmart.testapplication.R;

/**
 * Created by FLY on 2017/12/18.
 */

public class ProjectCatalog {
    /**工程目录结构:
     * app                  ***Module目录。
     * <p>
     * .gradle和.idea          自动生成的文件
     * build
     * <p>
     * gradle                  gradle wrapper的配置文件。Android Studio默认没有启动gradle wrapper的方式，如果需要打开，可以点击Android Studio导航栏 --> File --> Settings --> Build，Execution，Deployment --> Gradle，进行配置更改。
     * gradle.properties       配置gradle全局参数
     * settings.gradle      ***配置项目Module。
     * build.gradle         ***配置gradle构建脚本。
     * gradlew和gradlew.bat    可执行文件，其中gradlew是在Linux或Mac系统中使用的，gradlew.bat是在Windows系统中使用的。
     * <p>
     * local.properties     ***设置Android SDK、NDK路径。
     * TestApplication.iml     iml用于标识这是一个IntelliJ IDEA项目。
     * .gitignore              git使用的ignore文件。
     */

    /**system目录：
     * app                     系统app
     * bin                     linux自带组件
     * fonts                   字体
     * framework               系统框架层
     * lib                     存放so文件
     * media                   系统声音
     * usr                     用户配置文件
     */

    /**sdk目录：
     * add-ons                 附加库
     * build-tools             编译工具：aapt、aidl、dexdump、dx等
     * docs                    开发文档
     * emulator                模拟器
     * extras                  拓展开发包：v4、v7、Intel硬件加速程序等
     * platforms               开发平台，SDK真正文件
     * platform-tools          开发工具：adb、aapt、aidl、dx等
     * samples                 示例工程
     * sources                 SDK源码
     * system-images           系统镜像(模拟器的镜像文件)
     * tools                   开发和调试的工具：ddms、logcat、draw9patch
     */
    void test() {
        int sdk_manager = R.drawable.sdk_manager;
    }

}
