package com.eyesmart.testapplication.java;

import com.eyesmart.testapplication.R;

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
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * http://www.cnblogs.com/haimishasha/p/10757722.html
 * 类集，动态的对象数组，数据结构的包装
 * <p>
 * 数据结构：        数据的组织方式（树、图、哈希）和处理方式（增删改查、遍历）
 * 数据结构分类：    线性结构、树结构、图结构、哈希结构
 * 数据结构复杂度：  时间复杂度、空间复杂度
 * 时间复杂度：      算法执行时间随输入规模增长而增长的量级
 * 函数描述(好->坏)：常数级O(1)、对数级O(log n)、线性级O(n)、线性对数级O(n log n)、平方级O(n2)\立方级O(n3)、指数级O（2n）等
 * <p>
 * 集合类：工具类容器，实现了常用的数据结构，提供了操作API
 */

public class MyCollection {
    void test() throws IOException, InterruptedException {
        int[] temp = {4, 2, 6, 3, 1, 7, 5};
        Student[] students = {};                //对对象数组进行排列

        /**Arrays，数组工具类*/
        Arrays.fill(temp, 1);               //数组填充，全部为1
        //System.arraycopy();                   //数组复制
        Arrays.copyOf(temp, 6);      //数组复制5个（从0开始）
        Arrays.copyOfRange(temp, 0, 5);
        Arrays.binarySearch(temp, 7);      //数组查找，二分搜索法，调用之前必须排序
        Arrays.binarySearch(temp, 3, 6, 7); //数组查找
        Arrays.sort(temp);                      //数组排序
        Arrays.sort(students, 0, 5, new StuComparator());
        Arrays.toString(temp);                  //数组输出
        Arrays.deepToString(students);          //二维数组输出
        Arrays.equals(temp, null);          //数组比较
        Arrays.deepEquals(students, null);      //二维数组比较
//--------------------------------------------------------------------------------------------------
        List<String> c = new ArrayList<>();
        /**Collections，集合工具类*/
        Collections.addAll(c, "111");                //添加
        Collections.reverse(c);                                 //反转
        Collections.binarySearch(c, "111");                //查找，二分搜索法，调用之前必须排序
        Collections.replaceAll(c, "111", "222"); //替换
        Collections.swap(c, 0, 1);                         //交换
        Collections.sort(c);                                    //排序
        Collections.shuffle(c);                                 //乱序
        Collections.max(c);                                     //最大
        Collections.min(c);                                     //最小
//--------------------------------------------------------------------------------------------------
        /**集合类*/
        int collection1 = R.drawable.collection;     //绿色：可并发，灰色：弃用
        int collection2 = R.drawable.collection2;    //结构

        /**List*/
        //ArrayList，基于动态数组，内含数组，长度不够则扩大1.5倍；查找效率高
        List<String> list = new ArrayList<>();
        list.isEmpty();
        list.add(0, "abc");           //增
        list.remove(0);                        //删
        list.remove("abc");                       //若删除对象，则其必须实现equals()及hashCode()方法
        list.contains("abc");                        //查
        list.indexOf("abc");
        list.subList(1, 2);                          //截取
        list.isEmpty();                              //是否为空
        String[] strs = list.toArray(new String[]{});//输出为数组（指定泛型）

        //LinkedList，基于链表，也实现Queue接口，有队列功能（先进先出）；随机增删效率高
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.addFirst("X");                 //表头增加
        linkedList.addLast("Y");                  //表尾增加
        linkedList.removeFirst();                    //表头删除
        linkedList.removeLast();                     //表尾增删除
        linkedList.descendingIterator();             //逆向迭代器

        //Vector，类似ArrayList，但线程安全
        list = new Vector<>();
//--------------------------------------------------------------------------------------------------
        /**Set，基于二叉树，数据不可重复*/
        //对象类要覆写equals()、hashCode()方法
        Set<String> set = new HashSet<>();            //HashSet，无序，离散，遍历序和插入序不一致;LinkedHashSet保证遍历序和插入序一致（Hash离散，Linked有序）
        TreeSet<String> treeSet = new TreeSet<>();    //TreeSet，排序（对象类必须实现Comparable接口）；TreeSet中实现接口SortedSet，可用于排序
//--------------------------------------------------------------------------------------------------
        /**迭代器*/
        //Collection的输出接口
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
        /**Map，key-value*/
        //以key-value形式存储，key不可重复，重复会覆盖（key要覆写equals()、hashCode()方法）
        Map<String, String> map = new HashMap<>();                  //HashMap，key无序；IdentityHashMap可实现key重复；LinkedHashMap保证遍历序和插入序一致（Hash离散，Linked有序）
        TreeMap<String, String> treeMap = new TreeMap<>();          //TreeMap，key排序（key类必须实现Comparable接口）；TreeMap中实现接口SortedMap，用于排序（多了一些排序相关方法）
        map.put("key", "value");
        map.get("key");
        map.containsKey("key");
        map.containsValue("value");

        Set<String> keys = map.keySet();                            //Map中所有key
        Iterator<String> keysIterator = keys.iterator();
        Collection<String> values = map.values();                   //Map中所有value
        Iterator<String> valuesIterator = values.iterator();

        Set<Map.Entry<String, String>> entrySet = map.entrySet();   //遍历Map中所有key、value
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
        /**Stack，模拟栈，先进后出*/
        Stack<String> stringStack = new Stack<>();  //父类是Vector
        stringStack.isEmpty();
        stringStack.push("abc");              //入栈
        stringStack.pop();                          //出栈，同时删除
        stringStack.peek();                         //出栈，不删除
        stringStack.search("abc");               //查找
//--------------------------------------------------------------------------------------------------
        /**属性类，可读写属性文件*/
        Properties p = new Properties();
        p.setProperty("key0", "value0");
        p.getProperty("key0");

        File file = new File("D:" + File.separator + "myPro.properties");//从普通文件保存读取属性；key0=value0
        p.store(new FileOutputStream(file), "注释");
        p.load(new FileInputStream(file));

        File xmlFile = new File("D:" + File.separator + "myPro.xml");    //从xml文件保存读取属性；<properties>    <entry key="key0">value</entry>   </properties>
        p.storeToXML(new FileOutputStream(xmlFile), "注释");
        p.loadFromXML(new FileInputStream(xmlFile));

//--------------------------------------------------------------------------------------------------
        /**Queue，模拟队列，先进先出*/
        Queue<String> queue = linkedList;
        queue.offer("Y");                    //表尾增加
        queue.poll();                           //得到表头并删除，没有就返回null
        queue.element();                        //得到表头，没有就抛出异常
        queue.peek();                           //得到表头，没有就返回null

        //不阻塞队列LinkedList，PriorityQueue，ConcurrentLinkedQueue
        queue = new PriorityQueue<>();          //优先队列，会重新排序元素
        queue = new ConcurrentLinkedQueue<>();  //并发链接队列，线程安全

        //阻塞队列
        //ArrayBlockingQueue：   由数组结构组成的有界阻塞队列。
        //LinkedBlockingQueue：  由链表结构组成的有界阻塞队列。
        //PriorityBlockingQueue：支持优先级排序的无界阻塞队列。
        //DelayQueue：           使用优先级队列实现的无界阻塞队列。
        //SynchronousQueue：     不存储元素的阻塞队列。
        //LinkedTransferQueue：  由链表结构组成的无界阻塞队列。
        //LinkedBlockingDeque：  由链表结构组成的双向阻塞队列
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
    }

    class Action {
        void action() {

        }
    }
}
