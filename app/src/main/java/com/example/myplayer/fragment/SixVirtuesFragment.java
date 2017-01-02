package com.example.myplayer.fragment;

import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.util.ViewUtils;
import com.socks.library.KLog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/***
 * 测试页面
 * Four
 */
public class SixVirtuesFragment extends BaseFragment implements View.OnClickListener {
    private Button button_getSIMInfo;
    private TextView number;
    private TextView privoid;
    private TextView netState;
    private TextView ip;

    @Override
    protected View initView() {
        return ViewUtils.inflateView(R.layout.fragment_other);
    }

    @Override
    protected void initListener() {
//        button_getSIMInfo = (Button) mRootView.findViewById(R.id.getSIMInfo);
//        number = (TextView) mRootView.findViewById(R.id.textView1);
//        privoid = (TextView) mRootView.findViewById(R.id.textView2);
//        netState = (TextView) mRootView.findViewById(R.id.textView3);
//        ip = (TextView) mRootView.findViewById(R.id.textView4);
//        button_getSIMInfo.setOnClickListener(new ButtonListener());
        mRootView.findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

//        NetWorkFactory.getInstance().storeQuery(new Page(), new QueryStoreForCRequest(), new FilterSortMap(), new CallBack<Page<StoreViewModel>>() {
//            @Override
//            public void onSuccess(Page<StoreViewModel> result) {
//
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });


//        CompositeSubscription subscription = new CompositeSubscription();
//
//        PageRequest pageRequest = new PageRequest();
//        pageRequest.setClientCommonInfo(ClientCommonInfo.getInstance());
//        pageRequest.setParam(new QueryStoreForCRequest());
//        pageRequest.setPage(new Page());
//        KLog.e("请求网络");
//        subscription.add(//
//                UserService.createUserService().newsList(pageRequest)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<Object>() {
//                            @Override
//                            public void onCompleted() {
//                                KLog.e("oncompleted");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                KLog.e("失败");
//                            }
//
//                            @Override
//                            public void onNext(Object contributors) {
//                                KLog.json("aaaaaaa", contributors.toString());
//                            }
//                        }));

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("bnVtYmVy2", "15222222222")
                .add("cGFzc3dvcmQ", "123456")
                .add("cGFyZW50aWQ", "12121")
                .add("YWdlbnRBY2NvdW50", "111111")
                .add("cXVlc3Rpb25z", "111111")
                .add("YgsdkayTdsa", "111111")
                .build();
        try {
            KLog.e("bba");
            KLog.e(new String(android.util.Base64.encode("123".getBytes(), Base64.DEFAULT)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Request request = new Request.Builder()
                .url("http://122.114.47.56:8095/YUISAdyui1/sdfsyuto")
                .post(requestBody)
//                .addHeader("token", "helloworldhelloworldhelloworld")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                KLog.e("失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                KLog.e(request.body().toString());
            }
        });
    }

}
