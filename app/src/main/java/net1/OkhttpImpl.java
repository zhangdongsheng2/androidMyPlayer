package net1;

import android.widget.Toast;

import com.example.myplayer.MyApplication;
import com.socks.library.KLog;

public class OkhttpImpl implements INetWork {

    @Override
    public void storeQuery(Page page, QueryStoreForCRequest queryStoreForCRequest, FilterSortMap filterSortMap, CallBack<Page<StoreViewModel>> exiuCallBack) {
        pageHttp(Url.Store.QueryRescueStores, page, queryStoreForCRequest, exiuCallBack, StoreViewModel.class);
    }

    /**
     * 普通的
     *
     * @param b    是否显示loading图
     * @param file 参数
     * @param back 回调
     * @param url
     */
    private void http(boolean b, String url, Object file, CallBack<?> back, Class clazz) {
        BasicRequest model = new BasicRequest();
        model.setClientCommonInfo(ClientCommonInfo.getInstance());
        model.setParam(file);
        baseHttp(b, url, model, back, clazz);
    }

    //分页的
    private void pageHttp(String url, Page page, Object request, CallBack<?> exiuCallBack, Class clazz) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setClientCommonInfo(ClientCommonInfo.getInstance());
        pageRequest.setParam(request);
        pageRequest.setPage(page);
        baseHttp(true, url, pageRequest, exiuCallBack, clazz);
    }

    private void baseHttp(final boolean b, final String url, final BasicRequest model, final CallBack back, final Class clazz) {
        String param = GsonHelper.toJson(model);
        KLog.e("接口" + url);
        Object param1 = model.getParam();
        if (param1 != null) {
            KLog.e("请求对象类型" + param1.getClass().toString());
        } else {
            KLog.e("请求对象为空");
        }
        KLog.json(param);
        final long start = System.currentTimeMillis();
        if (b) {
            //启动动画
        }
        HttpUtil.asynPost(url, param, new HttpUtil.HttpCallBack<String>() {
            @Override
            public void onFailure() {
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (b) {
                            //关闭加载动画
                        }
                        Toast.makeText(MyApplication.getInstance(), "网络异常", Toast.LENGTH_SHORT).show();
                        back.onFailure();
                    }
                });
            }

            @Override
            public void onSuccess(final String result) {
                if (clazz != null) {
                    KLog.e("返回对象类型" + clazz.toString());
                }
                KLog.json(result);
                //把请求日志时间等写入文件中下次启动上传
                KLog.d("本次执行时间：" + (System.currentTimeMillis() - start) + "ms");

                //处理请求的数据

//                MyApplication.getHandler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (b) {
//                            //关闭加载动画
//                        }
//                        try {
//                            Json jsonObject = new Json(result);
//                            if (jsonObject.getInt("errorCode") != 0) {
//                                if (TextUtils.isEmpty(jsonObject.getString("errorMessage"))) {
//                                    Toast.makeText(MyApplication.getInstance(), "服务器返回异常", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(MyApplication.getInstance(), jsonObject.getString("errorMessage"), Toast.LENGTH_SHORT).show();
//                                }
//                                back.onFailure();
//                            } else {
//                                String resultInfo = jsonObject.getString("result");
//                                if (model instanceof PageRequest) {
//                                    Page page = GsonHelper.fromJson(jsonObject.getString("page"), Page.class);
//                                    page.setDataList(GsonHelper.fromJsonList(resultInfo, clazz));
//                                    back.onSuccess(page);
//                                } else {
//                                    if (resultInfo.startsWith("[")) {
//                                        back.onSuccess(GsonHelper.fromJsonList(resultInfo, clazz));
//                                    } else {
//                                        if (clazz == null) {
//                                            back.onSuccess(null);
//                                        } else if (clazz == String.class) {
//                                            back.onSuccess(resultInfo);
//                                        } else {
//                                            back.onSuccess(GsonHelper.fromJson(resultInfo, clazz));
//                                        }
//                                    }
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(MyApplication.getInstance(), "服务器返回异常", Toast.LENGTH_SHORT).show();
//                            back.onFailure();
//                        }
//                    }
//                });
            }
        });


    }
}