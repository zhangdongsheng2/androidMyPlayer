package com.example.myplayer.util;

import android.content.Context;
import android.text.TextUtils;

import com.example.myplayer.MyApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 处理SharedPrefrence的工具类
 *
 * @description
 */
public class SPHelper {
    private static final Map<String, SPHelper> map = new HashMap<>();
    private String name;

    private SPHelper(String name) {
        if (TextUtils.isEmpty(name)) {
            name = "zds";
        }
        this.name = name;
    }

    /**
     * 得到默认的exiu
     *
     * @return
     */
    public static SPHelper getInstance() {
        return getInstance("zds");
    }

    /**
     * 得到指定的name的SP
     *
     * @param name
     * @return
     */
    public static SPHelper getInstance(String name) {
        if (TextUtils.isEmpty(name)) {
            name = "zds";
        }
        if (!map.containsKey(name)) {
            map.put(name, new SPHelper(name));
        }
        return map.get(name);
    }


    public boolean putBoolean(String key, boolean value) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).edit()
                .putBoolean(key, value).commit();
    }

    public boolean putFloat(String key, float value) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).edit().putFloat(key, value)
                .commit();
    }

    /**
     * 删除
     *
     * @param key
     */
    public void remove(String key) {
        MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).edit().remove(key).commit();
    }

    public boolean putInt(String key, int value) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).edit().putInt(key, value)
                .commit();
    }

    public boolean putLong(String key, long value) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).edit().putLong(key, value)
                .commit();
    }

    public boolean putString(String key, String value) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).edit()
                .putString(key, value).commit();
    }

    public boolean putStringSet(String key, Set<String> value) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).edit()
                .putStringSet(key, value).commit();
    }

    public Map<String, ?> getAll() {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).getAll();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).getLong(key, defValue);
    }

    public String getString(String key, String defValue) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).getString(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).getStringSet(key,
                defValue);
    }

}
