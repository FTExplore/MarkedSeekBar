package com.example.bill.seekbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by FTExplore on 2017/8/16.
 */

public class MarkedSeekBar extends View {

    private Paint mPaint;
    private int mHeight;
    private float mTrackLength; // pixel length of whole track
    private float mDelta; // max - min
    private float mMin; // min
    private float mMax; // max
    private float mProgress; // real time value
    private float mLeft; // space between left of track and left of the view
    private float mRight; // space between right of track and left of the view
    private boolean isThumbOnDragging; // is thumb on dragging or not

    public MarkedSeekBar(Context context) {
        super(context);
        init(context, null);
    }

    public MarkedSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public MarkedSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#d7c60e"));
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw background line

        //draw buffing line

        //draw the marker
        canvas.drawCircle(250, 50, 10, mPaint);

        //draw progress line

        //draw thumb


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                isThumbOnDragging = isThumbTouched(event);
            }
            break;
            case MotionEvent.ACTION_MOVE: {

            }
            break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {

            }
            break;
            default:
                break;

        }


        Log.e("ZHZ", "x : " + event.getX() + " y : " + event.getY());

        return super.onTouchEvent(event);
    }


    /**
     * Detect effective touch of thumb
     */
    private boolean isThumbTouched(MotionEvent event) {
        if (!isEnabled())
            return false;
        //
        float x = mTrackLength / mDelta * (mProgress - mMin) + mLeft;
        float y = getMeasuredHeight() / 2f;
        return (event.getX() - x) * (event.getX() - x) + (event.getY() - y) * (event.getY() - y)
                <= (mLeft + dp2px(8)) * (mLeft + dp2px(8));
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }
}
