package com.example.myplayer.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class Memo_extra {
    public static final String FIELD_MEMOID = "memoid";
    private static final long serialVersionUID = -800523504916550259L;
    @Id
    private Long id;
    @NotNull
    @Unique
    private Integer memoid;
    private Integer temperid;
    private Integer weatherid;
    private String wextratitle;

    public Memo_extra() {
    }

    public Memo_extra(Integer memoid, Integer weatherid, Integer temperid) {
        this.memoid = memoid;
        this.weatherid = weatherid;
        this.temperid = temperid;
    }

    @Generated(hash = 1419866714)
    public Memo_extra(Long id, @NotNull Integer memoid, Integer temperid, Integer weatherid,
                      String wextratitle) {
        this.id = id;
        this.memoid = memoid;
        this.temperid = temperid;
        this.weatherid = weatherid;
        this.wextratitle = wextratitle;
    }

    public Long geId() {
        return this.id;
    }

    public Integer getMemoId() {
        return this.memoid;
    }

    public void setMemoId(Integer paramInteger) {
        this.memoid = paramInteger;
    }

    public Integer getTemperId() {
        if (this.temperid == null) {
            this.temperid = Integer.valueOf(4);
        }
        return this.temperid;
    }

    public void setTemperId(Integer paramInteger) {
        this.temperid = paramInteger;
    }

    public Integer getWeatherId() {
        if (this.weatherid == null) {
            this.weatherid = Integer.valueOf(0);
        }
        return this.weatherid;
    }

    public void setWeatherId(Integer paramInteger) {
        this.weatherid = paramInteger;
    }

    public String getWextratitle() {
        return this.wextratitle;
    }

    public void setWextratitle(String paramString) {
        this.wextratitle = paramString;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long paramInteger) {
        this.id = paramInteger;
    }

    public Integer getMemoid() {
        return this.memoid;
    }

    public void setMemoid(Integer memoid) {
        this.memoid = memoid;
    }

    public Integer getTemperid() {
        return this.temperid;
    }

    public void setTemperid(Integer temperid) {
        this.temperid = temperid;
    }

    public Integer getWeatherid() {
        return this.weatherid;
    }

    public void setWeatherid(Integer weatherid) {
        this.weatherid = weatherid;
    }
}



/* Location:           D:\Program Files\AndroidKiller\projects\base\ProjectSrc\smali\

 * Qualified Name:     com.jiji.models.db.Memo_extra

 * JD-Core Version:    0.7.0.1

 */