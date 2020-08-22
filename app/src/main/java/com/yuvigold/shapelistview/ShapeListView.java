package com.yuvigold.shapelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public abstract class ShapeListView extends RelativeLayout implements NotifiedListAdapter.ItemChangeListener {
    public ArrayList<View> itemViewList;
    protected NotifiedListAdapter listAdapter;

    public float layoutWidth;
    public float layoutHeight;
    public float layoutCenter_x;
    public float layoutCenter_y;
    public float itemWidth;
    public float itemHeight;
    public float itemMargin;

    public ShapeListView(Context context) {
        super(context);
        this.init();
    }

    public ShapeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public ShapeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    protected void init() {
        this.recalculate();
        this.itemViewList = new ArrayList<>();
    }

    public void recalculate() {
        this.post(new Runnable() {
            public void run() {
                ShapeListView.this.layoutWidth = (float)ShapeListView.this.getWidth();
                ShapeListView.this.layoutHeight = (float)ShapeListView.this.getHeight();
                ShapeListView.this.layoutCenter_x = ShapeListView.this.layoutWidth / 2.0F;
                ShapeListView.this.layoutCenter_y = ShapeListView.this.layoutHeight / 2.0F;
                ShapeListView.this.itemWidth = 0;
                ShapeListView.this.itemHeight = 0;
                ShapeListView.this.itemMargin = 5;
            }
        });
    }

    public void setAdapter(NotifiedListAdapter adapter) {
        this.listAdapter = adapter;
        this.listAdapter.setOnItemChangeListener(this);
        this.listAdapter.notifyItemChange();
    }

    public void onItemChange() {
        Log.d(getClass().toString(), "size " + this.listAdapter.getCount());

        if (this.listAdapter.getCount() > 0) {
            this.listAdapter.getItemAt(0).post(new Runnable() {
                @Override
                public void run() {
                    ShapeListView.this.itemWidth = ShapeListView.this.layoutWidth / ShapeListView.this.getMaxItems();
                    ShapeListView.this.itemHeight = ShapeListView.this.layoutHeight / ShapeListView.this.getMaxItems();
                }
            });
        }

        for (int i = 0; i < this.listAdapter.getCount(); ++i) {
            final View item = this.listAdapter.getItemAt(i);

            if (item.getParent() == null) {
                item.setVisibility(View.INVISIBLE); // Sets the item as visible after location
                this.addView(item);
            }

            final int index = i;

            // Runs only when the screen already built to get sizes
            item.post(new Runnable() {
                public void run() {
                    updateItemParameters(item);
                    updateItemPosition(item, index);
                    Log.d(getClass().toString(), "Item " + index + " " + item.getLayoutParams().width + " " + item.getLayoutParams().height);
                    item.setVisibility(View.VISIBLE);
                }
            });
        }

        for (int i = 0; i < this.itemViewList.size(); ++i) {
            View itemAfterChanged = (View)this.itemViewList.get(i);
            if (this.listAdapter.getAllViews().indexOf(itemAfterChanged) == -1) {
                this.removeView(itemAfterChanged);
            }
        }

        this.itemViewList = (ArrayList)this.listAdapter.getAllViews().clone();
    }

    protected abstract void updateItemPosition(final View item, final int index);

    protected float getMaxItems() { return (float)1.5 * this.listAdapter.getCount(); }

    protected void updateItemParameters(View item) {
        item.getLayoutParams().width = (int)this.itemWidth;
        item.getLayoutParams().height = (int)this.itemHeight;
    }
}