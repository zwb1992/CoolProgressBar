package com.zwb.coolprogressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zwb.coolprogressbar.R;

/**
 * Created by zwb
 * Description
 * Date 2017/6/24.
 */

public class LinearProgressBar extends View {
    private int mBarWidth = 50;//进度条的宽度
    private int mOffsetX = -mBarWidth;//水平偏移量，通过它来不断移动位置,默认偏移出屏幕外
    private int mBarColor;//进度条的颜色
    private Paint paint;
    private static Handler handler;

    public LinearProgressBar(Context context) {
        this(context, null);
    }

    public LinearProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LinearProgressBar, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.LinearProgressBar_bar_width:
                    mBarWidth = (int) array.getDimension(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mBarWidth, getResources().getDisplayMetrics()));
                    mOffsetX = -mBarWidth;
                    break;
                case R.styleable.LinearProgressBar_bar_color:
                    mBarColor = array.getColor(attr, Color.BLUE);
                    break;
            }
        }
        array.recycle();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(mBarColor);
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(0, getMeasuredHeight() / 2.0f);
        paint.setStrokeWidth(getMeasuredHeight()-2);
        canvas.drawLine(mOffsetX, 0, mOffsetX + mBarWidth, 0, paint);
        canvas.restore();
    }

    private Runnable barRunnable = new Runnable() {
        @Override
        public void run() {
            mOffsetX += 20;
            if (mOffsetX > getMeasuredWidth()) {
                mOffsetX = -mBarWidth;
            }
            invalidate();
            handler.postDelayed(this, 100);
        }
    };

    public void start() {
        handler.removeCallbacks(barRunnable);
        handler.post(barRunnable);
    }

    public void stop() {
        handler.removeCallbacks(barRunnable);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(barRunnable);
        handler = null;
    }
}
