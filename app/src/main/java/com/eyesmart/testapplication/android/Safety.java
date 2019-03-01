package com.eyesmart.testapplication.android;

/**
 * Android 安全机制
 * 权限：
 * https://blog.csdn.net/koozxcv/article/details/50835601
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
    /**
     *（一）linux文件系统上的权限
     *      apk程序是运行在虚拟机上的,对应的是Android独特的权限机制，只有体现到文件系统上时才使用linux的权限设置。
     *（二）Android的权限规则
     *      （1）Android中的apk必须签名
     *          system等级的权限有专门对应的签名，签名不对，权限也就获取不到。
     *      （2）基于UserID的进程级别的安全机制
     *          进程有唯一的linux userID，有独立的地址空间，进程与进程间默认是不能互相访问的
     *          在AndroidManifest.xml中利用sharedUserId属性给不同的app分配相同的userID，相同的签名，通过这样做，两个package可以被当做同一个程序，
     *      （3）默认apk生成的数据对外是不可见的
     *      （4）AndroidManifest.xml中的显式权限声明
     *（三）常见权限不足问题分析
     *      通过system/app安装的apk的权限一般比直接安装的apk的权限要高一些。
     *      目前所知的apk能提升到的权限就是system
     *      （1）apk 获取system权限
     *          1. 加入android:sharedUserId="android.uid.system"这个属性。
     *          2. 修改Android.mk文件，加入LOCAL_CERTIFICATE := platform这一行
     *          3. 使用mm命令来编译，生成的apk就有修改系统时间的权限了。
     *      （1）apk 获取root权限
     */
}
