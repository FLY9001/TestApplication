package com.eyesmart.testapplication.android;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 为同时满足：UI操作线程安全（只允许UI线程修改）、多线程并发UI操作
 * 解决：子线程无法操作UI的问题
 * **********************
 * 为什么不用锁机制解决？  1、访问UI逻辑复杂；2、会阻塞线程，降低效率
 * 为什么主线程Looper.loop()不阻塞主线程？   Looper.loop()死循环一直阻塞主线程，一旦退出消息循环，程序也就退出了；只会发生别的代码阻塞Looper.loop()
 * **********************
 */

public class TestHandler {
    /**
     * 创建Handler
     */
    Handler handler = new MyHandler();

    void test() {
        /**创建Message*/
        Message message = new Message();
        message = handler.obtainMessage(0);
        /**发送Message*/
        handler.sendMessage(message);
        handler.sendMessageDelayed(message, 1000);
        /**Message主动发送*/
        message.setTarget(handler);
        message.sendToTarget();
        /**发送Runnable*/
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        /**AsyncTask*/
        new TestAsyncTask().execute("test");
        //适合：边执行后台边更新UI
        //不适合：特别耗时操作

        /**HandlerThread*/
        //封装Looper的Thread，可创建Handler执行消息队列，最后需quit()终止looper
        //封装HandlerThread的IntentService
        Class<TestIntentService> serviceClass = TestIntentService.class;
    }

    void newThreadTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**主线程Handler*/
                Handler mainHandler = new Handler(Looper.getMainLooper());

                /**分线程Handler*/
                Looper.prepare();                       //准备looper（实例化Looper，创建MessageQueue）
                Handler threadHandler = new Handler();  //默认绑定本线程looper
                Looper.loop();                          //运行looper
                Looper.myLooper().quit();               //立即终止looper
                Looper.myLooper().quitSafely();         //处理完已有消息，再终止looper
            }
        }).start();
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
            }
        }
    }

    class TestAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            publishProgress("values");
            return "result";
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
