package com.example.myplayer.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.util.ViewUtils;
import com.socks.library.KLog;

import net1.interfaces.Page;
import net1.interfaces.QueryStoreForCRequest;
import net1.netutils.ClientCommonInfo;
import net1.netutils.PageRequest;
import net2.UserService;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/***
 * 测试页面
 */
public class OtherFragment extends BaseFragment {
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
    protected void initData() {

    }

    @Override
    protected void processClick(View view) {

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


        CompositeSubscription subscription = new CompositeSubscription();

        PageRequest pageRequest = new PageRequest();
        pageRequest.setClientCommonInfo(ClientCommonInfo.getInstance());
        pageRequest.setParam(new QueryStoreForCRequest());
        pageRequest.setPage(new Page());
        subscription.add(//
                UserService.createUserService().newsList(pageRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Object contributors) {
                                KLog.json("aaaaaaa", contributors.toString());
                            }
                        }));


    }


}
