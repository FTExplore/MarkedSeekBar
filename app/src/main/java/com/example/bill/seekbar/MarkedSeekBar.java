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
    private float mMin = 0f; // min
    private float mMax = 100f; // max
    private float mProgress; // real time value
    private float mLeft; // space between left of track and left of the view
    private float mRight; // space between right of track and left of the view
    private boolean isThumbOnDragging = false; // is thumb on dragging or not
    //thumb
    private float mThumbCenterX; // X coordinate of thumb's center
    private int mThumbRadius; // radius of thumb
    private int mThumbRadiusOnDragging; // radius of thumb when be dragging
    //Color
    private int mColorBackgroundLine;
    private int mColorBufferingLine;
    private int mColorProgressLine;
    private int mColorThumb;
    //轨道的厚度
    private float mSecondTrackSize;

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
        mColorBackgroundLine = Color.parseColor("#4dffffff");
        //mColorBackgroundLine = Color.parseColor("#FF0000");
        mColorBufferingLine = Color.parseColor("#99ffffff");
        mColorProgressLine = Color.parseColor("#FFE61616");
        mColorThumb = Color.parseColor("#FFE61616");

        mThumbRadius = dp2px(5);
        mThumbRadiusOnDragging = dp2px(10);
        mSecondTrackSize = dp2px(2);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mLeft = getPaddingLeft() + mThumbRadiusOnDragging;
        mRight = getMeasuredWidth() - getPaddingRight() - mThumbRadiusOnDragging;

        mTrackLength = mRight - mLeft;
        mDelta = mMax - mMin;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float xLeft = getPaddingLeft();
        float xRight = getMeasuredWidth() - getPaddingRight();
        float yTop = getPaddingTop() + mThumbRadiusOnDragging;


        if (!isThumbOnDragging) {
            mThumbCenterX = mTrackLength / mDelta * (mProgress - mMin) + xLeft;
        }

        //draw background line
        mPaint.setColor(mColorBackgroundLine);
        mPaint.setStrokeWidth(mSecondTrackSize);
        canvas.drawLine(xLeft, yTop, xRight, yTop, mPaint);

        //draw buffing line

        //draw the marker
        mPaint.setColor(mColorThumb);
        canvas.drawCircle(mThumbCenterX, getMeasuredHeight() / 2, mThumbRadius, mPaint);

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
