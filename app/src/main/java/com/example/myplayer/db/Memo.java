package com.example.myplayer.db;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.example.myplayer.bean.Photo;
import com.example.myplayer.util.DateUtils;
import com.example.myplayer.util.StringUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import java.util.List;

@Entity
public class Memo {
    public static final String DATE_FIELD_ADDDAY = "addday";
    public static final String DATE_FIELD_ADDMONTH = "addmonth";
    public static final String DATE_FIELD_ADDYEAR = "addyear";
    public static final String DATE_FIELD_NAME = "adddate";
    public static final String FIELD_ADDDATE = "adddate";
    public static final String FIELD_AT = "at";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_EMAIL = "useremail";
    public static final String FIELD_LOC = "loc";
    public static final String FIELD_PHOTO = "photo";
    public static final String FIELD_UUID = "uuid";
    public static final String TAG_SEPARATOR = "\\|";

    @NotNull
    private Date adddate;

    private Integer addday;

    private Integer addmonth;

    private Integer addyear;

    private String at;

    private String content;
    @Id
    private Integer id;

    private Boolean isasynced = Boolean.valueOf(false);

    private String loc;

    private String photo;

    private Date updatedate;

    private String useremail = "0";

    private String uuid = "0";

    public Memo() {
    }

    public Memo(String paramString, Date paramDate) {
        this(paramString, paramDate, null, null, null);
    }

    public Memo(String paramString1, Date paramDate, String paramString2, String paramString3) {
        this(paramString1, paramDate, paramString2, paramString3, null);
    }

    public Memo(String paramString1, Date paramDate, String paramString2, String paramString3, String paramString4) {
        this.content = paramString1;
        addDate(paramDate);
        this.at = paramString2;
        this.loc = paramString3;
        this.photo = paramString4;
        this.updatedate = new Date();
    }

    public Memo(String paramString1, Date paramDate, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) {
        this(paramString1, paramDate, paramString2, paramString3, paramString4);
        this.useremail = paramString5;
        this.uuid = paramString6;
    }

    @Generated(hash = 1295257571)
    public Memo(@NotNull Date adddate, Integer addday, Integer addmonth, Integer addyear, String at, String content, Integer id, Boolean isasynced, String loc,
                String photo, Date updatedate, String useremail, String uuid) {
        this.adddate = adddate;
        this.addday = addday;
        this.addmonth = addmonth;
        this.addyear = addyear;
        this.at = at;
        this.content = content;
        this.id = id;
        this.isasynced = isasynced;
        this.loc = loc;
        this.photo = photo;
        this.updatedate = updatedate;
        this.useremail = useremail;
        this.uuid = uuid;
    }


    public static List<String> tranferAtToList(String paramString) {
//        ArrayList localArrayList = new ArrayList();
//        int i;
//        if (!StringUtils.isNullOrEmpty(paramString)) {
//            paramString = paramString.split("\\|");
//            i = 0;
//        }
//        for (; ; ) {
//            if (i >= paramString.length) {
//                return localArrayList;
//            }
//            localArrayList.add(paramString[i]);
//            i += 1;
//        }
        return null;
    }

    private Integer getTagNum(String paramString) {
        if (TextUtils.isEmpty(paramString)) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(paramString.split("\\|").length);
    }

    public void addDate(Date paramDate) {
        if (paramDate != null) {
            this.adddate = paramDate;
            this.addyear = Integer.valueOf(DateUtils.formatDate(paramDate, "yyyy"));
            this.addmonth = Integer.valueOf(DateUtils.formatDate(paramDate, "MM"));
            this.addday = Integer.valueOf(DateUtils.formatDate(paramDate, "dd"));
        }
    }

    public void addDateFromStr(String paramString) {
        addDate(DateUtils.transform(paramString, "yyyy-MM-dd HH:mm:ss"));
    }

    public String getAddDateStr() {
        if (this.adddate != null) {
            return DateUtils.formatDate(this.adddate, "yyyy-MM-dd HH:mm:ss");
        }
        return DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public Date getAdddate() {
        return this.adddate;
    }

    public void setAdddate(Date paramDate) {
        this.adddate = paramDate;
    }

    public void setAdddate(String paramString) {
        this.adddate = DateUtils.transform(paramString, "yyyy-MM-dd HH:mm:ss");
    }

    public Integer getAddday() {
        return this.addday;
    }

    public void setAddday(Integer paramInteger) {
        this.addday = paramInteger;
    }

    public Integer getAddmonth() {
        return this.addmonth;
    }

    public void setAddmonth(Integer paramInteger) {
        this.addmonth = paramInteger;
    }

    public Integer getAddyear() {
        return this.addyear;
    }

    public void setAddyear(Integer paramInteger) {
        this.addyear = paramInteger;
    }

    public String getAt() {
        return StringUtils.notNullString(this.at);
    }

    public void setAt(String paramString) {
        this.at = StringUtils.notNullString(paramString);
    }

    public List<String> getAtList() {
//        ArrayList localArrayList = new ArrayList();
//        String[] arrayOfString;
//        int i;
//        if (!StringUtils.isNullOrEmpty(this.at)) {
//            arrayOfString = this.at.split("\\|");
//            i = 0;
//        }
//        for (; ; ) {
//            if (i >= arrayOfString.length) {
//                return localArrayList;
//            }
//            localArrayList.add(arrayOfString[i]);
//            i += 1;
//        }

        return null;
    }

    public Integer getAtNum() {
        return getTagNum(this.at);
    }

    public String getContent() {
        return StringUtils.notNullString(this.content);
    }

    public void setContent(String paramString) {
        this.content = paramString;
    }

    public Integer getId() {
        if (this.id == null) {
        }
        for (int i = 0; ; i = this.id.intValue()) {
            return Integer.valueOf(i);
        }
    }

    public void setId(Integer paramInteger) {
        this.id = paramInteger;
    }

    public Boolean getIsAsynced() {
        return this.isasynced;
    }

    public void setIsAsynced(boolean paramBoolean) {
        this.isasynced = Boolean.valueOf(paramBoolean);
    }

    public String getLoc() {
        return StringUtils.notNullString(this.loc);
    }

    public void setLoc(String paramString) {
        this.loc = paramString;
    }

    public List<String> getLocList() {
//        ArrayList localArrayList = new ArrayList();
//        String[] arrayOfString;
//        int i;
//        if (!StringUtils.isNullOrEmpty(this.loc)) {
//            arrayOfString = this.loc.split("\\|");
//            i = 0;
//        }
//        for (; ; ) {
//            if (i >= arrayOfString.length) {
//                return localArrayList;
//            }
//            localArrayList.add(arrayOfString[i]);
//            i += 1;
//        }
        return null;
    }

    public Integer getLocNum() {
        return getTagNum(this.loc);
    }

    public String getPhoto() {

        return StringUtils.notNullString(this.photo);
    }

    public void setPhoto(String paramString) {
        this.photo = paramString;
    }

    public Bitmap getPhotoBitmap() {
//        Object localObject2 = null;
//        Photo localPhoto = getPhotoData();
//        Object localObject1 = localObject2;
//        if (localPhoto != null) {
//            localObject1 = localObject2;
//            if (localPhoto.getPath() != null) {
//                localObject1 = FileUtils.readAsBitmap(FileUtils.getPhotoPath() + localPhoto.getPath());
//            }
//        }
//        return localObject1;
        return null;
    }

    public Bitmap getPhotoBitmap(int paramInt1, int paramInt2) {
//        Object localObject2 = null;
//        Photo localPhoto = getPhotoData();
//        Object localObject1 = localObject2;
//        if (localPhoto != null) {
//            localObject1 = localObject2;
//            if (localPhoto.getPath() != null) {
//                localObject1 = FileUtils.readAsBitmap(FileUtils.getPhotoPath() + localPhoto.getPath());
//            }
//        }
//        return localObject1;
        return null;
    }

    public Photo getPhotoData() {
        if (hasPhoto().booleanValue()) {
            return new Photo(getPhoto());
        }
        return null;
    }

    public Bitmap getPhotoThumbBitmap() {
//        Object localObject2 = null;
//        Photo localPhoto = getPhotoData();
//        Object localObject1 = localObject2;
//        if (localPhoto != null) {
//            localObject1 = localObject2;
//            if (localPhoto.getPath() != null) {
//                localObject1 = FileUtils.readAsBitmap(FileUtils.getThumbsPath() + localPhoto.getPath());
//            }
//        }
//        return localObject1;
        return null;
    }

    public Date getTime() {
        return this.adddate;
    }

    public String getTopContent(int paramInt) {
        try {
            String str = this.content.substring(0, paramInt);
            return str;
        } catch (Exception localException) {
        }
        return this.content;
    }

    public String getUpdatedateStr() {
        if (this.updatedate != null) {
            return DateUtils.formatDate(updatedate, "yyyy-MM-dd HH:mm:ss");
        }
        return DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public String getUseremail() {
        return this.useremail;
    }

    public void setUseremail(String paramString) {
        this.useremail = paramString;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String paramString) {
        this.uuid = paramString;
    }

    public Boolean hasAt() {
        return TextUtils.isEmpty(at);
    }

    public Boolean hasLoc() {
        return TextUtils.isEmpty(loc);
    }

    public Boolean hasPhoto() {
        return TextUtils.isEmpty(photo);
    }

    public Boolean isContainAt(String paramString) {
        return Boolean.valueOf(getAtList().contains(paramString));
    }

    public Boolean isContainLoc(String paramString) {
        return Boolean.valueOf(getLocList().contains(paramString));
    }

    public boolean isPhotoExisted() {
//        boolean bool2 = false;
//        Object localObject = getPhotoData();
//        boolean bool1 = bool2;
//        if (localObject != null) {
//            bool1 = bool2;
//            if (((Photo) localObject).getPath() != null) {
//                localObject = new File(FileUtils.getPhotoPath(), ((Photo) localObject).getPath());
//                if ((localObject == null) || (!((File) localObject).exists()) || (!((File) localObject).isFile()) || (!((File) localObject).canRead())) {
//                    break label66;
//                }
//                bool1 = true;
//            }
//        }
//        return bool1;
//        label66:
        return false;
    }

    public void setUpdatedate(String paramString) {
        this.updatedate = DateUtils.transform(paramString, "yyyy-MM-dd HH:mm:ss");
    }

    public String toString() {
        return this.id + this.content + this.at + this.loc;
    }

    public Boolean getIsasynced() {
        return this.isasynced;
    }

    public void setIsasynced(Boolean isasynced) {
        this.isasynced = isasynced;
    }

    public Date getUpdatedate() {
        return this.updatedate;
    }

    public void setUpdatedate(Date paramDate) {
        this.updatedate = paramDate;
    }
}



/* Location:           D:\Program Files\AndroidKiller\projects\base\ProjectSrc\smali\

 * Qualified Name:     com.jiji.models.db.Memo

 * JD-Core Version:    0.7.0.1

 */