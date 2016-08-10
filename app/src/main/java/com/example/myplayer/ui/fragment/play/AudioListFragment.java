package com.example.myplayer.ui.fragment.play;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.util.SIMCardNetInfo;
import com.example.myplayer.util.ViewUtils;

/**
 * Created by Administrator on 2016/3/31.
 */
public class AudioListFragment extends BaseFragment {
    private Button button_getSIMInfo;
    private TextView number;
    private TextView privoid;
    private TextView netState;

    @Override
    protected View initView() {
        return ViewUtils.inflateView(R.layout.fragment_audio);
    }

    @Override
    protected void initListener() {
        button_getSIMInfo = (Button) mRootView.findViewById(R.id.getSIMInfo);
        number = (TextView) mRootView.findViewById(R.id.textView1);
        privoid = (TextView) mRootView.findViewById(R.id.textView2);
        netState = (TextView) mRootView.findViewById(R.id.textView3);
        button_getSIMInfo.setOnClickListener(new ButtonListener());
    }

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == button_getSIMInfo) {
                SIMCardNetInfo siminfo = new SIMCardNetInfo(getActivity());
                System.out.println(siminfo.getProvidersName());
                System.out.println(siminfo.getNativePhoneNumber());
                number.setText(siminfo.getNativePhoneNumber());
                privoid.setText(siminfo.getProvidersName());
                netState.setText(siminfo.getNetworkState());
            }
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void processClick(View view) {

    }
}
