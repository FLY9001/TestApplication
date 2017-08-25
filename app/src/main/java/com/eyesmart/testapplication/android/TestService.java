package com.eyesmart.testapplication.android;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

public class TestService extends Service {
    boolean binded = false;

    void test() {
        Intent intent = new Intent(this, TestService.class);
        startService(intent);       //调用onStartCommand(),多次启动多次回调
        stopService(intent);
        //------------------------------------------------------------------------
        ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //当连接成功时
                binded = true;
                MyBinder binder = (TestService.MyBinder) service;  //获取其实例
                TestService testService = binder.getService();
                testService.setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(int progress) {         //Service主动发出通知

                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                //当异常断开连接时
            }
        };
        //调用onBind()，多次绑定一次回调
        bindService(intent, conn, BIND_AUTO_CREATE);   //若没有，则自动创建，0为不自动
        if (binded) {
            unbindService(conn);                       //多次解绑会抛异常
            binded = false;
        }
    }

//****************************************************************************************************************

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    OnProgressListener onProgressListener;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public TestService getService() {
            return TestService.this;
        }
    }

    public interface OnProgressListener {
        void onProgress(int progress);
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }
}
