package com.example.myplayer.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myplayer.R;
import com.example.myplayer.activity.leonids.ExampleListActivity;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.dialog.ChangeAddressDialog;
import com.example.myplayer.dialog.ChangeBirthDialog;
import com.example.myplayer.util.ViewUtils;

/**
 * Created by Administrator on 2016/3/31.
 */
public class NirvanaFragment extends BaseFragment {
    private TextView mBirth;
    private TextView mAddress;

    @Override
    protected View initView() {
        return ViewUtils.inflateView(R.layout.fragment_news);
    }

    @Override
    protected void initData() {
        mBirth = (TextView) mRootView.findViewById(R.id.tv_birth);
        mAddress = (TextView) mRootView.findViewById(R.id.tv_address);
        Button button = (Button) mRootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ExampleListActivity.class));
            }
        });

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


}
