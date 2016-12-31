package com.example.myplayer.fragment.play;

import android.view.View;

import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.view.AudioListFragmentView;

/**
 * Created by Administrator on 2016/3/31.
 */
public class AudioListFragment extends BaseFragment {
    @Override
    protected View initView() {
        return AudioListFragmentView.getInstance(getContext(), this);
    }
}
