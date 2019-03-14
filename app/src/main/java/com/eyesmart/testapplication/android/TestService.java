package com.eyesmart.testapplication.android;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.eyesmart.testapplication.IMyAidlInterface;
import com.eyesmart.testapplication.MainActivity;
import com.eyesmart.testapplication.R;

/**
 * http://www.jianshu.com/p/d963c55c3ab9
 * 服务，长生命周期、没有用户界面、后台运行
 */
public class TestService extends Service {
    void test() {
        /**生命周期*/
        onCreate();
        onStartCommand(null, 0, 0);
        onBind(null);
        onUnbind(null);
        onDestroy();

        Intent intent = new Intent(this, TestService.class);
        /**普通启动*/
        startService(intent);                       //调用onStartCommand()，多次启动多次回调
        stopService(intent);

        /**绑定启动*/
        bindService(intent, conn, BIND_AUTO_CREATE);//调用onBind()，多次绑定一次回调。若没有，则自动创建，0为不自动
        if (binded) {
            unbindService(conn);                    //多次解绑会抛异常
            binded = false;
        }

        /**前台Service*/
        onCreate();

        /**IntentService*/
        //1、构造器传入线程名称、覆写onHandleIntent()方法
        intent = new Intent(this, TestIntentService.class);
        startService(intent);
        //执行完成自动关闭

        /**远程Service*/
        //AIDL：Android Interface Definition Language，Android接口定义语言，远程Service进行跨进程通信
        //服务端：1、aidl文件夹下定义接口；2、Binder实现接口；3、清单文件设置为远程Service
        mBinder.asBinder();
        //客户端：1、复制aidl文件夹；2、绑定启动Service；3、IBinder转换为接口对象
        //参数与服务器端的action要一致,即"服务器包名.aidl接口文件名"
        intent = new Intent("com.eyesmart.testapplication.IMyAidlInterface");
        //Android5.0后无法只通过隐式Intent绑定远程Service，需要通过setPackage()方法指定包名
        intent.setPackage("com.eyesmart.testapplication");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    boolean binded = false;
    ServiceConnection conn = new ServiceConnection() {
        //当连接成功时
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binded = true;
            /**一般绑定时*/
            MyBinder binder = (TestService.MyBinder) service;
            TestService testService = binder.getService();
            testService.setOnProgressListener(new OnProgressListener() {//Service被动接受调用
                @Override
                public void onProgress(int progress) {                  //Service主动发出通知

                }
            });
            /**AIDL绑定时*/
            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            //iMyAidlInterface.basicTypes();
        }

        //当发生异常断开连接时
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
//****************************************************************************************************************

    @Override
    public void onCreate() {
        super.onCreate();
        //前台Service在下拉通知栏有显示通知，但后台Service没有
        //前台Service优先级较高，不会由于系统内存不足而被回收；普通后台Service优先级较低，当系统出现内存不足情况时，很有可能会被回收

        //添加下列代码将后台Service变成前台Service
        //构建"点击通知后打开MainActivity"的Intent对象
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        //新建Notification对象
        Notification notification = new Notification.Builder(this)
                .setContentTitle("前台服务通知的标题")   //设置通知的标题
                .setContentText("前台服务通知的内容")    //设置通知的内容
                .setSmallIcon(R.mipmap.ic_launcher)      //设置通知的图标
                .setContentIntent(pendingIntent)         //设置点击通知后的操作
                .build();
        startForeground(1, notification);            //让Service变成前台Service,并在系统的状态栏显示出来
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    Binder myBinder = new MyBinder();

    public class MyBinder extends Binder {
        public TestService getService() {
            return TestService.this;
        }
    }

    OnProgressListener onProgressListener;

    public interface OnProgressListener {
        void onProgress(int progress);
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }
//****************************************************************************************************************

    /**
     * AIDL
     */
    IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
}
//****************************************************************************************************************

//与后台线程相比，IntentService是一种后台服务，优点是：优先级高（不容易被系统杀死），从而保证任务的执行
class TestIntentService extends IntentService {
    public TestIntentService() {
        super("TestIntentService"); //工作线程的名字
    }

    @Override
    public void onCreate() {
        //1、创建了一个HandlerThread及其Handler
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        //2、向Handler发送包含intent的Message，由handleMessage()最终交给onHandleIntent()
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //TODO  在线程中自定义操作
    }
}
