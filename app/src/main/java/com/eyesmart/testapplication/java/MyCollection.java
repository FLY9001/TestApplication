package com.eyesmart.testapplication.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 * 类集，动态的对象数组，数据结构的包装
 */

public class MyCollection {
    void test() {
//--------------------------------------------------------------------------------------------------
        //List
        //ArrayList
        List<String> list = new ArrayList();
        list.add(0, "abc");                          //增
        list.remove(0);                              //删
        list.remove("abc");                          //若删除对象，则其必须实现equals()及hashCode()方法
        list.contains("abc");                        //查
        list.indexOf("abc");
        list.subList(1, 2);                          //截取
        list.isEmpty();                              //是否为空
        String[] strs = list.toArray(new String[]{});//输出为数组（指定泛型）

        //Vector
        Vector<String> vector = new Vector<>();

        //LinkedList，链表操作类，实现Queue接口，队列，先进先出
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.addFirst("X");                    //表头增加
        linkedList.addLast("Y");                     //表尾增加
        linkedList.element();                        //得到表头
        linkedList.peek();
        linkedList.poll();                           //得到表头并删除
//--------------------------------------------------------------------------------------------------
        //Set，数据不可重复（对象类可以覆写equals()、hashCode()方法）
        Set<String> set = new HashSet<>();           //HashSet，不可重复，没有顺序
        set = new TreeSet<>();                       //TreeSet，不可重复，有顺序（对象类必须实现Comparable接口）
        SortedSet<String> sortedSet = new TreeSet<>();//TreeSetz中实现接口，用于排序
    }
}
