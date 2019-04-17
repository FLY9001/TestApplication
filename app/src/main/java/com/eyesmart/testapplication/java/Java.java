package com.eyesmart.testapplication.java;

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

     */
}
