package com.eyesmart.testapplication.project;

/**
 * Created by FLY on 2017/12/18.
 */

public class ProjectCatalog {
    /**
     * 工程目录结构:
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

}
