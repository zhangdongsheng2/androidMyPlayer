package com.example.myplayer.bean;

/**
 * 封装的是一行歌词的数据
 *
 * @author Administrator
 */
public class Lyric implements Comparable<Lyric> {
    private long startPoint;//起始点
    private String content;

    public Lyric() {
    }

    public Lyric(long startPoint, String content) {
        super();
        this.startPoint = startPoint;
        this.content = content;
    }

    public long getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(long startPoint) {
        this.startPoint = startPoint;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(Lyric another) {
        return (int) (this.startPoint - another.getStartPoint());
    }


}
