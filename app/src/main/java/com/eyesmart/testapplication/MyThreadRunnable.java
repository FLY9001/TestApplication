package com.eyesmart.testapplication;

/**
 * 至少存在两个线程：main线程及JVM垃圾回收线程
 */

public class MyThreadRunnable implements Runnable {
    @Override
    public void run() {
        synchronized (this) {    //一般设置this为同步对象
            //需要同步的代码块
        }
        syn();
    }

    /**
     * 资源同步，注意：有可能产生死锁！！！！
     */
    private synchronized void syn() {   //需要同步的方法

    }

    /**
     * 一些控制线程API
     */
    public  void control() throws InterruptedException {
        Thread currentThread = Thread.currentThread();  //取得当前线程
        Thread.sleep(1000);                             //使当前线程休眠1000毫秒

        new Thread(new Runnable() {                     //添加一个任务，并启动线程
            @Override
            public void run() {

            }
        }).start();
        //currentThread.run();                          //执行方法，但没有启动线程

        currentThread.isAlive();                        //判断该线程是否已经启动
        currentThread.join();                           //强制该线程运行，其他线程暂时等待
        currentThread.interrupt();                      //中断此线程

        currentThread.setDaemon(true);                  //设置为守护（后台）线程
        currentThread.setPriority(Thread.MAX_PRIORITY); //设置为最高线程优先级
        currentThread.yield();                          //线程礼让（相同优先级）
    }
}
