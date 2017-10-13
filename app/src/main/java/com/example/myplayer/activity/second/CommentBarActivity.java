package com.example.myplayer.activity.second;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.activity.BaseActivity;
import com.example.myplayer.emojis.CommentBar;
import com.example.myplayer.emojis.InputHelper;
import com.socks.library.KLog;

import butterknife.BindView;

/**
 * Created by zhangdongsheng on 2017/5/31.
 * 表情
 */

public class CommentBarActivity extends BaseActivity {

    @BindView(R.id.ll_container)
    LinearLayout mCoordinatorLayout;
    private CommentBar mDelegation;

    @Override
    protected int getContentView() {
        return R.layout.activity_comment_bar;
    }

    @Override
    protected void initWidget() {
        mDelegation = CommentBar.delegation(this, mCoordinatorLayout);
        mDelegation.hideShare();
        mDelegation.hideFav();
        mDelegation.getBottomSheet().showEmoji();
        mDelegation.getBottomSheet().hideSyncAction();
        mDelegation.getBottomSheet().hideMentionAction();
        mDelegation.getBottomSheet().setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mDelegation.getBottomSheet().getCommentText().replaceAll("[\\s\\n]+", " ");
                KLog.e("CommentBar", content);

                TextView tvContent = new TextView(CommentBarActivity.this);
                tvContent.setText(InputHelper.displayEmoji(getResources(), content));


                mCoordinatorLayout.addView(tvContent, 0);
            }
        });

    }
}
