package com.eyesmart.testapplication.android;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.eyesmart.testapplication.TestApplication;

/**
 * Created by 1 on 2018/1/14 0014.
 */

public class Permission {
    /**
     * Android 6.0 权限申请变化
     * 权限分为 正常权限、危险权限（运行时授予）
     * 危险权限：读取sdcard、访问通讯录等
     * 不管是正常权限还是危险权限，都需要在应用的Manifest中申明!!!
     */
    void test() {
        //检查某个权限是否被授权
        int isGranted = ContextCompat.checkSelfPermission(TestApplication.getContext(), Manifest.permission.READ_CONTACTS);
        if (isGranted != PackageManager.PERMISSION_GRANTED){

        }else {
            //ActivityCompat.requestPermissions(TestApplication.getContext(),new String[0],1);

        }
    }
}
