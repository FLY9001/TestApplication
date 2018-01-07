package com.eyesmart.testapplication.android;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

/**
 * RAM分为寄存器、栈（Stack）、堆（Heap）、静态存储区域、常量池
 * 堆中内存不会立即被回收，会等待系统GC。内存优化即针对堆优化
 * GC：Garbage Collection Thread，垃圾回收器线程
 * <p>
 * 内存属性位置：/system/build.prop  //adb shell getprop dalvik.vm.heapstartsize查看
 * dalvik.vm.heapstartsize=8m       //堆分配的初始大小
 * dalvik.vm.heapgrowthlimit=64m    //单个应用可用最大内存
 * dalvik.vm.heapsize=384m          //单个进程可用最大内存,Manifest中设置android:largeHeap="true"时以此为准 （GC运行时间更长,影响体验）
 * <p>
 * 内存优化：
 * 1、Bitmap优化：
 * ---1.使用适当的分辨率和大小、图片缓存（LruCache、DiskCache）
 * 2、代码优化：
 * ---1.常量使用static；方法使用静态（static），访问速度提高15%
 * ---2.尽量使用局部成员、基本类型；不使用枚举、迭代器
 * ---3.注意Curser、Receiver、Sensor等对象的回收、解注册
 * ---4.使用SurfaceView、OpenGL、RenderScript进行复制绘图
 */

public class TestMemory {
    void test(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryClass();                        //dalvik.vm.heapgrowthlimit
        int heapSize = am.getLargeMemoryClass();    //dalvik.vm.heapsize

        //Memory Monitor

        //TraceView工具
        Debug.startMethodTracing();     //默认路径：/sdcard/dmtrace.trace
        Debug.stopMethodTracing();

        //MAT工具
    }

    /**
     * 内存泄露：对象无法被GC回收，大量的内存泄露会导致内存溢出(oom)
     * LeakCanary
     */
    void testMemoryLeak() {

    }
}
