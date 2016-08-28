package com.example.myplayer.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.test.TestJson;
import com.example.myplayer.util.FileUtil;
import com.example.myplayer.util.GsonTools;
import com.example.myplayer.util.ViewUtils;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.List;

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
        return ViewUtils.inflateView(R.layout.fragment_audio);
    }

    @Override
    protected void initListener() {
        button_getSIMInfo = (Button) mRootView.findViewById(R.id.getSIMInfo);
        number = (TextView) mRootView.findViewById(R.id.textView1);
        privoid = (TextView) mRootView.findViewById(R.id.textView2);
        netState = (TextView) mRootView.findViewById(R.id.textView3);
        ip = (TextView) mRootView.findViewById(R.id.textView4);
        button_getSIMInfo.setOnClickListener(new ButtonListener());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void processClick(View view) {

    }

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            try {
                String testJson = FileUtil.ReadFile(getActivity().getAssets().open("testJson"));
                List<TestJson> list_person = GsonTools.changeGsonToList(testJson, TestJson.class);

                KLog.e(testJson);
                KLog.e(list_person);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
