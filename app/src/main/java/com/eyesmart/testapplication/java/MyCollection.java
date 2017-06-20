package com.eyesmart.testapplication.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
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
        //Set，数据不可重复（对象类要覆写equals()、hashCode()方法）
        Set<String> set = new HashSet<>();           //HashSet，不可重复，没有顺序
        set = new TreeSet<>();                       //TreeSet，不可重复，有顺序（对象类必须实现Comparable接口）
        SortedSet<String> sortedSet = new TreeSet<>();//TreeSetz中实现接口，用于排序（多了一些排序相关方法）
//--------------------------------------------------------------------------------------------------
        //迭代器，Collection的输出接口
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            iterator.remove();
        }
        //双向迭代，List特有
        ListIterator<String> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            listIterator.next();
        }
        while (listIterator.hasPrevious()) {        //必须先从前往后后，才能向前
            String s = listIterator.previous();
            iterator.remove();
        }
//--------------------------------------------------------------------------------------------------
        //Map，以key-value形式存储，key不可重复，重复会覆盖（key要覆写equals()、hashCode()方法）
        Map<String, String> map = new HashMap<>();  //HashMap，没有顺序
        map = new TreeMap<>();                      //TreeMap，有顺序（key类必须实现Comparable接口）
        SortedMap<String, String> sortedMap = new TreeMap<>();//TreeMap中实现接口，用于排序（多了一些排序相关方法）
        map.put("key", "value");
        map.get("key");
        map.containsKey("key");
        map.containsValue("value");
        Set<String> keys = map.keySet();            //Map中所有key
        Collection<String> values = map.values();   //Map中所有value
        Iterator<String> keysIterator = keys.iterator();

        Set<Map.Entry<String, String>> entrySet = map.entrySet();//遍历Map中所有key、value
        Iterator<Map.Entry<String, String>> entryIterator = entrySet.iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, String> me = entryIterator.next();
            me.getKey();
            me.getValue();
        }
        for (Map.Entry<String, String> me : entrySet) {
            me.getKey();
            me.getValue();
        }
    }
}
