package com.example.myplayer.widget.level;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.myplayer.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 2016/2/25
 *
 * @author 显示信用等级TextView
 *         注：@—皇冠，#—钻石，￥—太阳，%—月亮，&—星星
 */
public class ExiuLevelTextView extends TextView {
    private int mCreditSize;
    private int mCreditAlignment;
    private int mCreditTextSize;
    private int mTextStart = 0;
    private int mTextLength = -1;
    private Drawable mDrawable;

    private static Map<String, Integer> sCreditDrawableMap = new HashMap<>();

    //用来匹配的字符
    static {
        sCreditDrawableMap.put("@", R.drawable.credit_crown);
        sCreditDrawableMap.put("#", R.drawable.credit_diamond);
        sCreditDrawableMap.put("$", R.drawable.credit_ooopic);
        sCreditDrawableMap.put("%", R.drawable.credit_half_moo);
        sCreditDrawableMap.put("&", R.drawable.credit_star);
    }

    private static int getSoftbankCreditResource(char c) {
        if (sCreditDrawableMap.containsKey(String.valueOf(c))) {
            return sCreditDrawableMap.get(String.valueOf(c));
        }
        return 0;
    }


    public ExiuLevelTextView(Context context) {
        super(context);
        init(null);
    }

    public ExiuLevelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ExiuLevelTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mCreditTextSize = (int) getTextSize();
        if (attrs == null) {
            mCreditSize = (int) getTextSize();
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Credit);
            mCreditSize = (int) a.getDimension(R.styleable.Credit_creditSize, getTextSize());
            mCreditAlignment = a.getInt(R.styleable.Credit_creditAlignment, DynamicDrawableSpan.ALIGN_BASELINE);
            mTextStart = a.getInteger(R.styleable.Credit_creditTextStart, 0);
            mTextLength = a.getInteger(R.styleable.Credit_creditTextLength, -1);
            a.recycle();
        }
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)) {
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            int textLength = text.length();
            int textLengthToProcessMax = textLength - mTextStart;
            int textLengthToProcess = mTextLength < 0 || mTextLength >= textLengthToProcessMax ? textLength : (mTextLength + mTextStart);
            DynamicDrawableSpan[] oldSpans = builder.getSpans(0, textLength, DynamicDrawableSpan.class);
            for (int i = 0; i < oldSpans.length; i++) {
                builder.removeSpan(oldSpans[i]);
            }
            for (int i = mTextStart; i < textLengthToProcess; i++) {
                final int icon;
                char c = builder.charAt(i);
                icon = getSoftbankCreditResource(c);
                if (icon > 0) {//如果匹配到就替换 匹配不到还显示原来的文字
                    builder.setSpan(getDynamicDrawableSpan(icon), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            text = builder;
        }
        super.setText(text, type);
    }

    //文字转图标
    private DynamicDrawableSpan getDynamicDrawableSpan(final int icon) {
        return new DynamicDrawableSpan(mCreditAlignment) {
            @Override
            public Drawable getDrawable() {
                try {
                    mDrawable = getContext().getResources().getDrawable(icon);
                    int mWidth = mCreditSize * mDrawable.getIntrinsicWidth() / mDrawable.getIntrinsicHeight();
                    int mTop = (mCreditTextSize - mCreditSize) / 2;
                    mDrawable.setBounds(0, mTop, mWidth, mTop + mCreditSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return mDrawable;
            }
        };
    }

    /**
     * 设置信誉图标大小
     */
    public void setCreditSize(int pixels) {
        mCreditSize = pixels;
        super.setText(getText());
    }

}
