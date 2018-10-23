package com.eyesmart.testapplication.android;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by FLY on 2018/10/23 0023.
 */

public class TestWindow {
    public static void creatWindow(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        //type
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        //flags，悬浮窗不获得焦点,不限制边界
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

        //设置位置，以屏幕左上角为原点
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        //wmParams.x = 0;
        //wmParams.y = 0;
        // 置长宽
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.format = 1;

        wm.addView(new View(context), wmParams);
    }
}
