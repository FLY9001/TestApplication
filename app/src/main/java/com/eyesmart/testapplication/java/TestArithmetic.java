package com.eyesmart.testapplication.java;

/**
 * Leetcode算法图解:https://github.com/MisterBooo/LeetCodeAnimation
 *
 * 算法的效率主要由以下两个复杂度来评估：
 * 时间复杂度：评估执行程序所需的时间。可以估算出程序对处理器的使用程度。
 * 空间复杂度：评估执行程序所需的存储空间。可以估算出程序对计算机内存的使用程度。
 * <p>
 * 时间频度：一个算法中的语句执行次数称为语句频度或时间频度
 */

public class TestArithmetic {

    //插入排序
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
