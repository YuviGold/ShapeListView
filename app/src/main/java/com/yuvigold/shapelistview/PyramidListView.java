package com.yuvigold.shapelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class PyramidListView extends ShapeListView {
    private Orientation orientation = Orientation.UPSIDE_DOWN;

    public enum Orientation {
        UPSIDE_DOWN,
        DOWNSIDE_UP
    }

    public PyramidListView(Context context) {
        super(context);
        this.init();
    }

    public PyramidListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public PyramidListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    protected void updateItemPosition(final View item, final int index) {
        float item_margin_width = this.itemWidth + this.itemMargin;
        float item_margin_height = this.itemHeight + this.itemMargin;


        int row = getRow((index + 1));
        int rowX = (int)(this.layoutWidth / 2.0F - ((item_margin_width / 2.0F) + ((row - 1) * item_margin_width)));
        int rowY = (int)(item_margin_height * (row - 1));

        int startX = 0;
        int startY = (int)(this.layoutCenter_y - (item_margin_height / 2.0F) * this.getRow(this.listAdapter.getCount()));

        if (orientation == Orientation.DOWNSIDE_UP) {
            rowY = -rowY;
            startY = (int)(this.layoutHeight - item_margin_height);
        }

        int itemX = rowX + (int)(getColumn(index + 1) * item_margin_width) * 2;

        LayoutParams params = (LayoutParams)item.getLayoutParams();
        params.setMargins(startX + itemX, startY + rowY,0, 0);

        item.setLayoutParams(params);
    }

    int amountOfItems(int n) {
        return (n * (n + 1)) / 2;
    }

    int getRow(int n) throws IllegalArgumentException {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        int row = 1;

        while (amountOfItems(row) < n) {
            row++;
        }

        return row;
    }

    int getColumn(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        if (n == 1) { return 0; }

        int row = getRow(n);

        return n - (amountOfItems(row) - row) - 1;
    }


    @Override
    protected float getMaxItems() { return 2 * this.getRow(this.listAdapter.getCount()); }
}
