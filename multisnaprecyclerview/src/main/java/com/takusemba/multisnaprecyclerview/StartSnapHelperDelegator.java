package com.takusemba.multisnaprecyclerview;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * {@link SnapHelperDelegator} for start gravity
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

  @Override int getDistance(RecyclerView.LayoutManager layoutManager, View targetView,
      OrientationHelper helper) {
    final int childStart = getChildPosition(targetView, helper);
    final int containerStart = getContainerPosition(layoutManager, helper);
    return childStart - containerStart;
  }

  @Override int getContainerPosition(RecyclerView.LayoutManager layoutManager,
      OrientationHelper helper) {
    return helper.getStartAfterPadding();
  }

  @Override int getChildPosition(View targetView, OrientationHelper helper) {
    return helper.getDecoratedStart(targetView);
  }

  @Override boolean shouldSkipTarget(View targetView, RecyclerView.LayoutManager layoutManager,
      OrientationHelper helper, boolean forwardDirection) {
    return forwardDirection ? getDistance(layoutManager, targetView, helper) < 0
        : getDistance(layoutManager, targetView, helper) > 0;
  }
}
