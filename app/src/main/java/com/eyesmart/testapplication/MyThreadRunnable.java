package com.eyesmart.testapplication;

/**
 * 至少存在两个线程：main线程及JVM垃圾回收线程
 */

public class MyThreadRunnable implements Runnable {
    @Override
    public void run() {
        synchronized (this) {    //this为需要同步的对象
            //需要同步的代码块
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
        //需要同步的方法                 //只有当线程执行完此同步方法后，才会释放锁对象，其他的线程才有可能获取此同步锁，以此类推...

        wait();         //导致当前线程等待并使其进入到等待阻塞状态。直到其他线程调用该同步锁对象的notify()或notifyAll()方法来唤醒此线程。
        notify();       //唤醒在此同步锁对象上等待的单个线程，如果有多个线程，则会任意选择其中某个线程进行唤醒操作，只有当前线程放弃对同步锁对象的锁定，才可能执行被唤醒的线程。
        notifyAll();    //唤醒在此同步锁对象上等待的所有线程，只有当前线程放弃对同步锁对象的锁定，才可能执行被唤醒的线程。
    }

    /**
     * 一些控制线程API
     */
    public void control() throws InterruptedException {
        Thread currentThread = Thread.currentThread();  //取得当前线程
        Thread.sleep(1000);                             //使当前线程休眠1000毫秒

        new Thread(new Runnable() {                     //添加一个任务，并启动线程
            @Override
            public void run() {

            }
        }).start();
        //currentThread.run();                          //执行方法，但没有启动线程
        currentThread.interrupt();                      //中断此线程

        currentThread.isAlive();                        //判断该线程是否已经启动
        currentThread.join();                           //强制该线程运行，其他线程暂时等待

        currentThread.setDaemon(true);                  //设置为守护（后台）线程
        currentThread.setPriority(Thread.MAX_PRIORITY); //设置为最高线程优先级
        currentThread.yield();                          //线程礼让（相同优先级）
    }
}
