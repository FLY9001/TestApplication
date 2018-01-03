package com.eyesmart.testapplication.android;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.eyesmart.testapplication.R;
import com.eyesmart.testapplication.TestApplication;

import java.util.List;

/**
 * 获取应用程序信息，可根据条件查找app中：activity、service、permission、配置信息等……
 */

public class AppInfoUtils {
    Context context = TestApplication.getContext();

    void test() throws PackageManager.NameNotFoundException {
        int manifest_info = R.drawable.manifest_info;
        /**PackageManager，获取应用的包信息*/
        PackageManager pm = context.getPackageManager();
        //PackageInfo
        PackageInfo packageInfo = pm.getPackageInfo("com.eyesmart.testapplication", PackageManager.GET_ACTIVITIES);
        //ApplicationInfo
        List<ApplicationInfo> applicationInfoList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        //ActivityInfo
        ComponentName componentName = new ComponentName("com.eyesmart.testapplication", "com.eyesmart.testapplication.MainActivity");
        //ActivityInfo activityInfo = pm.getActivityInfo(componentName, PackageManager.GET_ACTIVITIES);

        /**ActivityManager，获取正在运行的应用信息，包括内存信息*/
        //https://www.cnblogs.com/waleyx/p/3649520.html
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }
}