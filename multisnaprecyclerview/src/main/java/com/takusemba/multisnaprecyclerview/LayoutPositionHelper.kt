package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

interface LayoutPositionHelper {

  // TODO 後で消す
  /**
   * calculate the distance between [SnapHelperDelegator.getContainerPosition] and [SnapHelperDelegator.getChildPosition]
   *
   * @return the distance to the gravitated snap position
   */
  abstract fun getDistance(
      layoutManager: RecyclerView.LayoutManager,
      targetView: View,
      helper: OrientationHelper
  ): Int

  /**
   * find the position to snap.
   *
   * @return the gravitated snap position.
   */
  abstract fun getContainerPosition(
      layoutManager: RecyclerView.LayoutManager,
      helper: OrientationHelper
  ): Int

  /**
   * find the position where the RecyclerView will start to snap
   *
   * @return the position of the gravitated side on the target view
   */
  abstract fun getChildPosition(targetView: View, helper: OrientationHelper): Int

  // TODO 後で消す
  /**
   * check if the view should be skipped or not
   *
   * @return true if the view should be skipped, otherwise false
   */
  abstract fun shouldSkipTarget(
      targetView: View,
      layoutManager: RecyclerView.LayoutManager,
      helper: OrientationHelper,
      forwardDirection: Boolean
  ): Boolean
}