package com.eyesmart.testapplication.android;

import android.app.ActivityManager;
import android.content.Context;

/**
 * 内存属性位置：/system/build.prop  //adb shell getprop dalvik.vm.heapstartsize查看
 * dalvik.vm.heapstartsize=8m       //堆分配的初始大小
 * dalvik.vm.heapgrowthlimit=64m    //单个应用可用最大内存
 * dalvik.vm.heapsize=384m          //单个进程可用最大内存,Manifest中设置android:largeHeap="true"时以此为准 （GC运行时间更长,影响体验）
 */

public class TestMemory {
    void test(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryClass();        //dalvik.vm.heapgrowthlimit

        am.getLargeMemoryClass();   //dalvik.vm.heapsize
    }

    /**
     * 内存泄露：对象无法被GC回收，大量的内存泄露会导致内存溢出(oom)
     * LeakCanary
     */
    void testMemoryLeak() {


    }
}
