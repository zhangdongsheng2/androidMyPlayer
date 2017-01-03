package net1;

import java.util.Map;

/**
 * 过滤，排序基类
 */
public class FilterSortMap {
    private Map<String, String> filterMap; // 过滤 list.get(1)
    private Map<String, String> sortMap; // 排序 list.get(0)

    public Map<String, String> getFilterMap() {
        return filterMap;
    }

    public void setFilterMap(Map<String, String> filterMap) {
        this.filterMap = filterMap;
    }

    public Map<String, String> getSortMap() {
        return sortMap;
    }

    public void setSortMap(Map<String, String> sortMap) {
        this.sortMap = sortMap;
    }


}
