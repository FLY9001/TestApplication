package com.eyesmart.testapplication;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Tian on 2017-7-19 0019.
 */

public class TestRxJava {
    void test() {
        //被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                //TODO  某些操作
                e.onNext("1");
                e.onComplete();
                //e.onError(new NullPointerException());
            }
        });
        //观察者
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
        observable.subscribeOn(Schedulers.newThread())      //被观察者所在线程，只有第一次指定的有效
                .observeOn(AndroidSchedulers.mainThread())  //观察者所在线程，可更换指定线程
                .subscribe(observer);                       //Schedulers.io():数据读写、Schedulers.computation():大量运算

        observable.doOnNext(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                //只接收onNext事件
            }
        });
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                //只接收onNext事件
            }
        });
    }
}
