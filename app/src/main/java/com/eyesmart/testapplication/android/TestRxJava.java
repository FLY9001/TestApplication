package com.eyesmart.testapplication.android;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * RxJava：
 * 定义：一个 基于事件流、实现异步操作 的库
 * 特点：基于事件流的链式调用、逻辑简洁 & 实现优雅 & 使用简单
 * 原理：被观察者--->订阅--->观察者（顺序发送、响应事件）
 */

public class TestRxJava {
    void test() {
        /**被观察者*/
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                //TODO  某些操作
                e.onNext(1);
                e.onComplete();
                //e.onError(new NullPointerException());
            }
            //map，转换事件，flatMap可转换为Observable，concatMap可保证事件的顺序
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return integer + "";
            }
            //filter，过滤事件
        }).filter(new Predicate<String>() {
            @Override
            public boolean test(@NonNull String s) throws Exception {
                return false;
            }
            //sample，采样事件，每1秒采集一次
        }).sample(1, TimeUnit.SECONDS);
        //zip，组合被观察者，事件1对1组合，被组合对象最好分别在两个线程
        Observable.zip(observable, observable, new BiFunction<String, String, String>() {
            @Override
            public String apply(@NonNull String s, @NonNull String s2) throws Exception {
                return s + s2;
            }
        });
        /**观察者*/
        Observer<String> observer = new Observer<String>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(@NonNull String s) {
                disposable.dispose();       //切断和Observable的联系
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        /**订阅*/
        observable.subscribeOn(Schedulers.newThread())      //被观察者所在线程，只有第一次指定的有效
                .observeOn(AndroidSchedulers.mainThread())  //观察者所在线程，可更换指定线程
                .subscribe(observer);                       //Schedulers.io():数据读写、Schedulers.computation():大量运算


        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                //只接收onNext事件
            }
        });
        observable.doOnNext(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                //只接收onNext事件
            }
        });
    }

    void testObservable() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {

            }
        });
    }
}
