package com.github.ShinChven.ArcProgressBar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ShinChven on 16/3/3.
 */
public class ArcProgressBar extends View {
    private int mArcProgressBarColor;
    private int mArcProgressBarBackgroundColor;
    private int mArcProgressBarDottedLineColor;
    private int mArcProgressBarMaxProgress = 100;
    private int mArcProgressBarProgress = 0;
    private int mArcProgressBarAngle = 45;
    private int mArcProgressBarLineWidth = 20;
    private int radius;
    private int innerRadius;
    private int mArcProgressBarDottedSize = 5;
    private int mStartAngle;
    private int mX;
    private int mY;
    private int mArcProgressBarInnerRingSize = 20;
    private int mInnerRadius;

    public ArcProgressBar(Context context) {
        super(context);
        init();
    }

    public ArcProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    private void init() {


    }

    public ArcProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    protected void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ArcProgressBar);

        try {
            this.mArcProgressBarColor = ta.getColor(R.styleable.ArcProgressBar_arcProgressBarColor, context.getResources().getColor(R.color.arc_progress_bar_default));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.mArcProgressBarBackgroundColor = ta.getColor(R.styleable.ArcProgressBar_arcProgressBarBackgroundColor, context.getResources().getColor(R.color.arc_progress_bar_default_background));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.mArcProgressBarDottedLineColor = ta.getColor(R.styleable.ArcProgressBar_arcProgressBarDottedLineColor, context.getResources().getColor(R.color.arc_progress_bar_default));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


        try {
            this.mArcProgressBarMaxProgress = ta.getInteger(R.styleable.ArcProgressBar_arcProgressBarMaxProgress, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.mArcProgressBarProgress = ta.getInteger(R.styleable.ArcProgressBar_arcProgressBarProgress, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.mArcProgressBarAngle = ta.getInteger(R.styleable.ArcProgressBar_arcProgressBarAngle, 45);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.mArcProgressBarLineWidth = ta.getDimensionPixelSize(R.styleable.ArcProgressBar_arcProgressBarLineWidth, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.mArcProgressBarDottedSize = ta.getDimensionPixelSize(R.styleable.ArcProgressBar_arcProgressBarDottedSize, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            this.mArcProgressBarInnerRingSize = ta.getDimensionPixelSize(R.styleable
                    .ArcProgressBar_arcProgressBarInnerRingSize, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mStartAngle = 90 + (360 - mArcProgressBarAngle) / 2;

        // 获取控件高宽，以居中
        mX = getWidth();
        mY = getHeight();

        // 设置半径
        if (mX > mY) {
            radius = (int) ((mY / 2) - mArcProgressBarLineWidth / 2);
        } else {
            radius = (int) ((mX / 2) - mArcProgressBarLineWidth / 2);
        }
        drawDottedLine(canvas);

        this.mInnerRadius = radius - mArcProgressBarInnerRingSize;
        drawInnerArcBackground(canvas);
        drawProgress(canvas);


    }

    private void drawProgress(Canvas canvas) {
        // 设置画笔元素
        Paint paint = new Paint();
        // 开抗锯齿
        paint.setAntiAlias(true);
        // 设置样式为线条
        paint.setStyle(Paint.Style.STROKE);
        // 设置线条色
        paint.setColor(mArcProgressBarColor);
        // 设置线条宽度
        paint.setStrokeWidth(mArcProgressBarLineWidth);

        // 创建弧形位置属性
        RectF oval = new RectF();
        // 计算弧形居中
        oval.set(mX / 2 - mInnerRadius, mY / 2 - mInnerRadius, mX / 2 + mInnerRadius, mY / 2 + mInnerRadius);
        // 画弧形
        int progressAngle = mArcProgressBarAngle;

        float percent = ((float) mArcProgressBarProgress) / ((float) mArcProgressBarMaxProgress);
        if (percent < 1) {
            progressAngle = (int) (mArcProgressBarAngle * percent);
        }

        if (progressAngle > 0) {
            canvas.drawArc(oval, mStartAngle, progressAngle, false, paint);
        }

    }

    private void drawInnerArcBackground(Canvas canvas) {
        // 设置画笔元素
        Paint paint = new Paint();
        // 开抗锯齿
        paint.setAntiAlias(true);
        // 设置样式为线条
        paint.setStyle(Paint.Style.STROKE);
        // 设置线条色
        paint.setColor(mArcProgressBarBackgroundColor);
        // 设置线条宽度
        paint.setStrokeWidth(mArcProgressBarLineWidth);

        // 创建弧形位置属性
        RectF oval = new RectF();
        // 计算弧形居中
        oval.set(mX / 2 - mInnerRadius, mY / 2 - mInnerRadius, mX / 2 + mInnerRadius, mY / 2 + mInnerRadius);
        // 画弧形

        canvas.drawArc(oval, mStartAngle, mArcProgressBarAngle, false, paint);
    }

    private void drawDottedLine(Canvas canvas) {
        // 设置画笔元素
        Paint paint = new Paint();
        // 开抗锯齿
        paint.setAntiAlias(true);
        // 设置样式为线条
        paint.setStyle(Paint.Style.STROKE);
        // 设置线条色
        paint.setColor(mArcProgressBarDottedLineColor);
        paint.setPathEffect(new DashPathEffect(new float[]{mArcProgressBarDottedSize, mArcProgressBarDottedSize},
                mArcProgressBarDottedSize * 2));
        // 设置线条宽度
        paint.setStrokeWidth(mArcProgressBarDottedSize);


        // 创建弧形位置属性
        RectF oval = new RectF();
        // 计算弧形居中
        oval.set(mX / 2 - radius, mY / 2 - radius, mX / 2 + radius, mY / 2 + radius);
        // 画弧形

        canvas.drawArc(oval, mStartAngle, mArcProgressBarAngle, false, paint);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public float dip2px(Context context, float dpValue) {
        try {
            float scale = context.getResources().getDisplayMetrics().density;
            return (dpValue * scale + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public float px2dip(Context context, float pxValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (pxValue / scale + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int getmArcProgressBarColor() {
        return mArcProgressBarColor;
    }

    public void setmArcProgressBarColor(int mArcProgressBarColor) {
        this.mArcProgressBarColor = mArcProgressBarColor;
    }

    public int getmArcProgressBarBackgroundColor() {
        return mArcProgressBarBackgroundColor;
    }

    public void setmArcProgressBarBackgroundColor(int mArcProgressBarBackgroundColor) {
        this.mArcProgressBarBackgroundColor = mArcProgressBarBackgroundColor;
    }

    public int getmArcProgressBarDottedLineColor() {
        return mArcProgressBarDottedLineColor;
    }

    public void setmArcProgressBarDottedLineColor(int mArcProgressBarDottedLineColor) {
        this.mArcProgressBarDottedLineColor = mArcProgressBarDottedLineColor;
    }

    public int getmArcProgressBarMaxProgress() {
        return mArcProgressBarMaxProgress;
    }

    public void setmArcProgressBarMaxProgress(int mArcProgressBarMaxProgress) {
        this.mArcProgressBarMaxProgress = mArcProgressBarMaxProgress;
    }

    public int getmArcProgressBarProgress() {
        return mArcProgressBarProgress;
    }

    public void setmArcProgressBarProgress(int mArcProgressBarProgress) {
        this.mArcProgressBarProgress = mArcProgressBarProgress;
    }

    public int getmArcProgressBarAngle() {
        return mArcProgressBarAngle;
    }

    public void setmArcProgressBarAngle(int mArcProgressBarAngle) {
        this.mArcProgressBarAngle = mArcProgressBarAngle;
    }

    public int getmArcProgressBarLineWidth() {
        return mArcProgressBarLineWidth;
    }

    public void setmArcProgressBarLineWidth(int mArcProgressBarLineWidth) {
        this.mArcProgressBarLineWidth = mArcProgressBarLineWidth;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(int innerRadius) {
        this.innerRadius = innerRadius;
    }

    public int getmArcProgressBarDottedSize() {
        return mArcProgressBarDottedSize;
    }

    public void setmArcProgressBarDottedSize(int mArcProgressBarDottedSize) {
        this.mArcProgressBarDottedSize = mArcProgressBarDottedSize;
    }

    public int getmStartAngle() {
        return mStartAngle;
    }

    public void setmStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
    }

    public int getmX() {
        return mX;
    }

    public void setmX(int mX) {
        this.mX = mX;
    }

    public int getmY() {
        return mY;
    }

    public void setmY(int mY) {
        this.mY = mY;
    }

    public int getmArcProgressBarInnerRingSize() {
        return mArcProgressBarInnerRingSize;
    }

    public void setmArcProgressBarInnerRingSize(int mArcProgressBarInnerRingSize) {
        this.mArcProgressBarInnerRingSize = mArcProgressBarInnerRingSize;
    }

    public int getmInnerRadius() {
        return mInnerRadius;
    }

    public void setmInnerRadius(int mInnerRadius) {
        this.mInnerRadius = mInnerRadius;
    }
}
