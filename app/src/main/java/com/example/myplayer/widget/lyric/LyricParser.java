package com.example.myplayer.widget.lyric;

import com.example.myplayer.bean.Lyric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


/**
 * 歌词解析模块
 *
 * @author Administrator
 */
public class LyricParser {
    private static String tag = LyricParser.class.getSimpleName();

    public static ArrayList<Lyric> parseLyricFromFile(File file) {
        if (file == null || !file.exists())
            return null;
        ArrayList<Lyric> list = new ArrayList<Lyric>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            // 1.读取每行歌词
            String line;
            while ((line = reader.readLine()) != null) {
                // 2.将每行歌词解析成bean，然后放入集合
                String[] lyricArr = line.split("\\]");
                for (int i = 0; i < lyricArr.length - 1; i++) {
                    Lyric lyric = new Lyric();
                    lyric.setContent(lyricArr[lyricArr.length - 1]);
                    lyric.setStartPoint(formatStartPoint(lyricArr[i]));

                    list.add(lyric);
                }
            }
            // 3.对歌词进行排序
            Collections.sort(list);// 从小到大
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 将[00:22.05格式化为long类型的时间
     *
     * @param str
     * @return
     */
    private static long formatStartPoint(String str) {
        str = str.substring(1);// 得到00:22.05
        String[] arr1 = str.split("\\:");// 得到00 22.05
        String[] arr2 = arr1[1].split("\\.");// 得到22 05
        int minute = Integer.parseInt(arr1[0]);// 得到多分钟
        int second = Integer.parseInt(arr2[0]);// 得到多少秒
        int mills = Integer.parseInt(arr2[1]);// 得到多少毫秒，注意是10毫米

        return minute * 60 * 1000 + second * 1000 + mills * 10;
    }
}
