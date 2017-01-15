package com.example.myplayer.util;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by zn on 17/1/9.
 * <p>
 * 事件总线Rxbus,比简易版强那么一丢丢
 */

public class RxBus {
    private static RxBus mRxBus = null;
    /**
     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     */
    private Subject<Object, Object> mRxBusObservable = new SerializedSubject<>(PublishSubject.create());

    public static synchronized RxBus getInstance() {
        if (mRxBus == null) {
            mRxBus = new RxBus();
        }
        return mRxBus;
    }

    /**
     * 发送事件
     *
     * @param o   发送的对象
     * @param tag 发送额外tag做标示
     */
    public void send(Object o, String tag) {
        if (hasObservers()) {
            mRxBusObservable.onNext(new RxBusObject(tag, o));
        }
    }

    /**
     * 被观察者
     *
     * @param eventType 接收事件类型
     * @param tag       接收事件tag
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(final Class<T> eventType, final String tag) {
        return mRxBusObservable.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                if (!(o instanceof RxBusObject)) {
                    return false;
                }
                RxBusObject ro = (RxBusObject) o;
                return eventType.isInstance(ro.getObj()) && tag != null && tag.equals(ro.getTag());
            }
        }).map(new Func1<Object, T>() {
            @Override
            public T call(Object o) {
                RxBusObject ro = (RxBusObject) o;
                return (T) ro.getObj();
            }
        });
    }

    /**
     * 判断是否有订阅者
     */
    private boolean hasObservers() {
        return mRxBusObservable.hasObservers();
    }
}
