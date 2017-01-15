package com.example.myplayer.util;

import rx.Subscriber;

/**
 * Created by zn on 17/1/9.
 */

public abstract class RxBusSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        completed();
    }

    @Override
    public void onError(Throwable e) {
        error(e);
    }

    @Override
    public void onNext(T t) {
        onReceive(t);
    }

    public abstract void onReceive(T data);

    public void error(Throwable e) {
        e.printStackTrace();
    }

    public void completed() {
    }

}
