package com.silence.movies.ui.base;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridAutoFitLayoutManager extends GridLayoutManager {
    private int columnWidth;
    private boolean isColumnWidthChanged = true;
    private int lastWidth;
    private int lastHeight;

    public GridAutoFitLayoutManager(@NonNull final Context context, final int columnWidth) {
        /* Initially set spanCount to 1, will be changed automatically later. */
        super(context, 1);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    private int checkedColumnWidth(@NonNull final Context context, final int columnWidth) {
        if (columnWidth <= 0) {
            /* Set default columnWidth value (48dp here). It is better to move this constant
            to static constant on top, but we need context to convert it to dp, so can't really
            do so. */
            this.columnWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48,
                    context.getResources().getDisplayMetrics());
        }
        return columnWidth;
    }

    private void setColumnWidth(final int newColumnWidth) {
        if (newColumnWidth > 0 && newColumnWidth != columnWidth) {
            columnWidth = newColumnWidth;
            isColumnWidthChanged = true;
        }
    }

    @Override
    public void onLayoutChildren(@NonNull final RecyclerView.Recycler recycler,
                                 @NonNull final RecyclerView.State state) {

        if (columnWidth > 0 && getWidth() > 0 && getHeight() > 0
                && (isColumnWidthChanged || lastWidth != getWidth() || lastHeight != getHeight())) {

            final int spanCount = calculateSpanCount(1, getTotalSpace());
            setSpanCount(spanCount);
            isColumnWidthChanged = false;
        }
        lastWidth = getWidth();
        lastHeight = getHeight();
        super.onLayoutChildren(recycler, state);
    }

    private int getTotalSpace(){
        if (getOrientation() == VERTICAL) {
            return getWidth() - getPaddingRight() - getPaddingLeft();
        } else {
            return getHeight() - getPaddingTop() - getPaddingBottom();
        }
    }

    private int calculateSpanCount(int first, int totalSpace) {
        return Math.max(first, totalSpace / columnWidth);
    }
}
