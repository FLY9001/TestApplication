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

public class PermissionActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 0;

    /**
     * Android 6.0 权限申请变化
     * 权限分为 正常权限、危险权限（运行时授予）
     * 危险权限：读取sdcard、访问通讯录等
     * Manifest.READ_PHONE_STATE
     * Manifest.permission.READ_EXTERNAL_STORAGE/WRITE_EXTERNAL_STORAGE
     * Manifest.permission.CAMERA
     * 不管是正常权限还是危险权限，都需要在应用的Manifest中申明!!!
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    //需要先在应用的Manifest中申明!!!
    void test() {
        //判断当前系统版本是都是6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检测是否已经被用户授予该权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 如果没有被授予权限
                // 判断如果已经被用户拒绝过一次(非永久拒绝)，则显示用户此权限的一些说明，这里可以用toast，当然也可以用自定义界面来显示给用户，如果是采用自定义界面来显示给用户，在有“确定”，“取消”等按钮的情况下，下面的“请求权限”步骤要酌情调用
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "request read external storage", Toast.LENGTH_LONG).show();
                }
                // 请求权限，数组传入多个值，可以一次请求多个权限
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
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    go();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    void go() {

    }
}
