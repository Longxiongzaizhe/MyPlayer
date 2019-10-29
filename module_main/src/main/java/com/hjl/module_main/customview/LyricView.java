package com.hjl.module_main.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.hjl.commonlib.utils.DateUtils;
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
    private int INDICATOR_PADDING = DensityUtil.dp2px(15);

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
     * 滑动相关
     */

    private Scroller mScroller;
    private boolean mIsDraging; // 判断是否拖动
    private boolean mIsShowIndicator;//


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
     * 指示器相关
     */
    private long indicatorTime = 0L; // 指示器滑动的到所在行的时间
    private int mIndicatorTextSize = DensityUtil.dp2px(12);
    private Paint mIndicatorPaint;
    private int mIndicatorHeight;
    private int mIndicatorTextWidth;
    private Rect mPlayBtnBound;


    private PlayBtnClickListener playBtnClickListener;



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

        mIndicatorPaint = new Paint();
        mIndicatorPaint.setAntiAlias(true);
        mIndicatorPaint.setColor(getResources().getColor(R.color.common_white));
        mIndicatorPaint.setAlpha(100);
        mIndicatorPaint.setTextSize(mIndicatorTextSize);


        measureTextHeight();
        computeIndicatorData();

        mScroller = new Scroller(getContext(),new LinearInterpolator());

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

            // 获取歌词宽度 画在中间
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

//            Log.d(TAG, "mDrawY: " + mDrawY + " offset : " + mOffsetY);
//            Log.d(TAG, "mHeight: " + mHeight);
            mDrawY += mTextLineHeight;
        }
        mOffsetY = currentLine * mTextLineHeight;

        if (!mIsDraging){ // 拖动的时候不滚动
            int lastScrollY = getScrollY();  // 上次滑动的距离
            currentLine = calculateCurrentLine(); // 获取当前行数
            mScroller.startScroll(0,lastScrollY,0,currentLine * mTextLineHeight - lastScrollY);
        }

        if (mIsDraging){
            drawIndicator(canvas);
        }

//        scrollTo(0,mOffsetY);
       // Log.d(TAG, "onDraw: currentLine: "+ currentLine + "  currentTime " + currentTime);

       // postInvalidateDelayed(100);

    }

    public void drawIndicator(Canvas canvas){

        float showingLineCenter = getShowingCenterY() + mTextLineHeight-LINE_SPACE;

        // 绘制时间
        mIndicatorPaint.setStyle(Paint.Style.FILL);
        mIndicatorPaint.setStrokeWidth(3);
        mIndicatorPaint.setPathEffect(null);

        canvas.drawText(DateUtils.getMusicTime((int) indicatorTime),DensityUtil.dp2px(5),showingLineCenter + mIndicatorHeight/2,mIndicatorPaint);

        // 绘制播放按钮
        Path playBtnPath = new Path();


        mPlayBtnBound = new Rect((int) (mWidth - DensityUtil.dp2px(15) - DensityUtil.dp2px(20)),
                (int)(showingLineCenter - DensityUtil.dp2px(10)),
                (int) (mWidth - DensityUtil.dp2px(15)),
                (int)(showingLineCenter + DensityUtil.dp2px(10)));
        float btnWidth = mPlayBtnBound.width() * 0.5f;
        float btnHegiht = mPlayBtnBound.height() * 0.7f;

        playBtnPath.moveTo(mPlayBtnBound.centerX() - btnWidth/3,mPlayBtnBound.top + (mPlayBtnBound.height() - btnHegiht)/2);
        playBtnPath.lineTo(mPlayBtnBound.centerX() - btnWidth/3,mPlayBtnBound.bottom - (mPlayBtnBound.height() - btnHegiht)/2);
        playBtnPath.lineTo(mPlayBtnBound.centerX() + btnWidth*2/3,mPlayBtnBound.centerY());
        playBtnPath.lineTo(mPlayBtnBound.centerX() - btnWidth/3,mPlayBtnBound.top + (mPlayBtnBound.height() - btnHegiht)/2);

        mIndicatorPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(playBtnPath,mIndicatorPaint);
        mIndicatorPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mPlayBtnBound.centerX(),mPlayBtnBound.centerY(),mPlayBtnBound.height()/2,mIndicatorPaint);


        // 绘制虚线
        mIndicatorPaint.setStyle(Paint.Style.STROKE);
        mIndicatorPaint.setStrokeWidth(3);
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{25,5},0); // 设置虚线效果
        mIndicatorPaint.setPathEffect(dashPathEffect);
        Path path = new Path();
        path.moveTo(DensityUtil.dp2px(15) + mIndicatorTextWidth, showingLineCenter);
        path.lineTo(mWidth - DensityUtil.dp2px(15) - mIndicatorTextWidth,showingLineCenter);

        canvas.drawPath(path,mIndicatorPaint);

        Log.d(TAG, "drawIndicator: getScrollY" + getScrollY());
    }

    /**
     * 实时View的中点Y值
     * @return
     */
    public float getShowingCenterY(){
        return getScrollY() + mHeight/2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                mIsDraging = true;
                return true;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsDraging = false;
                break;
        }


        return super.onTouchEvent(event);


    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mScroller.computeScrollOffset()){ // 判断是否滑动完成
            scrollTo(0,mScroller.getCurrY()); // 滑动到当前位置
            invalidate();
        }

    }

    /**
     * 测量每一行的高度
     */
    public void measureTextHeight(){
        Rect textRect = new Rect();
        mNormalPaint.getTextBounds("告白气球",0,"告白气球".length(),textRect);
        mTextLineHeight = textRect.height() + LINE_SPACE;
    }

    /**
     * 测量指示器显示高度
     */
    public void computeIndicatorData(){
        Rect indicatorRect = new Rect();
        mIndicatorPaint.getTextBounds("00:00",0,"00:00".length(),indicatorRect);

        mIndicatorHeight = indicatorRect.height();
        mIndicatorTextWidth = indicatorRect.width();

    }

    /**
     * 设置歌词对象
     */
    public void setLyric(Lyric lyric) {
        this.lyric = lyric;
        lyricLines = lyric.lyricLineList;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
        indicatorTime = currentTime;
        invalidate();
    }

    /**
     * 计算当前行数
     */
    public int calculateCurrentLine(){

        for (int i = 0; i < lyricLines.size() ; i++){

            if (currentTime < lyricLines.get(i).startTime){
                return i-1;
            }

            if (i == lyricLines.size()-1) return i;

        }

        return lyricLines.size() - 1;
    }


    public void computeIsClickPlayBtn(int x,int y){

        if (mPlayBtnBound == null) return;

        if ( playBtnClickListener != null && x >= mPlayBtnBound.left && x <= mPlayBtnBound.right &&
                    y >= mPlayBtnBound.top && y <= mPlayBtnBound.bottom ){
            playBtnClickListener.onPlayBtnClick(indicatorTime);
        }


    }

    public void setPlayBtnClickListener(PlayBtnClickListener playBtnClickListener) {
        this.playBtnClickListener = playBtnClickListener;
    }

    interface PlayBtnClickListener{
        void onPlayBtnClick(long lineTime);
    }
}
