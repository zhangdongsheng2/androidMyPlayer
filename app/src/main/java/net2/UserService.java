package net2;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class UserService {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES).build();

    private UserService() {
    }

    public static UserApi createUserService() {
        Retrofit.Builder builder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new MyGsonConverterFactory())
                .baseUrl("http://api.114995.com");


//            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request();
//                    Request newReq = request.newBuilder()
//                            .addHeader("Authorization", "")
//                            .build();
//                    return chain.proceed(newReq);
//                }
//            }).build();

        builder.client(okHttpClient);

        return builder.build().create(UserApi.class);
    }


}
