package com.eyesmart.testapplication.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

/**
 * https://mp.weixin.qq.com/s?__biz=MzU4MTIzMjM3MA==&mid=2247483887&idx=3&sn=153693beef649ee6982fca56809a2afe&chksm=fd4bf272ca3c7b6420c43cbd4dffb8d63197cf5c680b3785ecd20dac5021da261d89278bd8aa&scene=21#wechat_redirect
 */
public class TestReceiver extends BroadcastReceiver {
    Context context;

    void test() {
        /**1、创建*/
        //覆写onReceive();
        /**2、注册、解注册*/
        TestReceiver tr = new TestReceiver();       //动态注册（静态注册，AndroidManifest.xml）
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.eyesmart.testapplication.android.TestReceiver");
        context.registerReceiver(tr, filter);       //最好在onResume

        context.unregisterReceiver(tr);             //最好在onPause，保证一定会被注销，防止内存泄露；
        //因为onPause一定会执行，而onStop、onDestroy在activity“内存销毁”时不会执行
        //有注册就必然得有注销，否则会导致内存泄露
        //重复注册、重复注销也不允许


        /**发送广播*/
        Intent intent = new Intent("com.eyesmart.testapplication.android.TestReceiver");
        context.sendBroadcast(intent);

        /**有序广播*/
        context.sendOrderedBroadcast(intent, null);
        //按优先级传播，priority（-1000~1000），null为不指定权限
        //Priority属性相同者，动态注册的广播优先
        //可以对广播进行截断、修改

        /**局部广播*/
        //通过exported设为false
        //通过permission
        //通过intent.setPackage(packageName)指定报名
        //通过LocalBroadcastManager：
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.registerReceiver(tr, filter);
        manager.unregisterReceiver(tr);
        manager.sendBroadcast(intent);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String value = intent.getStringExtra("key");

        //传递附加数据
        Bundle bundle = new Bundle();
        bundle.putString("msg", value + "@TestReceiver");
        setResultExtras(bundle);
        //得到附加数据
        String msg = getResultExtras(true).getString("msg");
        //中止广播
        abortBroadcast();
    }
}
