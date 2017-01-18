package com.example.myplayer.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @Author 冯高远
 * @Date 2016/9/18 11:55
 * @Description
 */
public class SaveDataHelper {

    public static void put(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key不能为空");
        }
        File file = getFile();
        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream(file);
//            properties.load(in);
            properties.load(new InputStreamReader(in, Charset.defaultCharset()));
            in.close();
            properties.put(key, value);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            properties.store(fileOutputStream, "UFT-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getFileName() {
        return "general";
    }


    protected static File getFile() {
        File file = new File(Environment.getExternalStorageDirectory(), getFileName());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    public static String get(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key不能为空");
        }
        File file = getFile();
        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream(file);
            properties.load(in);
            in.close();
            return (String) properties.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean contains(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key不能为空");
        }
        File file = getFile();
        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream(file);
            properties.load(in);
            in.close();
            return properties.containsKey(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
