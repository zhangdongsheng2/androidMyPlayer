package net1.interfaces;

import java.util.List;

/**
 * `
 * 车主端查询商家 请求模型
 */
public class QueryStoreForCRequest // : BaseFilterSortRequest
{
    private List<Integer> storeIds; // 商家id列表
    private String keyword; // 关键字
    private String areaCode = "310000"; // 地区编码
    private SearchInView searchInView; // 视野范围搜索
    private String sltCategory = "修理厂"; // 店铺类别
    private String carCode; // 汽车品牌代码
    private GisPoint userLocation = new GisPoint(); // 用户所在位置
    private Integer queryDistance = 99999999; // 距离检索
    private Integer couponAccountId; // 根据CouponId查查商家
    private List<Integer> excludeStoreIds;// 排除的商家id列表
    private String fullTextKeyWord;//如果该字段填写，则进入全文检索模式
    private Boolean isRecommend;// 不填代表全部商家，true只查询推荐商家，false只查询非推荐商家
    private List<Integer> labels;
    private String queryClaimType = "0";

    public Boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String value) {
        areaCode = value;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String value) {
        carCode = value;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String value) {
        keyword = value;
    }

    public Integer getQueryDistance() {
        return queryDistance;
    }

    public void setQueryDistance(Integer value) {
        queryDistance = value;
    }

    public SearchInView getSearchInView() {
        return searchInView;
    }

    public void setSearchInView(SearchInView value) {
        searchInView = value;
    }

    public String getSltCategory() {
        return sltCategory;
    }

    public void setSltCategory(String value) {
        sltCategory = value;
    }

    public List<Integer> getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(List<Integer> value) {
        storeIds = value;
    }

    public GisPoint getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(GisPoint value) {
        userLocation = value;
    }

    public Integer getCouponAccountId() {
        return couponAccountId;
    }

    public void setCouponAccountId(Integer couponAccountId) {
        this.couponAccountId = couponAccountId;
    }

    public List<Integer> getExcludeStoreIds() {
        return excludeStoreIds;
    }

    public void setExcludeStoreIds(List<Integer> excludeStoreIds) {
        this.excludeStoreIds = excludeStoreIds;
    }

    public String getFullTextKeyWord() {
        return fullTextKeyWord;
    }

    public void setFullTextKeyWord(String fullTextKeyWord) {
        this.fullTextKeyWord = fullTextKeyWord;
    }

    public List<Integer> getLabels() {
        return labels;
    }

    public void setLabels(List<Integer> labels) {
        this.labels = labels;
    }


    class GisPoint {
        private double lng = 121.435125; // 经度
        private double lat = 31.263359; // 维度

        public GisPoint() {
            super();
        }

        public GisPoint(double lng, double lat) {
            this.lng = lng;
            this.lat = lat;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double value) {
            lat = value;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double value) {
            lng = value;
        }
    }


    class SearchInView {
        private double lng_LB; // 经度(左下)
        private double lat_LB; // 维度(左下)
        private double lng_RT; // 经度(右上)
        private double lat_RT; // 维度(右上)

        public double getLat_LB() {
            return lat_LB;
        }

        public void setLat_LB(double value) {
            lat_LB = value;
        }

        public double getLat_RT() {
            return lat_RT;
        }

        public void setLat_RT(double value) {
            lat_RT = value;
        }

        public double getLng_LB() {
            return lng_LB;
        }

        public void setLng_LB(double value) {
            lng_LB = value;
        }

        public double getLng_RT() {
            return lng_RT;
        }

        public void setLng_RT(double value) {
            lng_RT = value;
        }
    }
}
