package com.eyesmart.testapplication.android;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * RxJava：
 * 定义：一个 基于事件流、实现异步操作 的库
 * 特点：基于事件流的链式调用，逻辑简洁 & 实现优雅 & 使用简单
 * 原理：被观察者--->订阅--->观察者（顺序发送、响应事件）
 */

public class TestRxJava {
    void test() {
        /**被观察者*/
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                //TODO  某些操作
                e.onNext("1");
                e.onComplete();
                //e.onError(new NullPointerException());
            }
            //filter，过滤事件
        }).filter(new Predicate<String>() {
            @Override
            public boolean test(@NonNull String s) throws Exception {
                return false;
            }
            //sample，采样事件，每1秒采集一次
        }).sample(1, TimeUnit.SECONDS);
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
        /**基本创建*/
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {

            }
        });
        /**快速创建*/
        Observable.just(1, 2, 3, 4, 5);
        Observable.fromArray(new int[]{1, 2, 3, 4, 5});         //fromArray，数组遍历发送
        Observable.fromIterable(new ArrayList<Integer>());      //fromIterable，集合遍历发送
        Observable.empty();                                     //empty，仅发送Complete事件，直接通知完成
        Observable.error(new RuntimeException());               //error，仅发送Error事件，直接通知异常
        Observable.never();                                     //never，不发送任何事件
        /**定时、周期性创建*/
        Observable.defer(new Callable<ObservableSource<?>>() {  //defer，延迟到订阅时创建
            @Override
            public ObservableSource<?> call() throws Exception {
                return Observable.just(1, 2, 3, 4, 5);
            }
        });
        Observable.timer(2, TimeUnit.SECONDS);                  //timer，延迟2s后发送0
        Observable.interval(2, 1, TimeUnit.SECONDS);            //interval，延迟2s，间隔1s，不断递增发送0、1、2、……
        Observable.intervalRange(3, 10, 2, 1, TimeUnit.SECONDS);//intervalRange，延迟2s，间隔1s，从3开始递增，共发送10个事件
        Observable.range(3, 10);                                //range，从3开始递增，共发送10个事件

        /**转换*/
        observable.map(new Function<Integer, String>() {        //map，转换为新的事件类型
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return integer + "";
            }
        });
        observable.flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            //flatMap，转换为新的Observable，ConcatMap可保证旧事件序列原始顺序
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                return Observable.just(integer + "1", integer + "2");
            }
        });
        observable.buffer(3, 1);                                //buffer，缓存事件，缓存3个，前进一个；如123、234、345、……

        /**组合*/
        Observable.concat(Observable.just(1, 2), observable);   //concat，串行组合，大于4个用concatArray，防止onError事件终止发送用concatDelayError
        Observable.merge(Observable.just(1, 2), observable);    //merge，并行组合，大于4个用mergeArray，mergeDelayError
        Observable.zip(Observable.just(1, 2), observable, new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer + integer2 + "";                 //zip，合并，事件1对1合并，被合并对象最好分别在两个线程，按时间合并用combineLatest、combineLatestDelayError
            }                                                   //事件组合方式 = 严格按照原先事件序列 进行对位合并
        });                                                     //最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer * integer2;              //reduce，聚合成1个事件，1*2，2*3，6*4，最终发送24
                    }
                });
        Observable.just(1, 2, 3, 4)
                .collect(new Callable<ArrayList<Integer>>() {   //collect，收集到一个数据结构里，最终发送{1, 2, 3, 4}
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        // 1. 创建数据结构（容器），用于收集被观察者发送的数据
                        return new ArrayList<>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> list, Integer integer) throws Exception {
                        // 2. 对发送的数据进行收集
                        list.add(integer);
                    }
                });
        observable.startWith(Observable.just(0, 0, 0))          //startWith，发送前追加，后调用先追加，顺序1，2，0，0，0，……
                .startWithArray(1, 2);
        observable.count();                                     //count，统计发送事件数量，最终发送的事件为旧事件总数
    }
}
