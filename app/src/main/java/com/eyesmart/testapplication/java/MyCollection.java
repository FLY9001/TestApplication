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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 类集，动态的对象数组，数据结构的包装
 */

public class MyCollection {
    void test() throws IOException, InterruptedException {
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
        list.add(0, "abc");                     //增
        list.remove(0);                            //删
        list.remove("abc");                       //若删除对象，则其必须实现equals()及hashCode()方法
        list.contains("abc");                        //查
        list.indexOf("abc");
        list.subList(1, 2);                    //截取
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

//--------------------------------------------------------------------------------------------------
        //阻塞队列
        BlockingQueue<Action> actionQueue = new LinkedBlockingQueue<>(100);
        Action action = new Action();

        //放入
        actionQueue.offer(action);//试图添加BlockingQueue里。如果可以，容纳，返回true，否则返回false。(本方法不阻塞当前执行方法的线程。)
        actionQueue.offer(action, 1, TimeUnit.SECONDS);//可以设定等待的时间。如果在指定的时间内还不能往队列中加入BlockingQueue，则返回失败。
        actionQueue.put(action);//将anObject加到BlockingQueue里。如果BlockQueue没有空间，则调用此方法的线程被阻断，直到BlockingQueue里面有空间再继续。
        //获取
        actionQueue.poll();//取走 BlockingQueue 里排在首位的对象。若不能立即取出，则可以等 time参数规定的时间。取不到时返回null。
        actionQueue.poll(1, TimeUnit.SECONDS); //从BlockingQueue中取出一个队首的对象。如果在指定时间内，队列一旦有数据可取，则立即返回队列中的数据；否则直到时间超时还没有数据可取，返回失败。
        actionQueue.take();//取走BlockingQueue里排在首位的对象。若BlockingQueue为空，则阻断进入等待状态，直到 BlockingQueue有新的数据被加入。
        actionQueue.drainTo(new ArrayList<Action>());//一次性从BlockingQueue获取所有可用的数据对象(还可以指定获取数据的个数)。通过该方法，可以提升获取数据的效率；无须多次分批加锁或释放锁。
        /**
         * 在Java中提供了7个阻塞队列，它们分别如下所示。
         • ArrayBlockingQueue：由数组结构组成的有界阻塞队列。
         • LinkedBlockingQueue：由链表结构组成的有界阻塞队列。
         • PriorityBlockingQueue：支持优先级排序的无界阻塞队列。
         • DelayQueue：使用优先级队列实现的无界阻塞队列。
         • SynchronousQueue：不存储元素的阻塞队列。
         • LinkedTransferQueue：由链表结构组成的无界阻塞队列。
         • LinkedBlockingDeque：由链表结构组成的双向阻塞队列
         */

    }

    class Action {
        void action() {

        }
    }
}
