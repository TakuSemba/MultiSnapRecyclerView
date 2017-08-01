package com.takusemba.multisnaprecyclerview;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Target
 *
 * @author takusemba
 * @since 30/07/2017
 **/
class CenterSnapHelperDelegator extends SnapHelperDelegator {

    /**
     * Constructor
     *
     * @param snapCount the number of items to scroll over
     */
    CenterSnapHelperDelegator(int snapCount) {
        super(snapCount);
    }

    @Override
    int getDistance(RecyclerView.LayoutManager layoutManager, View targetView, OrientationHelper helper) {
        final int childCenter = getChildPosition(targetView, helper);
        final int containerCenter = getContainerPosition(layoutManager, helper);
        return childCenter - containerCenter;
    }

    @Override
    int getContainerPosition(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        return layoutManager.getClipToPadding()
                ? helper.getStartAfterPadding() + helper.getTotalSpace() / 2
                : helper.getEnd() / 2;
    }

    @Override
    int getChildPosition(View targetView, OrientationHelper helper) {
        return helper.getDecoratedStart(targetView) + (helper.getDecoratedMeasurement(targetView) / 2);
    }
}
