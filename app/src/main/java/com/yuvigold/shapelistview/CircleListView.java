package com.yuvigold.shapelistview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class CircleListView extends ShapeListView {
    public float radius;
    public static float MoveAccumulator = 0.0F;

    public CircleListView(Context context) {
        super(context);
        this.init();
    }

    public CircleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public CircleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    @Override
    protected void init() {
        super.init();

        this.post(new Runnable() {
            public void run() {
                CircleListView.this.radius = CircleListView.this.layoutWidth / 4.0F;
            }
        });
    }

    public void setRadius(float r) {
        r = r < 0.0F ? 0.0F : r;
        this.radius = r;
        if (this.listAdapter != null) {
            this.listAdapter.notifyItemChange();
        }
    }

    @Override
    protected void updateItemPosition(final View item, final int index) {
        int itemCount = this.listAdapter.getCount();
        int existChildCount = this.getChildCount();
        boolean isLayoutEmpty = existChildCount == 0;
        double pre_IntervalAngle = isLayoutEmpty ? 0.0D : 6.283185307179586D / (double)existChildCount;
        double intervalAngle = 6.283185307179586D / (double)itemCount;

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(new float[]{(float)pre_IntervalAngle, (float)intervalAngle});
        valueAnimator.setDuration(500L);
        valueAnimator.setInterpolator(new OvershootInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float)((Float)animation.getAnimatedValue());
                LayoutParams params = (LayoutParams)item.getLayoutParams();
                params.setMargins((int)((double)(CircleListView.this.layoutCenter_x - item.getWidth() / 2.0F) + (double)CircleListView.this.radius * Math.cos((double)((float)index * value) + (double)CircleListView.MoveAccumulator * Math.PI * 2.0D)),
                        (int)((double)(CircleListView.this.layoutCenter_y - item.getHeight() / 2.0F) + (double)CircleListView.this.radius * Math.sin((double)((float)index * value) + (double)CircleListView.MoveAccumulator * Math.PI * 2.0D)),
                        0, 0);
                item.setLayoutParams(params);
            }
        });
        valueAnimator.start();
    }

    @Override
    protected float getMaxItems() { return (float)1.0 * this.listAdapter.getCount(); }
}