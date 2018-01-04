package com.eyesmart.testapplication.android;

import android.os.Build;
import android.util.Log;

/**
 * 设备、系统信息
 */

public class SystemInfoUtils {
    public static void test() {
        get_android_os_build();     //android.os.Build 获取硬件信息
        get_system_property();      //System.getProperty 获取系统属性
        //文件：/system/build.prop 包含系统信息
    }

    /**
     * android.os.Build 获取硬件信息
     */
    public static String get_android_os_build() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n主板： " + Build.BOARD);
        sb.append("\n系统启动程序版本号：" + Build.BOOTLOADER);
        sb.append("\n系统定制商： " + Build.BRAND);
        sb.append("\ncpu指令集： " + Build.CPU_ABI);
        sb.append("\ncpu指令集2 " + Build.CPU_ABI2);
        sb.append("\n设备参数： " + Build.DEVICE);
        sb.append("\n显示屏参数： " + Build.DISPLAY);
        sb.append("\n无线电固件版本： " + Build.getRadioVersion());
        sb.append("\n硬件识别码（唯一编号）： " + Build.FINGERPRINT);
        sb.append("\n硬件名称： " + Build.HARDWARE);
        sb.append("\nHOST: " + Build.HOST);
        sb.append("\n修订版本列表： " + Build.ID);
        sb.append("\n硬件制造商： " + Build.MANUFACTURER);
        sb.append("\n版本： " + Build.MODEL);
        sb.append("\n硬件序列号： " + Build.SERIAL);
        sb.append("\n手机产品名： " + Build.PRODUCT);
        sb.append("\n描述Build的标签： " + Build.TAGS);
        sb.append("\n编译时间TIME: " + Build.TIME);
        sb.append("\nBuilder类型： " + Build.TYPE);
        sb.append("\nUser名: " + Build.USER);
        Log.d("TAG", "android.os.Build: " + sb.toString());
        return sb.toString();
    }

    /**
     * System.getProperty 获取系统属性
     */
    public static String get_system_property() {
        StringBuffer sb = new StringBuffer();
        sb.append("\nOS版本：" + System.getProperty("os.version"));
        sb.append("\nOS名称：" + System.getProperty("os.name"));
        sb.append("\nOS架构：" + System.getProperty("os.arch"));
        sb.append("\nHOME属性： " + System.getProperty("user.home"));
        sb.append("\nName属性 " + System.getProperty("user.name"));
        sb.append("\nDir属性： " + System.getProperty("user.dir"));
        sb.append("\n时区： " + System.getProperty("user.timezone"));
        sb.append("\n路径分隔符： " + System.getProperty("path.separator"));
        sb.append("\n行分隔符： " + System.getProperty("line.separator"));
        sb.append("\n文件分隔符： " + System.getProperty("file.separator"));
        sb.append("\nJava Class 路径： " + System.getProperty("java.class.path"));
        sb.append("\nJava Class 版本： " + System.getProperty("java.class.version"));
        sb.append("\nJava Vender 属性： " + System.getProperty("java.vendor"));
        sb.append("\nJava vender URL： " + System.getProperty("java.vendor.url"));
        sb.append("\nJava 版本： " + System.getProperty("java.version"));
        sb.append("\nJava Home属性： " + System.getProperty("java.home"));
        Log.d("TAG", "System.getProperty: " + sb.toString());
        return sb.toString();
    }
}
