package com.eyesmart.testapplication.java;

import com.eyesmart.testapplication.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 进程：系统进行资源分配和调度的基本单位
 * 线程：操作系统调度的最小单元，也叫作轻量级进程
 *
 * 至少存在两个线程：main线程及JVM垃圾回收线程
 */

public class MyThread {
    public void test() throws InterruptedException {
        /**线程6种状态*/
        int t = R.drawable.thread;

        /**线程*/
        Thread thread = Thread.currentThread(); //取得当前线程
        Thread.sleep(1000);               //使当前线程休眠1000毫秒

        new Thread(new Runnable() {             //添加一个任务，并启动线程
            @Override
            public void run() {

            }
        }).start();
        //thread.run();                         //执行方法，但没有启动线程
        thread.interrupt();                     //中断此线程
        thread.isAlive();                       //判断该线程是否已经启动
        thread.join();                          //强制该线程运行，其他线程暂时等待
        thread.setDaemon(true);                 //设置为守护（后台）线程
        thread.setPriority(Thread.MAX_PRIORITY);//设置为最高线程优先级
        thread.yield();                         //线程礼让（相同优先级）

        /**线程池*/
        //1、重用线程池中的线程，避免因为线程的创建和销毁所带来的性能开销；
        //2、能有效的控制线程池中的最大并发数，避免大量线程之间因互相抢占系统资源而导致的阻塞现象；
        //3、能对线程进行简单的管理，并提供定时执行及指定间隔循环执行等功能；
        ExecutorService threadPool;
        threadPool = Executors.newSingleThreadExecutor();//单线程池
        threadPool = Executors.newCachedThreadPool();    //灵活复用执行完毕的线程，不用每次新建
        threadPool = Executors.newFixedThreadPool(3);    //固定大小，超出的线程会在队列中等待
        //Runtime.getRuntime().availableProcessors();    //可用的处理器核心，可用来设置线程池大小
        threadPool.execute(new MyThreadRunnable());

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);//类似Timer
        scheduledThreadPool.scheduleAtFixedRate(new MyThreadRunnable(), 1, 3, TimeUnit.SECONDS);

        /**Timer，定时工具*/
        //可以计划执行一个任务一次或反复多次
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
        Timer timer = new Timer();                       //一种线程设施，配合TimerTask使用
        timer.schedule(timerTask, 1000, 2000);           //设置任务，1秒后执行，每2秒重复
        timer.scheduleAtFixedRate(timerTask, 1000, 2000);//可以自动挑战间隔时间
        timer.cancel();                                  //放弃已安排的任务，对正在执行的无影响
        timer.purge();                                   //移除已取消的任务，释放内存空间

    }

    class MyThreadRunnable implements Runnable {
        @Override
        public void run() {
            synchronized (this) {    //this为需要同步的对象
                //TODO 需要同步的代码块
            }
            try {
                syn();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 资源同步，注意：有可能产生死锁！！！！
         */
        private synchronized void syn() throws InterruptedException {   //其同步对象为当前方法所在的对象自身
            //TODO 需要同步的方法                 //只有当线程执行完此同步方法后，才会释放锁对象，其他的线程才有可能获取此同步锁，以此类推...

            wait();         //导致当前线程等待并使其进入到等待阻塞状态。直到其他线程调用该同步锁对象的notify()或notifyAll()方法来唤醒此线程。
            notify();       //唤醒在此同步锁对象上等待的单个线程，如果有多个线程，则会任意选择其中某个线程进行唤醒操作，只有当前线程放弃对同步锁对象的锁定，才可能执行被唤醒的线程。
            notifyAll();    //唤醒在此同步锁对象上等待的所有线程，只有当前线程放弃对同步锁对象的锁定，才可能执行被唤醒的线程。
        }
    }
}