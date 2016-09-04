package net1.interfaces;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

public class StoreViewModel implements Serializable {
    private static final long serialVersionUID = 5375645067151419616L;
    /// <summary>
    /// 是否能卖保险
    /// </summary>
    public boolean canSellInsurance;
    private Integer storeId; // 商家店铺Id
    private String name; // 店铺名称
    private double distance; // 距离
    private Double latitude;// 维度
    private Double longitude; // 经度
    private String qQ; // QQ
    private String contact; // 联系人
    private String address; // 联系地址
    private String areaCode; // 区域编码
    private String areaName; // 区域编码名称
    private String openStartTime; // 营业开始时间
    private String openEndTime; // 营业结束时间
    private String contactTel1; // 联系电话1
    private String contactTel2; // 联系电话2
    private String contactTel3; // 联系电话3
    private String contactMobile1; // 联系手机1
    private String contactMobile2; // 联系手机2
    private String contactMobile3; // 联系手机3
    private java.sql.Timestamp createDate; // 创建时间
    private String mainFeatures; // 主营业务
    private String keywords; // 关键词
    private int score; // 评分
    private String email; // 邮箱地址
    private boolean isRegComplete; // 资料是否完整
    private String applyStatus; // 是否通过审核
    private String source; // 来源
    private Integer storeOwnerId; // 店铺所有者用户Id
    private Integer category; // 主营类别Id
    private String categoryName; // 主营类别名称
    private Double balance; // 帐户余额
    private List<String> carCodes; // 适用车型
    private Integer dataTypistId; // 采集人员id
    private boolean asPlatformAd; // 作为平台推广商家
    private String userName; // 用户名
    private String password; // 初始密码，只在采集端使用
    private int reviewCount; // 车主对店铺评论数量
    private String sltCategory = "停车场"; // （选择）主营类别
    private String sltSecCategory; // （选择）兼营
    private String sltSpecialBrands; // （选择） 专修品牌
    private boolean haveCoupon; // 是否有优惠
    // private boolean isReviewed;// 是否已评价
    private String storeOwnerName; // 店铺所有者用户名
    //	private boolean canRequestPreferentialCoupon; // 是否可以领取特惠券
    private boolean haseCouponForDraw;// 是否有券
    private boolean haveFixer; //
    private boolean isRelateCarBrand; // 是否关联车辆品牌
    private boolean isShowAlliance; // 是否显示联盟
    private boolean isShowProduct; // 是否显示产品
    private boolean hasFavorite; // 当前用户是否已经收藏
    private boolean allowInvited;// 是否能被邀请
    private String pvPretty;// 页面浏览量(美化显示，界面直接取该字段)
    private boolean isRecommend;// 是否推荐商家
    private boolean haseRealGoods;// 是否有商品
    private boolean haseService;// 是否有服务
    private boolean hasePackage;// 是否有套餐
    private boolean haseAlliance;// 是否有联盟
    private boolean haseStoreCoupon;//是否有店铺券
    /*****
     * 自定义字段
     *****/
    // 以下字段为非网络接口对应字段,根据需要自行添加
    private String contactTel;// 固定电话 (固定电话123拼接组成,逗号','隔开)
    private String contactMobile; // 联系手机 (联系手机123拼接组成,逗号','隔开)
    private String openTime; // 营业时间 (openStartTime 和openEndTime拼接组成,井号'#'隔开)
    private String className = this.getClass().getName();
    private boolean isMarked;// 车主端收藏商家，是否被选中（以供删除）
    private Integer complainantUserId; // 申诉人用户id
    private String complainantContact; // 冒领申诉人联系方式
    private String complainDate; // 冒领申诉日期
    private String appealContact; // 申领人联系方式
    private String appealDate; // 申领日期
    private String applyRefuseReason;// 审核失败原因
    // 营业执照图片地址的审核状态
    // 前台使用时，请判断如果字符串为空时，什么状态也不要显示。
    private String businessLicencesPicApplyStatus;
    // 营业执照图片地址的审核失败原因
    private String businessLicencesPicApplyRefuseReason;
    // 法人身份证图片地址审核状态
    // 前台使用时，请判断如果字符串为空时，什么状态也不要显示。
    private String ownerIdentityPicApplyStatus;
    // 法人身份证图片地址审核失败原因
    private String ownerIdentityPicApplyRefuseReason;
    /**
     * 庞立
     * 一下为百度地图显示需要的数据处理
     */
    private double startLatitude;
    private double startLongitude;

    public StoreViewModel() {
        super();
        setName(getSltCategory());
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double value) {
        distance = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer value) {
        storeId = value;
    }

    public String getBusinessLicencesPicApplyStatus() {
        return businessLicencesPicApplyStatus;
    }

    public void setBusinessLicencesPicApplyStatus(String businessLicencesPicApplyStatus) {
    }

    public String getBusinessLicencesPicApplyRefuseReason() {
        return businessLicencesPicApplyRefuseReason;
    }

    public void setBusinessLicencesPicApplyRefuseReason(String businessLicencesPicApplyRefuseReason) {
    }

    public String getOwnerIdentityPicApplyStatus() {
        return ownerIdentityPicApplyStatus;
    }

    public void setOwnerIdentityPicApplyStatus(String ownerIdentityPicApplyStatus) {
    }

    public String getOwnerIdentityPicApplyRefuseReason() {
        return ownerIdentityPicApplyRefuseReason;
    }

    public void setOwnerIdentityPicApplyRefuseReason(String ownerIdentityPicApplyRefuseReason) {
    }


    public boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean isMarked) {
        this.isMarked = isMarked;
    }

    public String getqQ() {
        return qQ;
    }

    public void setqQ(String qQ) {
        this.qQ = qQ;
    }

    public String getStoreOwnerName() {
        return storeOwnerName;
    }

    public void setStoreOwnerName(String storeOwnerName) {
        this.storeOwnerName = storeOwnerName;
    }

    public boolean isHaveFixer() {
        return haveFixer;
    }

    public void setHaveFixer(boolean haveFixer) {
        this.haveFixer = haveFixer;
    }

    public boolean isRelateCarBrand() {
        return isRelateCarBrand;
    }

    public void setRelateCarBrand(boolean isRelateCarBrand) {
        this.isRelateCarBrand = isRelateCarBrand;
    }

    public boolean isShowAlliance() {
        return isShowAlliance;
    }

    public void setShowAlliance(boolean isShowAlliance) {
        this.isShowAlliance = isShowAlliance;
    }

    public boolean isShowProduct() {
        return isShowProduct;
    }

    public void setShowProduct(boolean isShowProduct) {
        this.isShowProduct = isShowProduct;
    }

    public boolean isHasFavorite() {
        return hasFavorite;
    }

    public void setHasFavorite(boolean hasFavorite) {
        this.hasFavorite = hasFavorite;
    }

    public boolean isHaseCouponForDraw() {
        return haseCouponForDraw;
    }

    public void setHaseCouponForDraw(boolean haseCouponForDraw) {
        this.haseCouponForDraw = haseCouponForDraw;
    }

    public void setRegComplete(boolean isRegComplete) {
        this.isRegComplete = isRegComplete;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String value) {
        address = value;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String value) {
        applyStatus = value;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String value) {
        areaCode = value;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String value) {
        areaName = value;
    }

    public boolean getAsPlatformAd() {
        return asPlatformAd;
    }

    public void setAsPlatformAd(boolean value) {
        asPlatformAd = value;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double value) {
        balance = value;
    }


    public List<String> getCarCodes() {
        return carCodes;
    }

    public void setCarCodes(List<String> value) {
        carCodes = value;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer value) {
        category = value;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String value) {
        categoryName = value;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String value) {
        contact = value;
    }


    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
        setContactMobile1("");
        setContactMobile2("");
        setContactMobile3("");
        String[] mobiles;
        mobiles = contactMobile.split(",");
        for (int i = 0; i < mobiles.length; i++) {
            if (i == 0) {
                setContactMobile1(mobiles[0]);
            } else if (i == 1) {
                setContactMobile2(mobiles[1]);
            } else if (i == 2) {
                setContactMobile3(mobiles[2]);
            }
        }
    }

    public String getContactMobile1() {
        return contactMobile1;
    }

    public void setContactMobile1(String value) {
        contactMobile1 = value;
    }

    public String getContactMobile2() {
        return contactMobile2;
    }

    public void setContactMobile2(String value) {
        contactMobile2 = value;
    }

    public String getContactMobile3() {
        return contactMobile3;
    }

    public void setContactMobile3(String value) {
        contactMobile3 = value;
    }


    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
        setContactTel1("");
        setContactTel2("");
        setContactTel3("");
        String[] tels;
        tels = contactTel.split(",");
        for (int i = 0; i < tels.length; i++) {
            if (i == 0) {
                setContactTel1(tels[0]);
            } else if (i == 1) {
                setContactTel2(tels[1]);
            } else if (i == 2) {
                setContactTel3(tels[2]);
            }
        }
    }

    public String getContactTel1() {
        return contactTel1;
    }

    public void setContactTel1(String value) {
        contactTel1 = value;
    }

    public String getContactTel2() {
        return contactTel2;
    }

    public void setContactTel2(String value) {
        contactTel2 = value;
    }

    public String getContactTel3() {
        return contactTel3;
    }

    public void setContactTel3(String value) {
        contactTel3 = value;
    }

    public java.sql.Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.sql.Timestamp value) {
        createDate = value;
    }

    public Integer getDataTypistId() {
        return dataTypistId;
    }

    public void setDataTypistId(Integer value) {
        dataTypistId = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        email = value;
    }

    public boolean getIsRegComplete() {
        return isRegComplete;
    }

    public void setIsRegComplete(boolean value) {
        isRegComplete = value;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String value) {
        keywords = value;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double value) {
        latitude = value;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double value) {
        longitude = value;
    }

    public String getMainFeatures() {
        return mainFeatures;
    }

    public void setMainFeatures(String value) {
        mainFeatures = value;
    }

    public String getOpenEndTime() {
        return openEndTime;
    }

    public void setOpenEndTime(String value) {
        openEndTime = value;
    }

    public String getOpenStartTime() {
        return openStartTime;
    }

    public void setOpenStartTime(String value) {
        openStartTime = value;
    }


    public void setOpenTime(String openTime) {
        this.openTime = openTime;
        // "openTime": "07:00#18:00",
        if (!TextUtils.isEmpty(openTime) && openTime.contains("#")) {
            String temp[] = openTime.split("#");
            setOpenStartTime(temp[0]);
            setOpenEndTime(temp[1]);
        }
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        password = value;
    }

    public String getQQ() {
        return qQ;
    }

    public void setQQ(String value) {
        qQ = value;
    }


    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int value) {
        reviewCount = value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int value) {
        score = value;
    }

    public String getSltCategory() {
        return sltCategory;
    }

    public void setSltCategory(String value) {
        sltCategory = value;
    }

    public String getSltSpecialBrands() {
        return sltSpecialBrands;
    }

    public void setSltSpecialBrands(String value) {
        sltSpecialBrands = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String value) {
        source = value;
    }

    public Integer getStoreOwnerId() {
        return storeOwnerId;
    }

    public void setStoreOwnerId(Integer value) {
        storeOwnerId = value;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String value) {
        userName = value;
    }

    public boolean getHaveCoupon() {
        return haveCoupon;
    }

    public void setHaveCoupon(boolean value) {
        haveCoupon = value;
    }


    // 获取商家的手机号
    public String Telphone() {
        String telPhone;

        telPhone = (contactMobile1 != null && !contactMobile1.equals("")) ? contactMobile1
                : (contactMobile2 != null && !contactMobile2.equals("")) ? contactMobile2
                : (contactMobile3 != null && !contactMobile3.equals("")) ? contactMobile3 : "";
        return telPhone;
    }


    public void setMyLocate(double startLatitude, double startLongitude) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
    }


    public String getSltSecCategory() {
        return sltSecCategory;
    }

    public void setSltSecCategory(String sltSecCategory) {
        this.sltSecCategory = sltSecCategory;
    }

    public Integer getComplainantUserId() {
        return complainantUserId;
    }

    public void setComplainantUserId(Integer complainantUserId) {
        this.complainantUserId = complainantUserId;
    }

    public String getComplainantContact() {
        return complainantContact;
    }

    public void setComplainantContact(String complainantContact) {
        this.complainantContact = complainantContact;
    }


    public String getComplainDate() {
        return complainDate;
    }

    public void setComplainDate(String complainDate) {
        this.complainDate = complainDate;
    }

    public String getAppealContact() {
        return appealContact;
    }

    public void setAppealContact(String appealContact) {
        this.appealContact = appealContact;
    }


    public String getAppealDate() {
        return appealDate;
    }

    public void setAppealDate(String appealDate) {
        this.appealDate = appealDate;
    }

    public String getApplyRefuseReason() {
        return applyRefuseReason;
    }

    public void setApplyRefuseReason(String applyRefuseReason) {
        this.applyRefuseReason = applyRefuseReason;
    }

    public boolean isAllowInvited() {
        return allowInvited;
    }

    public void setAllowInvited(boolean allowInvited) {
        this.allowInvited = allowInvited;
    }

    public String getUserNameAndPassword() {
        if (null == getUserName()) {
            return "#";
        }
        if (null == getPassword()) {
            return getUserName() + "#";
        }
        return getUserName() + "#" + getPassword();
    }

    public void setUserNameAndPassword(String up) {
        if (!TextUtils.isEmpty(up)) {
            if (up.contains("#")) {
                String[] split = up.split("#");
                if (split.length > 1) {
                    setUserName(split[0]);
                    setPassword(split[1]);
                } else if (split.length == 1) {
                    setUserName(split[0]);
                }
            }
        }
    }

    public String getPvPretty() {
        return pvPretty;
    }

    public void setPvPretty(String pvPretty) {
        this.pvPretty = pvPretty;
    }


    public String getM_PvPretty() {
        return "浏览量" + getPvPretty();
    }

    public void setM_PvPretty(String m_PvPretty) {

    }

    public boolean isHaseAlliance() {
        return haseAlliance;
    }

    public void setHaseAlliance(boolean haseAlliance) {
        this.haseAlliance = haseAlliance;
    }

    public boolean isHaseService() {
        return haseService;
    }

    public void setHaseService(boolean haseService) {
        this.haseService = haseService;
    }

    public boolean isHasePackage() {
        return hasePackage;
    }

    public void setHasePackage(boolean hasePackage) {
        this.hasePackage = hasePackage;
    }

    public boolean isHaseRealGoods() {
        return haseRealGoods;
    }

    public void setHaseRealGoods(boolean haseRealGoods) {
        this.haseRealGoods = haseRealGoods;
    }

    public boolean isHaseStoreCoupon() {
        return haseStoreCoupon;
    }

    public void setHaseStoreCoupon(boolean haseStoreCoupon) {
        this.haseStoreCoupon = haseStoreCoupon;
    }

    public boolean isCanSellInsurance() {
        return canSellInsurance;
    }

    public void setCanSellInsurance(boolean canSell) {
        canSellInsurance = canSell;
    }
}
