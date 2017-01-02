package com.example.myplayer.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.myplayer.Const;
import com.example.myplayer.R;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.util.SPHelper;
import com.example.myplayer.util.ViewUtils;
import com.example.myplayer.widget.DraggableGridAdapter;
import com.example.myplayer.widget.DraggableGridViewPager;

/**
 * Created by Administrator on 2016/3/31.
 * Two
 */
public class NirvanaFragment extends BaseFragment {
    private TextView mBirth;
    private TextView mAddress;
    private DraggableGridViewPager myGridview;

    @Override
    protected View initView() {
        return ViewUtils.inflateView(R.layout.fragment_nirvana);
    }

    @Override
    protected void initData() {
        myGridview = (DraggableGridViewPager) mRootView.findViewById(R.id.homepage_gridview);

        String content = SPHelper.getInstance().getString("homepage_layout_", "");

        if (TextUtils.isEmpty(content)) {
            // 首次为空，采取默认布局
            // 1,代表模块编号
            String order = Const.ORDER;
            SPHelper.getInstance().getString("homepage_layout_", order);
            content = order;
        }

        DraggableGridAdapter mAdapter = new DraggableGridAdapter(getContext(), content, myGridview);
        myGridview.setAdapter(mAdapter);
    }


}
