package com.example.myplayer.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import okio.BufferedSink;
import okio.Okio;

/**
 * Created by zhangdongsheng on 16/8/27.
 */
public class FileUtil {
    private static String dataRiJiFileName = "dataRiJi.properties";//日记文件的名字 一般不用

    /**
     * 要存的文件夹
     *
     * @return
     */
    public static File getFileDirectory() {
        return new File(Environment.getExternalStorageDirectory(), "MyPlayer");
    }

    /**
     * 获取存放的文件
     *
     * @return
     */
    public static File getFile(String dataRiJiFileName) {
        File file = new File(getFileDirectory(), dataRiJiFileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void copyFile(File oldFile, File newFile) {
        if (newFile.exists()) {
            newFile.delete();
        }
        File parentFile = newFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        BufferedSink buffer = null;
        try {
            buffer = Okio.buffer(Okio.sink(oldFile));
            buffer.writeAll(Okio.source(newFile));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //==========================================================

    /**
     * 文件中读取字符串
     *
     * @param Path 文本文件路径
     * @return
     */
    public static String fileReadFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    /**
     * 文本文件流转字符串
     *
     * @param inputStream 文本文件流(Json)
     * @return
     */
    public static String fileReadFile(InputStream inputStream) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            InputStream fileInputStream = inputStream;
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

//======================================

    /**
     * 把字符串数据 存进文件中
     *
     * @param key
     * @param value
     */
    public static void propertiesPut(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key不能为空");
        }
        File file = getFile(dataRiJiFileName);
        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream(file);
            properties.load(new InputStreamReader(in, Charset.defaultCharset()));
            in.close();
            properties.put(key, value);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            properties.store(fileOutputStream, "UFT-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据Key 获取存放的字符串内容
     *
     * @param key
     * @return
     */
    public static String propertiesGet(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key不能为空");
        }
        File file = getFile(dataRiJiFileName);
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

    /**
     * 判断是否包含某个key
     *
     * @param key
     * @return
     */
    public static boolean propertiesContains(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key不能为空");
        }
        File file = getFile(dataRiJiFileName);
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
    //==========================================================
}
