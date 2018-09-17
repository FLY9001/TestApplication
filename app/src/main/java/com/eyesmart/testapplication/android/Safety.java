package com.eyesmart.testapplication.android;

/**
 * Android 安全机制
 */

public class Safety {
    /**
     * Android系统安全五道防线：
     * 1、代码安全机制     代码混淆    应用proguard，混淆代码，让反编译出的源码阅读困难；压缩代码，优化编译后字节码
     * 2、应用权限控制    permission   声明相应权限才能申请相应功能
     * 3、应用签名机制     数字证书    App必须有签名，签名保护App作者，同一数字证书升级时才会被认为是同一App
     * 4、内核层安全机制   访问权限    只有System、root用户才有权限访问系统文件
     * 5、虚拟沙箱机制     沙箱隔离    App运行在单独的虚拟机中，相互隔离，保证安全；一App崩溃，不影响其他
     */

    /**
     * 反编译：
     *
     * 反编译xml文件：
     * 1、工具：apktool（直接解压apk，xml文件会乱码）
     *    命令行：apktool.jar d test.apk（反编译），apktool.jar b test（重新编译），apk文件在dist文件夹
     *
     * 反编译java代码：
     * 1、解压apk文件，得到classes.dex
     * 2、工具：dex2jar     命令行：d2j-dex2jar.bat classes.dex，生成classes-dex2jar.jar
     * 3、工具：jd-gui      打开文件classes-dex2jar.jar
     */
}
