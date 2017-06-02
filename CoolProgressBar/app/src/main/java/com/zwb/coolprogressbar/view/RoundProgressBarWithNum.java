package com.zwb.coolprogressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.zwb.coolprogressbar.R;

/**
 * Created by zwb
 * Description
 * Date 2017/6/2.
 */

public class RoundProgressBarWithNum extends HorizontalProgressBar {
    private int mRadius;
    private int mMaxPaintWidth;

    public RoundProgressBarWithNum(Context context) {
        this(context, null);
    }

    public RoundProgressBarWithNum(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBarWithNum(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundProgressBarWithNum, defStyleAttr, 0);
        mRadius = (int) array.getDimension(R.styleable.RoundProgressBarWithNum_round_radius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
        array.recycle();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMaxPaintWidth = Math.max(mReachHeight, mUnReachHeight);
        int total = mRadius * 2 + getPaddingLeft() + getPaddingLeft() + mMaxPaintWidth;

        int width = resolveSize(total, widthMeasureSpec);
        int height = resolveSize(total, heightMeasureSpec);

        int realWidth = Math.min(width, height);
        mRadius = (realWidth - mMaxPaintWidth - getPaddingLeft() - getPaddingRight()) / 2;

        setMeasuredDimension(realWidth, realWidth);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        float ratio = getProgress() * 1.0f / getMax();
        String text = getProgress() + "%";
        int textWidth = (int) mTextPaint.measureText(text);
        mReachPaint.setStyle(Paint.Style.STROKE);
        mUnReachPaint.setStyle(Paint.Style.STROKE);

        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop() + mMaxPaintWidth / 2);
        canvas.drawCircle(mRadius, mRadius, mRadius, mUnReachPaint);

        float sweep = 360 * ratio;
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), 0, sweep, false, mReachPaint);

        int fy = (int) (-mTextPaint.ascent() - mTextPaint.descent()) / 2;
        canvas.drawText(text, mRadius - textWidth / 2, mRadius + fy, mTextPaint);
        canvas.restore();
    }
}
