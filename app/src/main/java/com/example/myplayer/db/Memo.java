package com.example.myplayer.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myplayer.util.DateUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Memo implements Parcelable {
    public static final Parcelable.Creator<Memo> CREATOR = new Parcelable.Creator<Memo>() {
        @Override
        public Memo createFromParcel(Parcel source) {
            return new Memo(source);
        }

        @Override
        public Memo[] newArray(int size) {
            return new Memo[size];
        }
    };
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
        adddate = DateUtils.formatSystemDate();
        addday = DateUtils.getNowDay();
        addmonth = DateUtils.getNowMonth();
        addyear = DateUtils.getNowYear();
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

    protected Memo(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.adddate = in.readString();
        this.addday = (Integer) in.readValue(Integer.class.getClassLoader());
        this.addmonth = (Integer) in.readValue(Integer.class.getClassLoader());
        this.addyear = (Integer) in.readValue(Integer.class.getClassLoader());
        this.at = in.readString();
        this.content = in.readString();
        this.isasynced = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.loc = in.readString();
        this.photo = in.readString();
        this.updatedate = in.readString();
        this.useremail = in.readString();
        this.uuid = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.adddate);
        dest.writeValue(this.addday);
        dest.writeValue(this.addmonth);
        dest.writeValue(this.addyear);
        dest.writeString(this.at);
        dest.writeString(this.content);
        dest.writeValue(this.isasynced);
        dest.writeString(this.loc);
        dest.writeString(this.photo);
        dest.writeString(this.updatedate);
        dest.writeString(this.useremail);
        dest.writeString(this.uuid);
    }
}
