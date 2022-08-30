package com.eyesmart.testapplication.java;

/**
 * Leetcode算法图解:https://github.com/MisterBooo/LeetCodeAnimation
 *
 * 算法的效率主要由以下两个复杂度来评估：
 * 时间复杂度：评估执行程序所需的时间。可以估算出程序对处理器的使用程度。
 * 空间复杂度：评估执行程序所需的存储空间。可以估算出程序对计算机内存的使用程度。
 * <p>
 * 时间频度：一个算法中的语句执行次数称为语句频度或时间频度
 *
 * 时间复杂度，大O表示法：
 * O(1) < O(log2n) < O(n) < O(nlog2n) < O(n^2) < O(n^3) < O(2^n) < O(n!) < O(n^n)
 *
 * 排序算法是《数据结构与算法》中最基本的算法之一。https://mp.weixin.qq.com/s/vn3KiV-ez79FmbZ36SX9lg
 * 插入排序、希尔排序、选择排序、冒泡排序、归并排序、快速排序、堆排序、基数排序等。
 *
 * 冒泡排序: 依次比较相邻的元素，把最大数冒泡到最后位置；
 * 选择排序：依次比较最终选择出最小的元素，放到起始位置；
 * 插入排序：依次将元素插入到前方有序数组中；
 *
 * 归并排序：数组先分为length个数组，相邻数组排序归并，直到归并为一个数组
 *
 */

public class TestArithmetic {

    // 插入排序
    public static int[] insertSort(int[] a) {
        int temp;
        for (int i = 1; i < a.length; i++) {
            temp = a[i];        //待插入元素
            int j = i - 1;
            while (j >= 0 && a[j] > temp) {
                a[j + 1] = a[j];//将大于temp的往后移动一位
                j--;
            }
            a[j + 1] = temp;    //最终插入
        }
        return a;
    }
}
