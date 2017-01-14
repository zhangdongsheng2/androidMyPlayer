package com.example.myplayer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.SeekBar;

import com.example.myplayer.R;


public class UiSeeKBar extends SeekBar {
    //构建画笔和文字
    Paint bmPaint;
    //进度条指示文字后缀
    private String numTextFormat = "%";
    private String numText;
    //进度条指示文字的大小吗默认20px
    private int numTextSize = 20;
    //字体指示颜色
    private int numTextColor;
    //文本的宽可能不准
    private float numTextWidth;
    //测量seekbar的规格
    private Rect rect_seek;
    //测量thum的规格
    private Rect rect_thum;

    //show 在top还是bottom
    private int type = Gravity.TOP;
    private Paint.FontMetrics fm;
    //特别说明这个scale比例是滑动的指示器小箭头部分占全部图片的比列，为了使其文字完全居中
    private double numScale = 0.16;

    private int iconLeft;
    private int iconRight;
    private int leftWidth;
    private int rightHeight;
    private Bitmap leftBtm;
    private Bitmap rightBtm;

    public UiSeeKBar(Context context) {
        this(context, null);
    }

    public UiSeeKBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public UiSeeKBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化属性
        init(context, attrs);
        //构建画笔
        initPaint();
        //设置预留显示
        initBm();
    }

    private void initBm() {
        leftBtm = BitmapFactory.decodeResource(getResources(), iconLeft);
        rightBtm = BitmapFactory.decodeResource(getResources(), iconRight);
        leftWidth = leftBtm.getWidth();
        rightHeight = leftBtm.getHeight();
        setPadding(getPaddingLeft(), getPaddingTop() + rightHeight / 2, getPaddingRight(), getPaddingBottom() + 200);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomSeekBar);
        numTextFormat = array.getString(R.styleable.CustomSeekBar_numTextFormat);
        numTextSize = array.getDimensionPixelSize(R.styleable.CustomSeekBar_numTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        numTextColor = array.getColor(R.styleable.CustomSeekBar_numTextColor, Color.WHITE);
        iconLeft = array.getResourceId(R.styleable.CustomSeekBar_ico_left, 0);
        iconRight = array.getResourceId(R.styleable.CustomSeekBar_ico_right, 0);
        numScale = Double.parseDouble(array.getString(R.styleable.CustomSeekBar_numScale) == null ? numScale + "" : array.getString(R.styleable.CustomSeekBar_numScale));
        numTextFormat = numTextFormat == null ? "%" : numTextFormat;

        array.recycle();
    }

    private void initPaint() {
        //抗锯齿
        bmPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bmPaint.setTypeface(Typeface.DEFAULT);
        bmPaint.setTextSize(numTextSize);
        bmPaint.setColor(numTextColor);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            fm = bmPaint.getFontMetrics();
            numText = (getProgress() * 100 / getMax()) + numTextFormat;
            numTextWidth = bmPaint.measureText(numText);

            rect_seek = this.getProgressDrawable().getBounds();
            float thum_height = 0;
            float thum_width = 0;
            //api必须大于16
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                rect_thum = this.getThumb().getBounds();
                thum_height = rect_thum.height();
                thum_width = rect_thum.width();
            }
            //计算文字 绘制文字
            float text_x = rect_seek.width() * getProgress() / getMax();
            canvas.drawText(numText, text_x + thum_width / 2, getPaddingTop() + (thum_height + 50), bmPaint);//文字绘制的位置  这里的 50 如果不合适可以在调整 这里临时写 50


            canvas.drawBitmap(leftBtm, rect_seek.width() + thum_width, rect_seek.height() / 2, bmPaint);
            canvas.drawBitmap(rightBtm, 0 + thum_width / 2, rect_seek.height() / 2, bmPaint);

        } catch (Exception e) {
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        return super.onTouchEvent(event);
    }


    public String getNumText() {
        return numText;
    }

    public void setNumText(String numText) {
        this.numText = numText;
        invalidate();
    }

    public int getNumTextSize() {
        return numTextSize;
    }

    public void setNumTextSize(int numTextSize) {
        this.numTextSize = numTextSize;

    }

    public int getNumTextColor() {
        return numTextColor;
    }

    public void setNumTextColor(int numTextColor) {
        this.numTextColor = numTextColor;
    }
}
