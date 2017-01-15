package com.example.myplayer.util;

/**
 * Created by zn on 17/1/9.
 */

public class RxBusObject {

    private String tag;
    private Object obj;

    public RxBusObject(String tag, Object obj) {
        this.tag = tag;
        this.obj = obj;
    }

    public static RxBusObject newInstance(String tag, Object obj) {
        return new RxBusObject(tag, obj);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
