package com.hjl.module_main.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * created by long on 2019/11/6
 */
public class NoConflictViewPager extends ViewPager {

    private String TAG = "NoConflictViewPager";
    private float startX;
    private float startY;
    private float mTouchSlop;

    public NoConflictViewPager(@NonNull Context context) {
        super(context);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NoConflictViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean isIntercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:

                if (Math.abs(x - startX) > Math.abs( y - startY)){
                    isIntercept = true;
                }else {
                    isIntercept = false;
                }
                break;

        }

        startX = x;
        startY = y;

        Log.d(TAG, "onInterceptTouchEvent: " + isIntercept);
        return isIntercept;
    }

}
