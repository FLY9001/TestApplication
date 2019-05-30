package com.eyesmart.testapplication.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * http://www.jianshu.com/p/9fe944ee02f7
 * **********************
 * 为什么不用锁机制解决？  1、访问UI逻辑复杂；2、会阻塞线程，降低效率
 * 为什么主线程Looper.loop()不阻塞主线程？   Looper.loop()死循环一直阻塞主线程，一旦退出消息循环，程序也就退出了；只会发生别的代码阻塞Looper.loop()
 * **********************
 * Handler(sendMessage)--->Message--->(enqueueMessage)MessageQueue(next)--->(loop)Looper--->Message--->(dispatchMessage)Handler(handleMessage)
 * <p>
 * 1个Thread只有1个Looper，1个MessageQueue,可有多个handler;
 * MessageQueue中有N个Message，1个Message最多指定1个Handler
 *
 * 静态内部类持有外部类，activity中的handler造成内存泄漏
 * 根本原因：activity结束了，handler还有没执行完的msg
 * 解决方法：
 * 1、activity结束时，调用removeCallbacksAndMessages(null)；
 * 2、写成静态内部类，如需调用activity方法，可弱引用activity；
 * 3、抽取做单独封装，实现回调弱引用的Handler。
 */
class ShanActivity extends Activity {
    private static class MyHandler extends Handler {
        private final WeakReference<ShanActivity> mActivity;

        public MyHandler(ShanActivity activity) {
            mActivity = new WeakReference<ShanActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ShanActivity activity = mActivity.get();
            if (activity != null) {
                //do Something
            }
        }
    }
}
public class TestHandler {
    /**
     * 创建Handler
     */
    Handler handler = new MyHandler();

    void test() {
        /**Handler*/
        //创建Message
        Message message = Message.obtain(handler, 0);
        message = handler.obtainMessage(0);
        //发送Runnable
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        //发送Message
        handler.sendMessage(message);
        handler.sendMessageDelayed(message, 1000);
        //Message主动发送
        message.setTarget(handler);
        message.sendToTarget();


        /**AsyncTask*/
        //优点：方便；缺点：版本改动大，性能一般
        //适合：边执行后台边更新UI
        //不适合：特别耗时操作
        TestAsyncTask task = new TestAsyncTask();
        task.execute("test");
        //取消
        if (!task.isCancelled()) {
            task.cancel(true);
        }

        /**HandlerThread = Handler + Thread*/
        //封装Looper的Thread，可创建Handler执行消息队列，最后需quit()终止looper
        HandlerThread handlerThread = new HandlerThread("线程名");
        handlerThread.start();
        Handler threadHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //消息在handlerThread这个子线程中处理
            }
        };
        handlerThread.quit();   //不用时，必须调用quit()

        //封装HandlerThread的IntentService
        Class<TestIntentService> serviceClass = TestIntentService.class;
    }

    void newThreadTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**主线程Handler*/
                Handler mainHandler = new Handler(Looper.getMainLooper());

                /**运用子线程Handler*/
                Looper.prepare();                       //1、创建Looper（子线程没有Looper，需手动创建）
                Handler threadHandler = new Handler();  //2、创建Handler（默认绑定本线程looper）
                Looper.loop();                          //3、运行looper

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
