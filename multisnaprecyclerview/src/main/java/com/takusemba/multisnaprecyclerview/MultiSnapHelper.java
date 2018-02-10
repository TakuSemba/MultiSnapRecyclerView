package com.takusemba.multisnaprecyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

/**
 * MultiSnapHelper
 *
 * @author takusemba
 * @since 30/07/2017
 **/
class MultiSnapHelper extends SnapHelper {

    private BaseSnapHelperDelegator snapHelper;
    private LinearSmoothScroller scroller;

    /**
     * Constructor
     *
     * @param gravity   gravity to which the RecyclerView snaps
     * @param snapCount the number of items to scroll over
     */
    MultiSnapHelper(SnapGravity gravity, int snapCount, LinearSmoothScroller scroller) {
        this.scroller = scroller;
        switch (gravity) {
            case CENTER:
                snapHelper = new CenterSnapHelperDelegator(snapCount);
                break;
            case START:
                snapHelper = new StartSnapHelperDelegator(snapCount);
                break;
            case END:
                snapHelper = new EndSnapHelperDelegator(snapCount);
                break;
            default:
                throw new IllegalArgumentException("not supported gravity");
        }
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View view) {
        return snapHelper.calculateDistanceToFinalSnap(layoutManager, view);
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        return snapHelper.findSnapView(layoutManager);
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        return snapHelper.findTargetSnapPosition(layoutManager, velocityX, velocityY);
    }

    void setListener(OnSnapListener listener) {
        snapHelper.setListener(listener);
    }

    /**
     * Creates a scroller to be used in the snapping implementation.
     *
     * @param layoutManager The {@link RecyclerView.LayoutManager} associated with the attached {@link RecyclerView}.
     * @return a {@link LinearSmoothScroller} which will handle the scrolling.
     */
    @Nullable
    protected LinearSmoothScroller createSnapScroller(final RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return scroller;
    }
}
