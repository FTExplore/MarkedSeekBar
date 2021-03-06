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
    private float mProgress = 0; // playing position value
    private float mBufferProgress = 50;// buffering position value
    private float mBufferMinValue = 0.0f;//buffering min value
    private float mBufferMaxValue = 100;// buffering max value
    private float mBufferDelta;// mBufferMaxValue - mBufferMinValue
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
    private int mColorMarker;
    //轨道的厚度
    private float mBackgroundTracSize;
    private float mBufferTrackSize;
    private float mProgressTrackSize;

    //marker
    private float mFilmTitleMarkerPos; // marker postion
    private float mFilmTailMarkerPos; // marker postion

    public interface OnSeekBarChangeListener {
        void onProgressChanged(MarkedSeekBar seekBar, float progress, boolean isFromUser);

        void onStartTrackingTouch(MarkedSeekBar seekBar);

        void onStopTrackingTouch(MarkedSeekBar seekBar);
    }

    private OnSeekBarChangeListener onSeekBarChangeListener;

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
        onSeekBarChangeListener = listener;
    }

    public float getProgress() {
        return mProgress;
    }

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
        mColorMarker = Color.parseColor("#FFB6C1");

        mThumbRadius = dp2px(5);
        mThumbRadiusOnDragging = dp2px(10);
        mBufferTrackSize = dp2px(2);
        mProgressTrackSize = dp2px(2);
        mBackgroundTracSize = dp2px(2);
    }

    public void setFilmTitleMarkerPos(float position) {
        if (position < 0) {
            mFilmTitleMarkerPos = 0;
        } else if (position > mMax) {
            mFilmTitleMarkerPos = mMax;
        } else {
            mFilmTitleMarkerPos = position;
        }

        invalidate();
    }

    public void setFilmTailMarkerPos(float position) {
        if (position < 0) {
            mFilmTailMarkerPos = 0;
        } else if (position > mMax) {
            mFilmTailMarkerPos = mMax;
        } else {
            mFilmTailMarkerPos = position;
        }

        invalidate();
    }

    /**
     * 设置缓冲位置
     */
    public void setBufferProgress(float bufferProgress) {
        if (bufferProgress > mBufferMaxValue) {
            mBufferProgress = mBufferMaxValue;
        } else if (bufferProgress < 0) {
            mBufferProgress = 0f;
        } else {
            mBufferProgress = bufferProgress;
        }

        invalidate();
    }

    /**
     * 设置缓冲最大值
     */
    public void setBufferMaxVaule(float bufferMaxVaule) {
        this.mBufferMaxValue = bufferMaxVaule;
        mBufferDelta = mBufferMaxValue - mBufferMinValue;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mLeft = getPaddingLeft() + mThumbRadiusOnDragging;
        mRight = getMeasuredWidth() - getPaddingRight() - mThumbRadiusOnDragging;

        mTrackLength = mRight - mLeft;
        mDelta = mMax - mMin;

        mBufferDelta = mBufferMaxValue - mBufferMinValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float xLeft = getPaddingLeft();
        float xRight = getMeasuredWidth() - getPaddingRight();
        float yTop = getPaddingTop() + mThumbRadiusOnDragging;
        float xBuffer = (mTrackLength / mBufferDelta) * (mBufferProgress - mBufferMinValue) + mLeft;
        float xFilmTitle = (mTrackLength / mDelta) * (mFilmTitleMarkerPos - mMin) + mLeft;
        float xFilmTail = (mTrackLength / mDelta) * (mFilmTailMarkerPos - mMin) + mLeft;

        if (!isThumbOnDragging) {
            mThumbCenterX = (mTrackLength / mDelta) * (mProgress - mMin) + mLeft;
        }

        //draw background line
        mPaint.setColor(mColorBackgroundLine);
        mPaint.setStrokeWidth(mBufferTrackSize);
        canvas.drawLine(mLeft, yTop, mRight, yTop, mPaint);

        //draw buffing line
        mPaint.setColor(mColorBufferingLine);
        mPaint.setStrokeWidth(mBackgroundTracSize);
        canvas.drawLine(mLeft, yTop, xBuffer, yTop, mPaint);

        //draw the marker
        mPaint.setColor(mColorMarker);
        canvas.drawCircle(xFilmTitle, yTop, mBackgroundTracSize / 2, mPaint);
        canvas.drawCircle(xFilmTail, yTop, mBackgroundTracSize / 2, mPaint);

        //draw progress line
        mPaint.setColor(mColorProgressLine);
        mPaint.setStrokeWidth(mProgressTrackSize);
        canvas.drawLine(mLeft, yTop, mThumbCenterX, yTop, mPaint);

        //draw thumb
        mPaint.setColor(mColorThumb);
        canvas.drawCircle(mThumbCenterX, yTop, isThumbOnDragging ? mThumbRadiusOnDragging : mThumbRadius, mPaint);

    }

    float dx;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                isThumbOnDragging = isThumbTouched(event);

                if (isThumbOnDragging) {

                } else {
                    //没有触碰到thumb区域
                    mThumbCenterX = event.getX();
                    if (mThumbCenterX < mLeft) {
                        mThumbCenterX = mLeft;
                    }
                    if (mThumbCenterX > mRight) {
                        mThumbCenterX = mRight;
                    }
                    updateProgress();
                    invalidate();
                }
            }

            dx = mThumbCenterX - event.getX();

            if (onSeekBarChangeListener != null){
                onSeekBarChangeListener.onStartTrackingTouch(this);
            }

            break;
            case MotionEvent.ACTION_MOVE: {
                if (isThumbOnDragging) {
                    mThumbCenterX = event.getX() + dx;
                    if (mThumbCenterX < mLeft) {
                        mThumbCenterX = mLeft;
                    }
                    if (mThumbCenterX > mRight) {
                        mThumbCenterX = mRight;
                    }
                    updateProgress();
                    invalidate();
                }

                if (onSeekBarChangeListener != null){
                    onSeekBarChangeListener.onProgressChanged(this,mProgress,true);
                }
            }
            break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                isThumbOnDragging = false;
                updateProgress();
                invalidate();

                if (onSeekBarChangeListener != null){
                    onSeekBarChangeListener.onStopTrackingTouch(this);
                }
            }
            break;
            default:
                break;

        }

        //Log.e("ZHZ", "x : " + event.getX() + " y : " + event.getY());

        return true;
    }

    /**
     * 当用户手动或者自动更改了thumb位置
     * 根据thumb的坐标更新mProgress的数值
     */
    private void updateProgress() {
        mProgress = (mThumbCenterX - mLeft) * mDelta / mTrackLength + mMin;
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
