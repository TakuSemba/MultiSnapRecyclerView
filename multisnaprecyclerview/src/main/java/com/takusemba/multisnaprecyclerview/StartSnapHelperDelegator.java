package com.takusemba.multisnaprecyclerview;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * StartSnapHelperDelegator
 *
 * @author takusemba
 * @since 30/07/2017
 **/
class StartSnapHelperDelegator extends SnapHelperDelegator {

    /**
     * Constructor
     *
     * @param snapCount the number of items to scroll over
     */
    StartSnapHelperDelegator(int snapCount) {
        super(snapCount);
    }

    @Override
    int getDistance(RecyclerView.LayoutManager layoutManager, View targetView, OrientationHelper helper) {
        final int childStart = getChildPosition(targetView, helper);
        final int containerStart = getContainerPosition(layoutManager, helper);
        return childStart - containerStart;
    }

    @Override
    int getContainerPosition(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        return helper.getStartAfterPadding();
    }

    @Override
    int getChildPosition(View targetView, OrientationHelper helper) {
        return helper.getDecoratedStart(targetView);
    }

    @Override
    boolean shouldSkipTarget(View targetView, RecyclerView.LayoutManager layoutManager, OrientationHelper helper, boolean forwardDirection) {
        return forwardDirection
                ? getDistance(layoutManager, targetView, helper) < 0
                : getDistance(layoutManager, targetView, helper) > 0;
    }
}
