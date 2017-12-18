package com.eyesmart.testapplication.project;

/**
 * Created by FLY on 2017/12/18.
 */

public class ProjectCatalog {
    /**
     工程目录结构:
     app                  ***Module目录。

     .gradle和.idea          自动生成的文件，无须关心。
     build                   编译时自动生成的文件。

     gradle                  gradle wrapper的配置文件。Android Studio默认没有启动gradle wrapper的方式，如果需要打开，可以点击Android Studio导航栏 --> File --> Settings --> Build，Execution，Deployment --> Gradle，进行配置更改。
     gradle.properties       配置gradle脚本中的参数
     settings.gradle      ***配置项目中的Module。
     build.gradle         ***配置gradle构建脚本。
     gradlew和gradlew.bat    在命令行界面中执行gradle命令的，其中gradlew是在Linux或Mac系统中使用的，gradlew.bat是在Windows系统中使用的。

     local.properties     ***设置Android SDK、NDK路径。
     TestApplication.iml     iml用于标识这是一个IntelliJ IDEA项目。
     .gitignore              将指定的目录或文件排除在版本控制之外的。
     */
}
