package com.eyesmart.testapplication.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * Created by Tian on 2017-7-31 0031.
 */

public class TestIO {
    void testSP(Context context) {
        //文件位置：/data/data/<包名>/share_prefs/xxx.xml
        SharedPreferences sp = context.getSharedPreferences("share_data", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("key", "value");   //增、改
        editor.remove("key");               //删除
        editor.clear();                     //清空
        editor.apply();                     //异步提交，节省资源，注意API版本

        sp.contains("key");                 //是否包含
        sp.getString("key", "defValue");    //得到
        Map<String, ?> all = sp.getAll();   //得到所有
    }

    void testFile(Context context) throws FileNotFoundException {
        //文件位置：/data/data/<包名>/files/xxx.xx
        FileInputStream fis = context.openFileInput("test.txt");
        FileOutputStream fos = context.openFileOutput("test.txt", Context.MODE_PRIVATE);//MODE_APPEND以追加的方式

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory(); // /mnt/sdcard

            //文件位置：/mnt/sdcard/Android/data/<包名>/files/xxx
            context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        }
    }
}
