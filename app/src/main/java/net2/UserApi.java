package net2;

import net1.netutils.PageRequest;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by marno on 2016/7/21/20:55.
 */
public interface UserApi {

//    @POST("/api/Store/QueryRescueStores")
//    Observable<String> newsList(@Body Page page, @Body QueryStoreForCRequest queryStoreForCRequest, @Body FilterSortMap filterSortMap);


    @POST("/api/Store/QueryRescueStores")
    Observable<Object> newsList(@Body PageRequest rest);

}
