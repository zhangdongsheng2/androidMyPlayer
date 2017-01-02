package com.example.myplayer.widget;

public class DraggableGridModule {
    private int id; //模块的编号
    private String name; //模块的名字
    private int picture; //图片
    private String state;//模块的状态  记录当前是否删除

    public DraggableGridModule(int id, String name, int picture) {
        super();
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.state = "0";//"0"删除图标隐藏
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
