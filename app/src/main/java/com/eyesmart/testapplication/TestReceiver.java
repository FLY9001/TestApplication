package com.eyesmart.testapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class TestReceiver extends BroadcastReceiver {
    Context context;

    void test() {
        //静态注册，AndroidManifest.xml
        //动态注册
        TestReceiver tr = new TestReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.eyesmart.testapplication.TestReceiver");
        context.registerReceiver(tr, filter);       //一般在onStart
        context.unregisterReceiver(tr);             //一般在onStop

        //发送广播
        Intent intent = new Intent(context, TestReceiver.class);
        intent.setAction("com.eyesmart.testapplication.TestReceiver");
        context.sendBroadcast(intent);
        context.sendOrderedBroadcast(intent, null); //有序广播，按优先级传播，priority（-1000~1000），null为不指定权限
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

        abortBroadcast();       //中止广播
    }
}
