package com.hjl.module_main.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DebugUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

import com.hjl.commonlib.utils.DensityUtil;

/**
 * created by long on 2019/11/6
 */
public class NoConflictScrollView extends ScrollView {

    private String TAG = "NoConflictScrollView";
    private float startX;
    private float startY;
    private float mTouchSlop;

    public NoConflictScrollView(Context context) {
        super(context);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NoConflictScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NoConflictScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean isIntercept = false;


        Log.d(TAG, "onInterceptTouchEvent: " +  ev.getAction());
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                float distanceX = Math.abs(ev.getX() - startX);
                float distanceY = Math.abs(ev.getY() - startY);
                if(distanceX > mTouchSlop && distanceX > distanceY){  //判断为横向滑动
                    return false;
                }


                break;

        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
