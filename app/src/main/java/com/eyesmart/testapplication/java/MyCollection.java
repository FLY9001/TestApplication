package com.eyesmart.testapplication.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

/**
 * 类集，动态的对象数组，数据结构的包装
 */

public class MyCollection {
    void test() throws IOException {
        int[] temp = {4, 2, 6, 3, 1, 7, 5};
        Student[] students = {};                //对对象数组进行排列

        Arrays.fill(temp, 1);               //数组填充，全部为1
        //System.arraycopy();                   //数组复制
        Arrays.copyOf(temp, 6);      //数组复制5个（从0开始）
        Arrays.copyOfRange(temp, 0, 5);
        Arrays.binarySearch(temp, 7);      //数组查找
        Arrays.binarySearch(temp, 3, 6, 7); //数组查找
        Arrays.sort(temp);                      //数组排序
        Arrays.sort(students, 0, 5, new StuComparator());
        Arrays.toString(temp);                  //数组输出
        Arrays.deepToString(students);          //二维数组输出
        Arrays.equals(temp, null);          //数组比较
        Arrays.deepEquals(students, null);  //二维数组比较
//--------------------------------------------------------------------------------------------------
        //List
        //ArrayList
        List<String> list = new ArrayList();
        list.add(0, "abc");          //增
        list.remove(0);                       //删
        list.remove("abc");                       //若删除对象，则其必须实现equals()及hashCode()方法
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
//--------------------------------------------------------------------------------------------------
        //Collections，集合工具类
        Collections.addAll(list, "111");            //添加
        Collections.reverse(list);                  //反转
        Collections.binarySearch(list, "111");      //查找
        Collections.replaceAll(list, "111", "222"); //替换
        Collections.sort(list);                     //排序
        Collections.swap(list, 0, 1);               //交换
        Collections.shuffle(list);                  //乱序
//--------------------------------------------------------------------------------------------------
        //栈，先进后出
        Stack<String> stringStack = new Stack<>();
        stringStack.push("abc");                    //入栈
        stringStack.pop();                          //出栈，同时删除
        stringStack.peek();                         //出栈，不删除
        stringStack.isEmpty();
        stringStack.search("abc");
//--------------------------------------------------------------------------------------------------
        //属性类
        Properties pro = new Properties();
        pro.setProperty("key", "value");
        pro.getProperty("key");

        File file = new File("D:" + File.separator + "myPro.peoperties");//从普通文件保存读取属性
        pro.store(new FileOutputStream(file), "info");
        pro.load(new FileInputStream(file));

        File xmlFile = new File("D:" + File.separator + "myPro.xml");    //从xml文件保存读取属性
        pro.storeToXML(new FileOutputStream(xmlFile), "info");
        pro.loadFromXML(new FileInputStream(xmlFile));
    }
}
