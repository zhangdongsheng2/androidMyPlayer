package net1.netutils;


import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @Author 冯高远
 * @Date 2016/7/28 10:30
 * @Description
 */
public class HttpUtil {

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES).build();

    public static OkHttpClient OkHttpClients = new OkHttpClient.Builder().sslSocketFactory(createSSLSocketFactory()).hostnameVerifier(new TrustAllHostnameVerifier()).build();


    /**
     * 区分 Http / Https 请求
     *
     * @param url 请求链接
     * @return
     */
    private static OkHttpClient getClient(String url) {
        if (TextUtils.isEmpty(url) || url.startsWith("http://")) {
            return okHttpClient;
        }
        return OkHttpClients;
    }

    /**
     * 异步 Post
     *
     * @param url
     * @param param
     * @return
     */
    public static void asynPost(String url, String param, final HttpCallBack httpCallBack) {
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Request build = new Request.Builder().url(url).post(requestBody).build();
        getClient(url).newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpCallBack.onFailure();

            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                try {
                    httpCallBack.onSuccess(response.body().string());
                } catch (IOException e) {
                    onFailure(call, e);
                }
            }
        });
    }

    /**
     * 同步 Post
     *
     * @param url
     * @param param
     * @return
     */
    public static String synPost(String url, String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Request build = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response execute = getClient(url).newCall(build).execute();
            return execute.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 同步     Get
     *
     * @param url
     * @return
     */
    public static String synGet(String url) {
        Request build = new Request.Builder().url(url).method("GET", null).build();
        okhttp3.Call call = getClient(url).newCall(build);
        try {
            Response execute = call.execute();
            return execute.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String synGet(String url, String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Request build = new Request.Builder().url(url).method("GET", requestBody).build();
        okhttp3.Call call = getClient(url).newCall(build);
        try {
            Response execute = call.execute();
            return execute.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步Get
     *
     * @param url
     * @param httpCallBack
     */
    public static void asynGet(String url, final HttpCallBack httpCallBack) {
        Request build = new Request.Builder().url(url).get().build();
        okhttp3.Call call = getClient(url).newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpCallBack.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                httpCallBack.onSuccess(response.body().string());
            }
        });
    }

    public static void asynGet(String url, String param, final HttpCallBack httpCallBack) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Request build = new Request.Builder().url(url).method("GET", requestBody).build();
        okhttp3.Call call = getClient(url).newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpCallBack.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                httpCallBack.onSuccess(response.body().string());
            }
        });
    }

    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    public interface HttpCallBack<T> {
        void onFailure();

        void onSuccess(T result);
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}
