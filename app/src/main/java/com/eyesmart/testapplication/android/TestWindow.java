package com.eyesmart.testapplication.android;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Window，通过Activity、Dialog、Toast、自定义的视图（悬浮窗） 呈现
 * https://www.cnblogs.com/deman/p/5641765.html
 */

public class TestWindow {
    public static void creatWindow(Context context) {
        /**LayoutParams*/
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        //wmParams = ((Activity) context).getWindow().getAttributes();  //activity获取

        //type，窗口类型；1~2999，层级越大越顶层
        //分为 应用Window（1~99，如通过Activity）、子Window（1000~1999，需附属父Window，如Dialog、PopupWindow）、系统window（2000~2999，需声明权限，如Toast和状态栏）
        //android.permission.SYSTEM_ALERT_WINDOW 允许程序显示系统窗口
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        //flags，显示特性； https://blog.csdn.net/qq_28535319/article/details/82790331
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;     //锁屏界面
        //设置位置，以屏幕左上角为原点
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        //wmParams.x = 0;
        //wmParams.y = 0;
        //设置长宽
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.format = 1;

        /**WindowManager*/
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        /**add、update、remove*/
        wm.addView(new View(context), wmParams);
        wm.updateViewLayout(new View(context), wmParams);
        wm.removeView(new View(context));
        //((Activity) context).getWindow().setAttributes(wmParams);     //activity设置
    }

    /**
     * 1、activity
     */
    public static void activityWindow(Activity activity) {
        WindowManager.LayoutParams wmParams = activity.getWindow().getAttributes();  //获取window
        activity.getWindow().setAttributes(wmParams);                                //设置window

        activity.getWindow().getDecorView();                                         //获取DecorView

        //activity与Window相关的生命周期回调
        activity.onAttachedToWindow();
        activity.onDetachedFromWindow();
        activity.dispatchTouchEvent(null);
    }
}
