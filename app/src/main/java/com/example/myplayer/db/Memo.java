package com.example.myplayer.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Memo {
    @Id(autoincrement = true)
    public Long id;

    @NotNull
    public String adddate;

    public Integer addday;

    public Integer addmonth;

    public Integer addyear;

    public String at;

    public String content;

    public Boolean isasynced = Boolean.valueOf(false);

    public String loc;

    public String photo;

    public String updatedate;

    public String useremail = "0";

    public String uuid = "0";

    public Memo() {
    }

    @Generated(hash = 1757184403)
    public Memo(Long id, @NotNull String adddate, Integer addday, Integer addmonth,
                Integer addyear, String at, String content, Boolean isasynced,
                String loc, String photo, String updatedate, String useremail,
                String uuid) {
        this.id = id;
        this.adddate = adddate;
        this.addday = addday;
        this.addmonth = addmonth;
        this.addyear = addyear;
        this.at = at;
        this.content = content;
        this.isasynced = isasynced;
        this.loc = loc;
        this.photo = photo;
        this.updatedate = updatedate;
        this.useremail = useremail;
        this.uuid = uuid;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdddate() {
        return this.adddate;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    public Integer getAddday() {
        return this.addday;
    }

    public void setAddday(Integer addday) {
        this.addday = addday;
    }

    public Integer getAddmonth() {
        return this.addmonth;
    }

    public void setAddmonth(Integer addmonth) {
        this.addmonth = addmonth;
    }

    public Integer getAddyear() {
        return this.addyear;
    }

    public void setAddyear(Integer addyear) {
        this.addyear = addyear;
    }

    public String getAt() {
        return this.at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsasynced() {
        return this.isasynced;
    }

    public void setIsasynced(Boolean isasynced) {
        this.isasynced = isasynced;
    }

    public String getLoc() {
        return this.loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUpdatedate() {
        return this.updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getUseremail() {
        return this.useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


}
