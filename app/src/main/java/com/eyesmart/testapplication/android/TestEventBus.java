package com.eyesmart.testapplication.android;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * EventBus：事件总线
 * 主要做了：事件订阅和线程控制，实现组建之间的解耦
 * <p>
 * 官方混淆配置：
 * -keepattributes *Annotation*
 * -keepclassmembers class ** { @org.greenrobot.eventbus.Subscribe <methods>; }
 * -keep enum org.greenrobot.eventbus.ThreadMode { *; }
 * # Only required if you use AsyncExecutor
 * -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent { <init>(java.lang.Throwable); }
 */

public class TestEventBus {

    /**register会把当前类中匹配的方法，存入一个map，而post会根据实参去map查找进行反射调用*/
    //订阅者MAP和订阅方法信息MAP，存储所有订阅相关
    //事件post时，遍历所有订阅，生成poster放在队列
    //POSTING类型事件不用排队，其他事件poster被放在3个队列中排队执行，mainThreadPoster、backgroundPoster、asyncPoster
    void test() {
        /**2、订阅事件*/
        EventBus.getDefault().register(this);
        /**4、发送事件*/
        EventBus.getDefault().post(new MessageEvent("事件消息"));   //postSticky发送黏性事件，可先发送后订阅（订阅时处理）
        /**3、取消订阅*/
        EventBus.getDefault().unregister(this);
    }

    /**
     * 5、处理事件
     */
    //线程模型、是否粘性、优先级
    //线程模型 默认POSTING（本线程处理，即post发送线程），MAIN（主线程处理），BACKGROUND（子线程处理），ASYNC（新建子线程处理）
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true, priority = 0)
    public void onReceiveEvent(MessageEvent event) {
        Log.d("TAG", "onReceiveEvent: " + event.getMessage());
    }

    /**
     * 1、定义事件类
     */
    class MessageEvent {
        private String message;

        public MessageEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
