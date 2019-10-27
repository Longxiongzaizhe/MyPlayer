package com.hjl.module_main.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.hjl.commonlib.utils.DensityUtil;
import com.hjl.module_main.R;
import com.hjl.module_main.net.bean.Lyric;
import com.hjl.module_main.net.bean.LyricLine;

import java.util.List;

/**
 * 显示歌词
 * created by long on 2019/10/25
 */
public class LyricView extends View {

    private final String TAG = "LyricView";

    /**
     * 默认宽高 wrap_content 时有效
     */
    private int DEFAULT_WIDTH = 1000;
    private int DEFAULT_HEIGHT = 400;

    private int LINE_SPACE = DensityUtil.dp2px(20);

    /**
     * 宽、高
     */
    private int mWidth;
    private int mHeight;
    private int mTextLineHeight;

    private int mOffsetY;

    /**
     * 画笔
     */

    private Paint mNormalPaint;
    private Paint mCurrentPaint;

    /**
     * 文字
     */

    // 大小
    private int mNormalTextSize = DensityUtil.dp2px(16);
    private int mCurrentTextSize = 16;

    //
    private Rect mTextRect = new Rect();



    /**
     * 数据
     */

    private Lyric lyric;
    private List<LyricLine> lyricLines;
    private int currentLine;

    /**
     * 时间
     */
    private long currentTime = 0L;


    /**
     *
     */
    public LyricView(Context context) {
        super(context);
        init();
    }

    public LyricView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LyricView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        mNormalPaint = new Paint();
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setTextSize(mNormalTextSize);
        mNormalPaint.setColor(getResources().getColor(R.color.common_white));

        mCurrentPaint = new Paint();
        mCurrentPaint.setAntiAlias(true);
        mCurrentPaint.setTextSize(mNormalTextSize);
        mCurrentPaint.setColor(getResources().getColor(R.color.lyric_yellow));

        measureTextHeight();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        }else if (widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(DEFAULT_WIDTH,heightSize);
        }else if (heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,DEFAULT_HEIGHT);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (lyricLines == null || lyricLines.size() == 0 ) return;

        int paddingTop = getPaddingTop();
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int paddingBottom = getPaddingBottom();

        int mDrawY = (mHeight + mTextLineHeight)/2 + paddingTop;

        for (int i = 0;i < lyricLines.size();i++){

            LyricLine line = lyricLines.get(i);
            mNormalPaint.getTextBounds(line.lyric,0,line.lyric.length(),mTextRect);
            int startX = (mWidth - mTextRect.width())/2;

            // 上部分淡出 && 下部分淡出 ; mDrawY - mOffsetY 是实际显示在屏幕上的Y位置
            if (mDrawY - mOffsetY >= mTextLineHeight*2 && mDrawY - mOffsetY <= mHeight - mTextLineHeight*2){
                mNormalPaint.setAlpha(255);
            }else {
                mNormalPaint.setAlpha(100);
            }

            if (i != currentLine){
                canvas.drawText(line.lyric,startX,mDrawY,mNormalPaint);
            }else {
                canvas.drawText(line.lyric,startX,mDrawY,mCurrentPaint);
            }

            Log.d(TAG, "mDrawY: " + mDrawY + " offset : " + mOffsetY);
            Log.d(TAG, "mHeight: " + mHeight);
            mDrawY += mTextLineHeight;
        }

        currentLine = calculateCurrentLine();
        mOffsetY = currentLine * mTextLineHeight;
        scrollTo(0,mOffsetY);
       // Log.d(TAG, "onDraw: currentLine: "+ currentLine + "  currentTime " + currentTime);

       // postInvalidateDelayed(100);

    }

    public void measureTextHeight(){
        Rect textRect = new Rect();
        mNormalPaint.getTextBounds("告白气球",0,"告白气球".length(),textRect);
        mTextLineHeight = textRect.height() + LINE_SPACE;
    }

    public void setLyric(Lyric lyric) {
        this.lyric = lyric;
        lyricLines = lyric.lyricLineList;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
        invalidate();
    }

    public int calculateCurrentLine(){


        for (int i = 0; i < lyricLines.size() ; i++){

            if (currentTime < lyricLines.get(i).startTime){
                return i-1;
            }

            if (i == lyricLines.size()-1) return i;

        }

        return lyricLines.size() - 1;

    }
}
