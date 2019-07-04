package com.eyesmart.testapplication.android;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.eyesmart.testapplication.R;

/**
 * Android 6.0 权限申请变化
 * 权限分为 正常权限、危险权限（运行时授予）
 * 正常权限：联网，震动等
 * 危险权限：读取sdcard、读取联系人、相机、定位等涉及用户隐私的
 * 不管是正常权限还是危险权限，都需要在应用的Manifest中申明!!!
 *
 * 危险权限组，adb命令查看：pm list permissions -g -d
 * 读写联系人、电话、日历信息（用户日程安排）
 * 相机、多媒体信息、传感器、位置信息
 * 读写存储卡、SMS卡
 *
 * 一句话权限适配：https://juejin.im/post/5cc184cee51d453f151c7fa6
 */
public class PermissionActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        test();
    }

    /**
     * 1、需要先在应用的Manifest中申明!!!
     */
    void test() {
        /**2、检查是否6.0以上*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /**3、检查是否有权限*/
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                /**3.5、判断如果已经被用户拒绝过一次*/
                //应用第一次请求权限被拒绝以后，这个方法会返回true
                //判断如果已经被用户拒绝过一次(非永久拒绝)，则显示用户此权限的一些说明，这里可以用toast，当然也可以用自定义界面来显示给用户，如果是采用自定义界面来显示给用户，在有“确定”，“取消”等按钮的情况下，下面的“请求权限”步骤要酌情调用
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "request read external storage", Toast.LENGTH_LONG).show();
                }
                /**4、请求权限*/
                //数组传入多个值，可以一次请求多个权限；此时会弹出一个系统级的对话框（无法自定义）
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUESTCODE);
            } else {
                go();
            }
        } else {
            // 6.0以下版本，直接使用权限
            go();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUESTCODE: {
                /**5、如果被授权*/
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    go();
                } else
                    //授权失败后，再次请求权限的话，依然会有系统级的弹框提示，只是多一个"不再提示"的单选框
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    void go() {

    }
}
