package com.example.myplayer.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.bean.Lyric;

import java.util.ArrayList;

public class LyricView extends TextView {
    private final int COLOR_DEFAULT = Color.WHITE;//普通歌词颜色
    private final int COLOR_LIGHT = Color.GREEN;//高亮歌词绿色
    private int LYRIC_SIZE_DEFAULT;//普通歌词的size
    private int LYRIC_SIZE_LIGHT;//高亮歌词的size
    private int LYRIC_ROW_HEIGHT;//每一行歌词的高度，要比歌词本身大一点，才会有空隙

    private int width, height;
    private ArrayList<Lyric> lyricList;

    private int lightLyricIndex;//代表高亮行歌词的索引
    private long currentPosition;//当前歌曲播放的位置
    private long totalDuration;//歌曲的总时间
    private boolean hasNoLyric = false;//是否没有歌词

    private Paint paint;

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LyricView(Context context) {
        super(context);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        LYRIC_ROW_HEIGHT = (int) getResources().getDimension(R.dimen.lyric_row_height);
        LYRIC_SIZE_DEFAULT = (int) getResources().getDimension(R.dimen.lyric_size_default);
        LYRIC_SIZE_LIGHT = (int) getResources().getDimension(R.dimen.lyric_size_light);

        paint = new Paint();
        paint.setColor(COLOR_LIGHT);
        paint.setTextSize(LYRIC_SIZE_DEFAULT);
        paint.setAntiAlias(true);

        //vitural data
//		lyricList = new ArrayList<Lyric>();
//		for (int i = 0; i < 50; i++) {
//			lyricList.add(new Lyric(i*2000, "我是歌词，哒哒哒 - "+i));
//		}
    }

    public void setLyricList(ArrayList<Lyric> list) {
        this.lyricList = list;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lyricList == null || lyricList.size() == 0) {
            String text = hasNoLyric ? "暂未找到歌词" : "正在加载歌词";
            float y = height / 2 + getTextHeight(text) / 2;
            drawCenterHorizontalText(canvas, text, y, true);
        } else {
            drawLyricList(canvas);
        }
    }

    /**
     * 绘制歌词列表
     *
     * @param canvas
     */
    private void drawLyricList(Canvas canvas) {
        Lyric lightLyric = lyricList.get(lightLyricIndex);
        //平滑滚动歌词,逻辑：在歌词的歌唱时间内滚动一个行高的距离
        //1.计算出高亮行歌词的歌唱时间，即高亮时间（下一行的startPoint-我的startPoint)
        int lightTime;
        if (lightLyricIndex == (lyricList.size() - 1)) {
            //如果是最后一行，则是总时间减去startPoint
            lightTime = (int) (totalDuration - lightLyric.getStartPoint());
        } else {
            lightTime = (int) (lyricList.get(lightLyricIndex + 1).getStartPoint() - lightLyric.getStartPoint());
        }
        //2.根据当前已经唱了多少时间，算出应该移动的距离
        float offsetPosition = (currentPosition - lightLyric.getStartPoint());
        float percent = offsetPosition / lightTime;//算出已经唱的占高亮时间的百分比
        float dy = percent * LYRIC_ROW_HEIGHT;

        canvas.translate(0, -dy);

        //1.先绘制高亮行歌词，得到高亮行歌词的y坐标
        float lightLyricY = height / 2 + getTextHeight(lightLyric.getContent()) / 2;
        drawCenterHorizontalText(canvas, lightLyric.getContent(), lightLyricY, true);
        //2.再绘制高亮行之前的歌词，就是根据y坐标向前偏移
        for (int i = 0; i < lightLyricIndex; i++) {
            Lyric lyric = lyricList.get(i);
            float y = lightLyricY - (lightLyricIndex - i) * LYRIC_ROW_HEIGHT;
            drawCenterHorizontalText(canvas, lyric.getContent(), y, false);
        }
        //3.在绘制高亮行之后的歌词，也是根据y向后偏移
        for (int i = lightLyricIndex + 1; i < lyricList.size(); i++) {
            Lyric lyric = lyricList.get(i);
            float y = lightLyricY + (i - lightLyricIndex) * LYRIC_ROW_HEIGHT;
            drawCenterHorizontalText(canvas, lyric.getContent(), y, false);
        }
    }

    /**
     * 获取文本高度
     *
     * @param text
     * @return
     */
    private int getTextHeight(String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    /**
     * 绘制在view中水平居中的文本
     *
     * @param canvas
     * @param text
     * @param y
     */
    private void drawCenterHorizontalText(Canvas canvas, String text, float y, boolean isLight) {
        paint.setColor(isLight ? COLOR_LIGHT : COLOR_DEFAULT);
        paint.setTextSize(isLight ? LYRIC_SIZE_LIGHT : LYRIC_SIZE_DEFAULT);
        float x = width / 2 - paint.measureText(text) / 2;
        canvas.drawText(text, x, y, paint);
    }

    /**
     * 动态滚动歌词
     */
    public void roll(long currentPosition, long totalDuration) {
        this.currentPosition = currentPosition;
        this.totalDuration = totalDuration;


        //1.根据歌曲播放的position，去计算lightLyricIndex
        if (lyricList == null || lyricList.size() == 0) {
            hasNoLyric = true;
        } else {
            calculateLightLyricIndex();
        }
        //2.重新绘制
        invalidate();
    }

    /**
     * 计算高亮行歌词的索引
     * 逻辑：如果currentPosition>=我的startPoint,并且小于我下一行歌词的startPoint,
     * 那我的索引就是高亮行索引
     */
    private void calculateLightLyricIndex() {
        for (int i = 0; i < lyricList.size(); i++) {
            long startPoint = lyricList.get(i).getStartPoint();
            if (i == (lyricList.size() - 1)) {//如果是最后一行
                //只需要判断currentPosition否是大于我的startPoint
                if (currentPosition >= startPoint) {
                    lightLyricIndex = i;
                }
            } else {
                //获取下一行歌词的startPoint
                long newStartPoint = lyricList.get(i + 1).getStartPoint();
                if (currentPosition >= startPoint && currentPosition < newStartPoint) {
                    lightLyricIndex = i;
                }
            }
        }
    }
}
