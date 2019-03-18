package com.takusemba.multisnaprecyclerview;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * {@link SnapHelperDelegator} for end gravity
 **/
class EndSnapHelperDelegator extends SnapHelperDelegator {

  /**
   * Constructor
   *
   * @param snapCount the number of items to scroll over
   * @param isRtl the boolean variable that shows the current layout direction
   */
  EndSnapHelperDelegator(int snapCount, boolean isRtl) {
    super(snapCount, isRtl);
  }

  @Override int getDistance(RecyclerView.LayoutManager layoutManager, View targetView,
      OrientationHelper helper) {
    final int childEnd = getChildPosition(targetView, helper);
    final int containerEnd = getContainerPosition(layoutManager, helper);
    return childEnd - containerEnd;
  }

  @Override int getContainerPosition(RecyclerView.LayoutManager layoutManager,
      OrientationHelper helper) {
    return layoutManager.getClipToPadding() ? helper.getStartAfterPadding() + helper.getTotalSpace()
        : helper.getEnd() - helper.getEndPadding();
  }

  @Override int getChildPosition(View targetView, OrientationHelper helper) {
    return helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView);
  }

  @Override boolean shouldSkipTarget(View targetView, RecyclerView.LayoutManager layoutManager,
      OrientationHelper helper, boolean forwardDirection) {
    return forwardDirection ? getDistance(layoutManager, targetView, helper) < 0
        : getDistance(layoutManager, targetView, helper) > 0;
  }
}
