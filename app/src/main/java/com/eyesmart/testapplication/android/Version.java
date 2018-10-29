package com.eyesmart.testapplication.android;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.graphics.Palette;

import com.eyesmart.testapplication.ui.viewwidget.RecyclerFragment;

/**
 * Created by 1 on 2018/10/25 0025.
 */

public class Version {
    void test() {
        int sdkInt = Build.VERSION.SDK_INT;     //获取设备系统版本
        int androidO = Build.VERSION_CODES.O;   //Android8.0，即26

        version5();     //5.0新特性
        version6();     //6.0新特性
    }

    /**
     * Android5.0 新特性：
     * 1、Material Design    TODO
     * 2、支持多种设备，如平板、手表、智能电视、汽车
     * 3、全新的通知中心
     * 4、64位ART虚拟机      TODO
     * 5、Overview多任务窗口
     * 6、设备识别解锁
     * 7、Ok Google语音命令
     * 8、Face unlock面部解锁
     */
    void version5() {
        //RecyclerFragment
        new RecyclerFragment();
        //CardView，继承FrameLayout，可设置 圆角和阴影，具有立体性
        //Notification，3种：普通、折叠、悬挂
        new NotificationUtils(null);
        //Toolbar
        //Palette获取颜色
        Bitmap bitmap = null;
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //得到一个调色板，可以获取颜色
                Palette.Swatch swatch = palette.getLightVibrantSwatch();//获取亮色
                if (null != swatch) {
                    int color = swatch.getRgb();
                }
            }
        });
    }

    /**
     * Android6.0 新特性：
     * 1、应用权限管理     TODO
     * 2、Android Pay
     * 3、指纹支持，统一指纹识别
     * 4、Doze电量管理
     * 5、App Links，加强软件关联，支持app直接跳转
     * 6、Now on Tap
     */
    void version6() {
        new PermissionActivity();
    }

    /**
     * Android7.0 新特性：
     * 1、多窗口模式
     * 2、Data SAVER，流量保护机制
     * 3、Java8语言支持
     * 4、自定义壁纸
     * 5、快速回复
     * 6、Daydream VR平台支持
     * 7、后台省电
     * 8、快速开关设置
     * 9、Unicode 9支持，全新emoji表情符号
     * 10、Google Assistant
     */
    void version7() {
        //禁用分屏
        //android:resizeableActivity="false"
    }
}
