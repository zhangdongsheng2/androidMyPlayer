package com.example.myplayer.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.util.ViewUtils;

/**
 * Created by Administrator on 2016/3/31.
 */
public class NewsFragment extends BaseFragment {
    private TextView mBirth;
    private TextView mAddress;

    @Override
    protected View initView() {
        return ViewUtils.inflateView(R.layout.fragment_news);
    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void initData() {
        mBirth = (TextView) mRootView.findViewById(R.id.tv_birth);
        mAddress = (TextView) mRootView.findViewById(R.id.tv_address);

        mBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(
                        getActivity());
                mChangeBirthDialog.setDate(2015, 03, 29);
                mChangeBirthDialog.show();
                mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getActivity(),
                                year + "-" + month + "-" + day,
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mAddress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ChangeAddressDialog mChangeAddressDialog = new ChangeAddressDialog(
                        getActivity());
                mChangeAddressDialog.setAddress("四川", "自贡");
                mChangeAddressDialog.show();
                mChangeAddressDialog
                        .setAddresskListener(new ChangeAddressDialog.OnAddressCListener() {

                            @Override
                            public void onClick(String province, String city) {
                                // TODO Auto-generated method stub
                                Toast.makeText(getActivity(),
                                        province + "-" + city,
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    @Override
    protected void processClick(View view) {

    }
}
