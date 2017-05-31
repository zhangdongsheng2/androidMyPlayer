package com.example.myplayer.emojis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.util.CommonUtil;
import com.example.myplayer.widget.RichEditText;


/**
 * Created by haibin
 * on 2016/11/10.
 * Change by fei
 * on 2016/11/17
 * desc:详情页输入框
 */
@SuppressWarnings("all")
public class CommentBar {

    private Context mContext;
    private View mRootView;
    private FrameLayout mFrameLayout;
    private ViewGroup mParent;
    private ImageButton mFavView;
    private ImageButton mShareView;
    private TextView mCommentText;
    private BottomSheetBar mDelegation;
    private LinearLayout mCommentLayout;


    private CommentBar(Context context) {
        this.mContext = context;
    }

    public static CommentBar delegation(Context context, ViewGroup parent) {
        CommentBar bar = new CommentBar(context);
        bar.mRootView = LayoutInflater.from(context).inflate(R.layout.layout_comment_bar, parent, false);
        bar.mParent = parent;
        bar.mDelegation = BottomSheetBar.delegation(context);
        bar.mParent.addView(bar.mRootView);
        bar.initView();
        return bar;
    }

    private void initView() {
        //((CoordinatorLayout.LayoutParams) mRootView.getLayoutParams()).setBehavior(new FloatingAutoHideDownBehavior());
        mFavView = (ImageButton) mRootView.findViewById(R.id.ib_fav);
        mShareView = (ImageButton) mRootView.findViewById(R.id.ib_share);
        mCommentText = (TextView) mRootView.findViewById(R.id.tv_comment);
        mCommentLayout = (LinearLayout) mRootView.findViewById(R.id.ll_comment);
        mCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (AccountHelper.isLogin()) {//判断是否登陆  如果登陆显示  非登录 显示登陆页面
                mDelegation.show(mCommentText.getHint().toString());
//                } else {
//                    LoginActivity.show(mContext);
//                }
            }
        });
    }

    /**
     * share 2 three sdk
     *
     * @param listener
     */
    public void setShareListener(View.OnClickListener listener) {
        mShareView.setOnClickListener(listener);
    }

    /**
     * favorite the detail
     *
     * @param listener
     */
    public void setFavListener(View.OnClickListener listener) {
        mFavView.setOnClickListener(listener);
    }

    public void setCommentListener(View.OnClickListener listener) {
        mCommentText.setOnClickListener(listener);
    }

    public void setCommentHint(String text) {
        mCommentText.setHint(text);
    }

    public void setFavDrawable(int drawable) {
        mFavView.setImageResource(drawable);
    }

    public BottomSheetBar getBottomSheet() {
        return mDelegation;
    }

    public void setCommitButtonEnable(boolean enable) {
        mDelegation.getBtnCommit().setEnabled(enable);
    }

    public void hideShare() {
        mShareView.setVisibility(View.GONE);
    }

    public void hideFav() {
        mFavView.setVisibility(View.GONE);
    }

    public TextView getCommentText() {
        return mCommentText;
    }


    public void performClick() {
        mCommentLayout.performClick();
    }


    //点击输入评论 弹出Dialog  大的输入框
    public static class BottomDialog extends BottomSheetDialog {

        private BottomSheetBehavior behavior;
        private boolean isTranslucentStatus;

        public BottomDialog(@NonNull Context context, boolean isTranslucentStatus) {
            super(context);
            this.isTranslucentStatus = isTranslucentStatus;
            Window window = getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (isTranslucentStatus)
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }

        @Override
        public void setContentView(View view) {
            super.setContentView(view);
            initialize(view);
        }

        @Override
        public void show() {
            super.show();
            //behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        private void initialize(final View view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
            behavior = (BottomSheetBehavior) params.getBehavior();
            behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss();
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        //CommonUtil.hideSoftKeyboard(view);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }
    }

    //点击后显示在Dialog上的内容
    public static class BottomSheetBar {

        private View mRootView;
        private RichEditText mEditText;
        private ImageButton mAtView;
        private ImageButton mFaceView;
        private CheckBox mSyncToTweetView;
        private Context mContext;
        private Button mBtnCommit;
        private BottomDialog mDialog;
        private FrameLayout mFrameLayout;
        private EmojiView mEmojiView;


        private BottomSheetBar(Context context) {
            this.mContext = context;
        }

        @SuppressLint("InflateParams")
        public static BottomSheetBar delegation(Context context) {
            BottomSheetBar bar = new BottomSheetBar(context);
            bar.mRootView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet_comment_bar, null, false);
            bar.initView();
            return bar;
        }

        private void initView() {
            mFrameLayout = (FrameLayout) mRootView.findViewById(R.id.fl_face);
            mEditText = (RichEditText) mRootView.findViewById(R.id.et_comment);
            mAtView = (ImageButton) mRootView.findViewById(R.id.ib_mention);
            mFaceView = (ImageButton) mRootView.findViewById(R.id.ib_face);
            mFaceView.setVisibility(View.GONE);
            mSyncToTweetView = (CheckBox) mRootView.findViewById(R.id.cb_sync);
            if (mFaceView.getVisibility() == View.GONE) {
                mSyncToTweetView.setText("转发到动弹");
            }
            mBtnCommit = (Button) mRootView.findViewById(R.id.btn_comment);
            mBtnCommit.setEnabled(false);

            mDialog = new BottomDialog(mContext, false);
            mDialog.setContentView(mRootView);

            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    CommonUtil.closeKeyboard(mEditText);
                    mFrameLayout.setVisibility(View.GONE);
                }
            });

            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mBtnCommit.setEnabled(s.length() > 0);
                }
            });
        }

        public void hideSyncAction() {
            mSyncToTweetView.setVisibility(View.INVISIBLE);
            mSyncToTweetView.setText(null);
        }

        public void hideMentionAction() {
            mAtView.setVisibility(View.INVISIBLE);
        }

        /**
         * 默认显示的
         */
        public void showSyncAction() {
            mSyncToTweetView.setVisibility(View.VISIBLE);
            mSyncToTweetView.setText("转发到动弹");
        }

        public void showEmoji() {
            mSyncToTweetView.setText("发布动弹");
            mFaceView.setVisibility(View.VISIBLE);
            mFaceView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mEmojiView == null) {
                        mEmojiView = new EmojiView(mContext, mEditText);
                        mFrameLayout.addView(mEmojiView);
                    }
                    mFrameLayout.setVisibility(View.VISIBLE);
                    mEmojiView.openPanel();
                    CommonUtil.closeKeyboard(mEditText);
                }
            });

            mEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFrameLayout.setVisibility(View.GONE);
                }
            });
        }

        public void show(String hint) {
            mDialog.show();
            if (!"添加评论".equals(hint)) {
                mEditText.setHint(hint + " ");
            }
            mRootView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    CommonUtil.showSoftKeyboard(mEditText);
                }
            }, 50);
        }

        public void dismiss() {
            CommonUtil.closeKeyboard(mEditText);
            mDialog.dismiss();
        }

        public void setMentionListener(View.OnClickListener listener) {
            mAtView.setOnClickListener(listener);
        }

        public void setFaceListener(View.OnClickListener listener) {
            mFaceView.setOnClickListener(listener);
        }

        public void setCommitListener(View.OnClickListener listener) {
            mBtnCommit.setOnClickListener(listener);
        }

        public void handleSelectFriendsResult(Intent data) {
            String names[] = data.getStringArrayExtra("names");
            if (names != null && names.length > 0) {
                String text = "";
                for (String n : names) {
                    text += "@" + n + " ";
                }
                mEditText.getText().insert(mEditText.getSelectionEnd(), text);
            }
        }

        public RichEditText getEditText() {
            return mEditText;
        }

        public String getCommentText() {
            return mEditText.getText().toString().trim();
        }

        public Button getBtnCommit() {
            return mBtnCommit;
        }

        public boolean isSyncToTweet() {
            return mSyncToTweetView != null && mSyncToTweetView.isChecked();
        }

    }
}
