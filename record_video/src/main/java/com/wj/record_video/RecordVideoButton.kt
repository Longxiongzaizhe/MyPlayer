package com.wj.record_video

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Description 录制视频的按钮
 * Date 2020/1/9 13:58
 * created by long
 */
class RecordVideoButton(context: Context?, attrs: AttributeSet? = null,defStyleAttr : Int = 0) : View(context, attrs,defStyleAttr) {


    constructor(context: Context?,attrs: AttributeSet?) : this(context,attrs,0)

    private var mWidth  = 0F
    private var mHeight = 0F
    private var mOutsideRadius = 0F
    private var mInsideRadius = 0F
    private var mProgressWidth = 0F

    private var centerX = 0F
    private var centerY = 0F

    private lateinit var mArcRect: RectF

    private var isRecording = false

    private var TAG = "RecordVideoButton"


    private var DEFAULT_LENGTH = resources.getDimension(R.dimen.default_length).toInt()

    private var recordTime = 10 * 1000F
    private var recordTimeCount = 0F

    private var mInsidePaint = Paint()
    private var mOutSidePaint = Paint()
    private var mProgressPaint = Paint()

    private var outSideAnimator: ValueAnimator? = null
    private var inSideAnimator: ValueAnimator? = null
    private var ANIMATOR_TIME = 200L



    init {
        mInsidePaint.color = Color.WHITE
        mOutSidePaint.color = Color.GRAY

        mInsidePaint.isAntiAlias = true
        mOutSidePaint.isAntiAlias = true
        mProgressPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mHeight = measuredHeight.toFloat()
        mWidth = measuredWidth.toFloat()

        centerX = mWidth / 2
        centerY = mHeight / 2

        mProgressWidth = mWidth/16
        mInsideRadius = mWidth/4
        mOutsideRadius = mInsideRadius + mProgressWidth


        mProgressPaint.color = Color.GREEN
        mProgressPaint.style = Paint.Style.STROKE
        mProgressPaint.strokeWidth = mProgressWidth

        mArcRect = RectF(mProgressWidth,mProgressWidth,mWidth - mProgressWidth,mHeight - mProgressWidth)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)


        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(DEFAULT_LENGTH,DEFAULT_LENGTH)
        }else if (widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(DEFAULT_LENGTH,heightSize)
        }else if (heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,DEFAULT_LENGTH)
        }


    }

    fun startRecord(){
        isRecording = true

        outSideAnimator?.cancel()
        inSideAnimator?.cancel()

        inSideAnimator = ValueAnimator.ofFloat(mInsideRadius,mWidth / 8)
        inSideAnimator?.duration = ANIMATOR_TIME
        inSideAnimator?.addUpdateListener {
            mInsideRadius = it.animatedValue as Float
            if (mInsideRadius <= mWidth / 8){
                inSideAnimator = null
            }
            invalidate()
        }

        outSideAnimator = ValueAnimator.ofFloat(mOutsideRadius,(mWidth - mProgressWidth)/2)
        outSideAnimator?.duration = ANIMATOR_TIME
        outSideAnimator?.addUpdateListener {
            mOutsideRadius = it.animatedValue as Float
            if (mOutsideRadius >= (mWidth - mProgressWidth)/2){
                outSideAnimator = null
            }
            invalidate()
        }

        inSideAnimator?.start()
        outSideAnimator?.start()


        postDelayed(mProgressRunnable,ANIMATOR_TIME)
    }

    fun stopRecord(){
        isRecording = false
        recordTimeCount = 0F

        outSideAnimator?.cancel()
        inSideAnimator?.cancel()

        inSideAnimator = ValueAnimator.ofFloat(mInsideRadius,mWidth/4)
        inSideAnimator?.duration = ANIMATOR_TIME
        inSideAnimator?.addUpdateListener {
            mInsideRadius = it.animatedValue as Float
            if (mInsideRadius >= mWidth/4){
                inSideAnimator = null
            }
            invalidate()
        }

        outSideAnimator = ValueAnimator.ofFloat(mOutsideRadius,mWidth/4 + mProgressWidth)
        outSideAnimator?.duration = ANIMATOR_TIME
        outSideAnimator?.addUpdateListener {
            mOutsideRadius = it.animatedValue as Float
            if (mOutsideRadius <= mInsideRadius + mProgressWidth){
                outSideAnimator = null
            }
            invalidate()
        }

        inSideAnimator?.start()
        outSideAnimator?.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawCircle(centerX,centerY,mOutsideRadius,mOutSidePaint)
        canvas?.drawCircle(centerX,centerY,mInsideRadius,mInsidePaint)

        drawArc(canvas)
    }

    private fun drawArc(canvas: Canvas?){
        if (!isRecording) return

        val sweepAngle = (recordTimeCount / recordTime) * 360

        canvas?.drawArc(mArcRect,-90F, sweepAngle,false,mProgressPaint)

    }

    private var mProgressRunnable = object : Runnable{
        override fun run() {
            recordTimeCount += 100
            if (recordTimeCount <= recordTime && isRecording){
                postDelayed(this, 100)
            }else{
                stopRecord()
            }

            postInvalidate()

            Log.d(TAG,recordTimeCount.toString())
        }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        outSideAnimator?.cancel()
        inSideAnimator?.cancel()

    }
}