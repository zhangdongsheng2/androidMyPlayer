package com.example.myplayer.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myplayer.R;
import com.example.myplayer.activity.BaseActivity;
import com.example.myplayer.db.Memo;
import com.example.myplayer.db.MemoDao;
import com.example.myplayer.util.APPUtil;
import com.example.myplayer.util.CommonUtil;
import com.example.myplayer.util.DateUtils;

/**
 * 编辑文档页面
 * Created by zhangdongsheng on 2017/1/19.
 */

public class DateRiJiMemoEditActivity extends BaseActivity {
    public static final String MODEL = "DateRiJiMemoEditActivityModel";
    private Memo memo;
    private EditText mEtContent;
    private MemoDao memoDao;
    private ImageView mIconBack;

    @Override
    protected int getContentView() {
        return R.layout.activity_date_riji_memo_edit;
    }


    @Override
    protected boolean initBundle(Bundle bundle) {
        memo = bundle.getParcelable(MODEL);
        return memo != null;
    }

    @Override
    protected void initWidget() {
        mEtContent = (EditText) findViewById(R.id.et_content);
        mIconBack = (ImageView) findViewById(R.id.icon_back);

        mEtContent.setText(memo.content);
        memoDao = APPUtil.getDaoSession().getMemoDao();
        mIconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMemo();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveMemo();
    }

    private void saveMemo() {
        if (mEtContent.getText().toString().equals(memo.content)) {
            finish();
            return;
        }

        CommonUtil.runOnThread(new Runnable() {
            @Override
            public void run() {
                memo.content = mEtContent.getText().toString();
                memo.updatedate = DateUtils.formatSystemDate();
                if (memo.id == null) {
                    memoDao.insert(memo);
                } else {
                    memoDao.update(memo);
                }
                finish();
            }
        });
    }
}
