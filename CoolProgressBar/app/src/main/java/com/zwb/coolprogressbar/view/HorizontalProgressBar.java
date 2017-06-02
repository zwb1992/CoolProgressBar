package com.zwb.coolprogressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.zwb.coolprogressbar.R;

/**
 * Created by zwb
 * Description
 * Date 2017/6/2.
 */

public class HorizontalProgressBar extends ProgressBar {
    private int mReachHeight;
    private int mReachColor;
    private int mUnReachHeight;
    private int mUnReachColor;
    private int mTextColor;
    private int mTextSize;
    private int mOffset;

    private Paint mTextPaint;
    private Paint mReachPaint;
    private Paint mUnReachPaint;

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.HorizontalProgressBar_reach_color:
                    mReachColor = array.getColor(attr, Color.GRAY);
                    break;
                case R.styleable.HorizontalProgressBar_unReach_color:
                    mUnReachColor = array.getColor(attr, Color.GRAY);
                    break;
                case R.styleable.HorizontalProgressBar_progress_text_color:
                    mTextColor = array.getColor(attr, Color.GRAY);
                    break;
                case R.styleable.HorizontalProgressBar_offset:
                    mOffset = (int) array.getDimension(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.HorizontalProgressBar_reach_width:
                    mReachHeight = (int) array.getDimension(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.HorizontalProgressBar_unReach_width:
                    mUnReachHeight = (int) array.getDimension(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.HorizontalProgressBar_progress_text_size:
                    mTextSize = (int) array.getDimension(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        mReachPaint = new Paint();
        mReachPaint.setAntiAlias(true);
        mReachPaint.setDither(true);
        mReachPaint.setStrokeWidth(mReachHeight);
        mReachPaint.setColor(mReachColor);

        mUnReachPaint = new Paint();
        mUnReachPaint.setAntiAlias(true);
        mUnReachPaint.setDither(true);
        mUnReachPaint.setStrokeWidth(mUnReachHeight);
        mUnReachPaint.setColor(mUnReachColor);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = measureHeight(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec 高
     * @return 返回最终的高
     */
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {//确定的值
            result = size;
        } else {//不确定得时候，取已到达的进度，为到达进度以及文字等高度的最大值
            int textHeight = (int) (mTextPaint.descent() - mTextPaint.ascent());
            result = Math.max(Math.max(Math.abs(textHeight), mUnReachHeight), mReachHeight);
        }
        return result;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        int mRealWidth = getMeasuredWidth();
        int mRealHeight = getMeasuredHeight();
        canvas.translate(getPaddingLeft(), mRealHeight / 2);//移动到最左边垂直居中的位置
        int progress = getProgress();
        float ratio = progress * 1.0f / getMax();
        String text = progress + "%";
        int textWidth = (int) mTextPaint.measureText(text);

        int progressX = (int) (ratio * (mRealWidth - mOffset - textWidth));
        if (ratio <= 1.0f) {
            canvas.drawLine(0, 0, progressX, 0, mReachPaint);

            int fy = (int) (-mTextPaint.ascent() - mTextPaint.descent()) / 2;
            canvas.drawText(text, progressX + mOffset / 2, fy, mTextPaint);

            canvas.drawLine(progressX + mOffset + textWidth, 0, mRealWidth, 0, mUnReachPaint);
        }
        canvas.restore();
    }
}
