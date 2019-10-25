package com.hjl.module_main.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hjl.module_main.R;
import com.hjl.module_main.net.bean.Lyric;
import com.hjl.module_main.net.bean.LyricLine;

import java.util.List;

/**
 * 显示歌词
 * created by long on 2019/10/25
 */
public class LyricView extends View {

    /**
     * 默认宽高 wrap_content 时有效
     */
    private int DEFAULT_WIDTH = 1000;
    private int DEFAULT_HEIGHT = 400;

    private int LINE_SPACE = 20;

    /**
     * 宽、高
     */
    private int mWidth;
    private int mHeight;
    private int mTextLineHeight;

    /**
     * 画笔
     */

    private Paint mNormalPaint;
    private Paint mCurrentPaint;

    /**
     * 文字
     */

    // 大小
    private int mNormalTextSize = 14;
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
        mCurrentPaint.setColor(getResources().getColor(R.color.common_base_theme_blue));

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


        int lastLine = currentLine;
        currentLine = calculateCurrentLine();
        if (lastLine != currentLine){
            setScrollY(mTextLineHeight);
        }

        int mDrawY = (mHeight + mTextLineHeight)/2 + paddingTop;

        for (int i = 0;i < lyricLines.size();i++){

            LyricLine line = lyricLines.get(i);
            mNormalPaint.getTextBounds(line.lyric,0,line.lyric.length(),mTextRect);
            int startX = (mWidth - mTextRect.width())/2;

            if (i != currentLine){
                canvas.drawText(line.lyric,startX,mDrawY,mNormalPaint);
            }else {
                canvas.drawText(line.lyric,startX,mDrawY,mCurrentPaint);
            }

            mDrawY += mTextLineHeight;
        }

       // postInvalidateDelayed(100);

    }

    public void measureTextHeight(){
        Rect textRect = new Rect();
        mNormalPaint.getTextBounds("告白气球",0,"告白气球".length(),textRect);
        mTextLineHeight = textRect.height();
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

            if (i == 0 && currentTime <= lyricLines.get(0).startTime){
                return 0;
            }

            if (currentTime > lyricLines.get(i).startTime ){
                return i;
            }
        }

        return lyricLines.size() - 1;

    }
}
