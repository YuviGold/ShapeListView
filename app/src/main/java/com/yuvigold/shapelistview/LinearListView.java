package com.yuvigold.shapelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class LinearListView extends ShapeListView {
    public LinearListView(Context context) {
        super(context);
        this.init();
    }

    public LinearListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public LinearListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    @Override
    protected void updateItemPosition(final View item, final int index) {
        float item_margin_width = this.itemWidth + this.itemMargin;
        float item_margin_height = this.itemHeight + this.itemMargin;

        int startX = (int)(this.layoutCenter_x - (item_margin_width / 2.0F) * this.listAdapter.getCount());
        int startY = (int)(this.layoutCenter_y - item_margin_height / 2.0F);

        int lines = (int)((index * item_margin_width) / (this.layoutWidth - item_margin_width));
        int itemX = (int)((index * item_margin_width) % (this.layoutWidth - item_margin_width));
        int itemY = (int)(item_margin_height * lines);

        LayoutParams params = (LayoutParams)item.getLayoutParams();
        params.setMargins(startX + itemX, startY + itemY,0, 0);

        item.setLayoutParams(params);
    }
}
