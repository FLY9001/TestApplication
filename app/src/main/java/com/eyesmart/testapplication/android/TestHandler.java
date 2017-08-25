package com.eyesmart.testapplication.android;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Tian on 2017-7-7 0007.
 */

public class TestHandler {
    void test() {
        boolean b = handler.hasMessages(0);     //判断消息队列中是否有此消息

        Message message = new Message();        //创建消息
        message = handler.obtainMessage(0);

        handler.sendMessage(message);           //发送消息
        handler.sendMessageDelayed(message, 1000);
        message.sendToTarget();

        new TestAsyncTask().execute("test");
    }

    void newThreadTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();                       //准备looper（实例化Looper，创建MessageQueue）
                Handler threadHandler = new Handler();  //分线程Handler
                Looper.loop();                          //运行looper

                Handler mainHandler = new Handler(Looper.getMainLooper());//主线程Handler
            }
        }).start();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
            }
        }
    };

    class TestAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            publishProgress("value");
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
