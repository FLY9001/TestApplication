package com.eyesmart.testapplication.java;

import java.lang.ref.SoftReference;

/**
 *
 */

public class Java {
    /**
     JVM:（Java Virtual Machine 简称JVM）
        Java虚拟机是运行所有Java程序的抽象计算机，是Java语言的运行环境，它是Java 最具吸引力的特性之一。
        一一对应，每个Java程序都运行于它自己的Java虚拟机实例中。
     1、垃圾回收器（Garbage Collection）：
        负责回收堆内存（Heap）中没有被使用的对象，即这些对象已经没有被引用了。
     2、类装载子系统（Classloader Sub-System）：
        除了要定位和导入二进制class文件外，还必须负责验证被导入类的正确性，为类变量分配并初始化内存，以及帮助解析符号引用。
     3、执行引擎（Execution Engine）：
        负责执行那些包含在被装载类的方法中的指令。
     4、运行时数据区（Java Memory Allocation Area）：
        又叫虚拟机内存或者Java内存，虚拟机运行时需要从整个计算机内存划分一块内存区域存储许多东西。例如：字节码、从已装载的class文件中得到的其他信息、程序创建的对象、传递给方法的参数，返回值、局部变量等等。

     内存划分为两种：一种是栈（stack）内存，一种是堆（heap）内存
        栈：存放 基本类型变量（不包括string）和引用变量，存取速度比堆快
        堆：存放 由new创建的对象和数组（基本数据类型包装类、变量实体）。在堆中分配的内存，由Java虚拟机的垃圾回收器来管理。

     -------------------------------------------------------------------------------------------------------------------------------
     内存中各类型的存储：
     String类型比较：
        String str = “AA”;              栈-->字符串常量池       栈中str的引用为字符串常量池的AA的地址
        String str = new String(“AA”);  栈-->堆-->字符串常量池  栈中str的引用为堆中new String()的地址，new String()里存储字符串常量池的AA的地址

     基本数据类型包装类型比较：
        Integer i1 = new Integer(3);
        Integer i2 = new Integer(3);
        Integer j1 = 3; 即 Integer j1 = Integer.valueOf(3);
        Integer j2 = 3；
     new Integer()每次都会创建新的对象；所以i1 == i2为false；
     Integer.valueOf()方法创建的 -128~127区间 的Integer对象只存在一个包装对象，所以j1 == j2为true；
     基本数据类型与包装类进行==比较，会自动拆箱，直接比较值，如j1 == 3为true

     Character（0~127）、Byte、Short、Integer、Long（-128~127），有同样的缓存机制

     -------------------------------------------------------------------------------------------------------------------------------
     内存中对象的引用方式:
        Java中负责内存回收的是JVM。通过JVM自动回收内存，虽省心但不灵活，
        为了解决内存操作不灵活的问题，我们可以通过Java的引用方式来解决这个问题。

     1．强引用（Strong Reference）
        最普遍的引用就是强引用，绝不会被回收。当内存空间不足，宁愿抛出OOM，异常终止程序，也不会靠回收它来解决内存不足问题。
     2．软引用（SoftReference）
        一个只具有软引用的对象，只有内存不足，才会被回收。使用软引用，可解决此对象引起的OOM问题。软引用可用来实现内存敏感的高速缓存。
        软引用可以和一个引用队列（ReferenceQueue）联合使用，如果软引用所引用的对象被垃圾回收，JVM就会把这个软引用加入到与之关联的引用队列中。
     3．弱引用（WeakReference）
        一个只具有弱引用的对象，即使内存充足，也有可能被回收，全靠垃圾回收器是不是扫描到它。
        弱引用可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被垃圾回收，JVM就会把这个弱引用加入到与之关联的引用队列中。
     4．虚引用（PhantomReference）
        "虚引用"顾名思义，就是形同虚设，任何时候都可能被垃圾回收。主要用来跟踪监听对象是否被垃圾回收。
        虚引用与软引用和弱引用的一个区别在于：虚引用必须和引用队列（ReferenceQueue）联合使用。
        当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。
        程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。程序如果发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动。
     */
    public void test() {
        A a = new A();
        /**软引用的使用*/
        SoftReference<A> sr = new SoftReference<A>(a);
        a = null;
        /**如过对象没有被回收，则可以重新得到*/
        if (sr != null) {
            a = sr.get();
        } else {
            a = new A();
            sr = new SoftReference<A>(a);
        }
    }

    class A {
        int[] a;
        public A() {
            a = new int[100000000];
        }
    }

    /**
    1.解决Handler可能造成的内存泄露 -- 使用弱引用
        1)静态类不持有外部类的对象，使用静态内部类Handler；
        2)因为不持有外部类的对象，所以要把activity对象传进去，此时可对activity弱引用
     正经的解决方法其实是：handler.removeCallbacksAndMessages(null);！！！！

    2.解决图片加载时，可能造成的内存不足问题 -- 使用软引用
        使用软引用相对使用强引用，在图片加载方面能够很明显的提升性能，并减少崩溃的几率，与Lru算法指定LruCache能够更好的去管理，因为增加了根据图片使用频率来管理内存的算法，相比较更加合理和人性化。
    */
//    class SampleActivity extends Activity {
//        /**
//         * Instances of static inner classes do not hold an implicit
//         * reference to their outer class.
//         */
//        private static class MyHandler extends Handler {
//            private final WeakReference<SampleActivity> mActivity;
//            public MyHandler(SampleActivity activity) {
//                mActivity = new WeakReference<SampleActivity>(activity);
//            }
//            @Override
//            public void handleMessage(Message msg) {
//                SampleActivity activity = mActivity.get();
//                if (activity != null) {
//
//                }
//            }
//        }
//    }
}
