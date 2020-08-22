package com.yuvigold.shapelistview;

import android.view.View;

import java.util.ArrayList;

public abstract class NotifiedListAdapter {
    private ItemChangeListener itemChangeListener;

    public NotifiedListAdapter() {
    }

    public abstract int getCount();

    public abstract ArrayList<View> getAllViews();

    public abstract View getItemAt(int var1);

    public abstract void removeItemAt(int var1);

    public abstract void addItem(View var1);

    public void notifyItemChange() {
        this.itemChangeListener.onItemChange();
    }

    public void setOnItemChangeListener(ItemChangeListener listener) {
        this.itemChangeListener = listener;
    }

    interface ItemChangeListener {
        void onItemChange();
    }
}
