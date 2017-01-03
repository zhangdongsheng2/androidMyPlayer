package net1;


public interface AccountInterface {


    /**
     * 车主端搜索店铺
     *
     * @param page
     * @param queryStoreForCRequest
     * @param exiuCallBack
     * @return
     */
    void storeQuery(Page page, QueryStoreForCRequest queryStoreForCRequest, FilterSortMap filterSortMap,
                    CallBack<Page<StoreViewModel>> exiuCallBack);

}
