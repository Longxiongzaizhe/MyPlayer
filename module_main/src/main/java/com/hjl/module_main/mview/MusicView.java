package com.hjl.module_main.mview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.hjl.commonlib.utils.BitmapUtils;
import com.hjl.commonlib.utils.DensityUtil;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.module_main.R;

import java.util.concurrent.ExecutionException;

public class MusicView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static final long TIME_UPDATE = 50L;
    private static final float DISC_ROTATION_INCREASE = 0.5f;
    private static final float NEEDLE_ROTATION_PLAY = 0.0f;
    private static final float NEEDLE_ROTATION_PAUSE = -25.0f;
    private Handler mHandler = new Handler();

    private Bitmap mDiscBm;    // 黑胶唱片的黑胶图片
    private Bitmap mNeedleBm; // 针的图片
    private Bitmap mCoverBm;  // cover 图片
    private String mCoverUrl; // cover 地址

    private int mCoverBorderWidth;
    private Drawable mCoverBorder;

    private ValueAnimator mNeedlePlayAnimator;
    private ValueAnimator mNeedlePauseAnimator;

    // 设置Bitmap 旋转的中心等
    private Matrix mNeedleMatrix = new Matrix();
    private Matrix mDiscMatrix = new Matrix();
    private Matrix mCoverMatrix = new Matrix();

    // 图片起始坐标
    private Point mDiscPoint = new Point();
    private Point mCoverPoint = new Point();
    private Point mNeedlePoint = new Point();
    // 旋转中心坐标
    private Point mDiscCenterPoint = new Point();
    private Point mCoverCenterPoint = new Point();
    private Point mNeedleCenterPoint = new Point();

    private float mDiscRotation = 0.0f;
    private float mNeedleRotation = NEEDLE_ROTATION_PLAY;
    private boolean isPlaying = false;


    public MusicView(Context context) {
        super(context);
    }

    public MusicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        try {
            init();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        initNeedle(false);
        initAnimator();
    }

    public MusicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() throws ExecutionException, InterruptedException {
        mCoverBorderWidth = DensityUtil.dp2px(1);
        mCoverBorder = getResources().getDrawable(R.drawable.play_page_cover_border_shape);
        mDiscBm = BitmapFactory.decodeResource(getResources(), R.drawable.play_page_disc);
        mNeedleBm = BitmapFactory.decodeResource(getResources(),R.drawable.play_page_needle);
        if (!StringUtils.isEmpty(mCoverUrl)){
            mCoverBm = Glide.with(this).asBitmap().centerCrop().load(mCoverUrl).submit(500,500).get();
        }else {
            mCoverBm = BitmapFactory.decodeResource(getResources(),R.drawable.szu);
        }
    }

    private void initAnimator(){

        mNeedlePauseAnimator = ValueAnimator.ofFloat(NEEDLE_ROTATION_PLAY,NEEDLE_ROTATION_PAUSE);
        mNeedlePlayAnimator = ValueAnimator.ofFloat(NEEDLE_ROTATION_PAUSE,NEEDLE_ROTATION_PLAY);
        mNeedlePauseAnimator.setDuration(300);
        mNeedlePlayAnimator.setDuration(300);
        mNeedlePauseAnimator.addUpdateListener(this);
        mNeedlePlayAnimator.addUpdateListener(this);
    }

    public void setmCoverUrl(String mCoverUrl) {
        this.mCoverUrl = mCoverUrl;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 2.绘制黑胶唱片外侧半透明边框
        mCoverBorder.setBounds(mDiscPoint.x - mCoverBorderWidth, mDiscPoint.y - mCoverBorderWidth,
                mDiscPoint.x + mDiscBm.getWidth() + mCoverBorderWidth, mDiscPoint.y + mDiscBm.getHeight() + mCoverBorderWidth);
        mCoverBorder.draw(canvas);
        // 3.绘制黑胶
        // 设置旋转中心和旋转角度，setRotate和preTranslate顺序很重要
        mDiscMatrix.setRotate(mDiscRotation, mDiscCenterPoint.x, mDiscCenterPoint.y);
        // 设置图片起始坐标
        mDiscMatrix.preTranslate(mDiscPoint.x, mDiscPoint.y);
        canvas.drawBitmap(mDiscBm, mDiscMatrix, null);
        // 4.绘制封面
        mCoverMatrix.setRotate(mDiscRotation, mCoverCenterPoint.x, mCoverCenterPoint.y);
        mCoverMatrix.preTranslate(mCoverPoint.x, mCoverPoint.y);
        canvas.drawBitmap(mCoverBm, mCoverMatrix, null);
        // 5.绘制指针
        mNeedleMatrix.setRotate(mNeedleRotation, mNeedleCenterPoint.x, mNeedleCenterPoint.y);
        mNeedleMatrix.preTranslate(mNeedlePoint.x, mNeedlePoint.y);
        canvas.drawBitmap(mNeedleBm, mNeedleMatrix, null);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            initOnLayout();
        }
    }

    private void initOnLayout() {
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        int unit = Math.min(getWidth(), getHeight()) / 8;
        //  CoverLoader.get().setRoundLength(unit * 4);

        mDiscBm = BitmapUtils.resizeImage(mDiscBm, unit * 6, unit * 6);
        mCoverBm = BitmapUtils.resizeImage(mCoverBm, unit * 4, unit * 4);
        mNeedleBm = BitmapUtils.resizeImage(mNeedleBm, unit * 2, unit * 3);

        int discOffsetY = mNeedleBm.getHeight() / 2;
        mDiscPoint.x = (getWidth() - mDiscBm.getWidth()) / 2;
        mDiscPoint.y = discOffsetY;
        mCoverPoint.x = (getWidth() - mCoverBm.getWidth()) / 2;
        mCoverPoint.y = discOffsetY + (mDiscBm.getHeight() - mCoverBm.getHeight()) / 2;
        mNeedlePoint.x = getWidth() / 2 - mNeedleBm.getWidth() / 6;
        mNeedlePoint.y = 0;

        mDiscCenterPoint.x = getWidth() / 2;
        mDiscCenterPoint.y = mDiscBm.getHeight() / 2 + discOffsetY;
        mCoverCenterPoint.x = mDiscCenterPoint.x;
        mCoverCenterPoint.y = mDiscCenterPoint.y;
        mNeedleCenterPoint.x = mDiscCenterPoint.x;
        mNeedleCenterPoint.y = 0;
    }

    public void initNeedle(boolean isPlaying) {
        mNeedleRotation = isPlaying ? NEEDLE_ROTATION_PLAY : NEEDLE_ROTATION_PAUSE;
        invalidate();
    }

    public void start() {
        if (isPlaying) {
            return;
        }
        isPlaying = true;
        mHandler.post(mRotationRunnable);
        mNeedlePlayAnimator.start();
    }

    public void pause() {
        if (!isPlaying) {
            return;
        }
        isPlaying = false;
        mHandler.removeCallbacks(mRotationRunnable);
        mNeedlePauseAnimator.start();
    }

    private Runnable mRotationRunnable = new Runnable() {
        @Override
        public void run() {
            if (isPlaying) {
                mDiscRotation += DISC_ROTATION_INCREASE;
                if (mDiscRotation >= 360) {
                    mDiscRotation = 0;
                }
                invalidate();
            }
            mHandler.postDelayed(this, TIME_UPDATE);
        }
    };



    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mNeedleRotation = (float) animation.getAnimatedValue();
        invalidate();
    }
}
