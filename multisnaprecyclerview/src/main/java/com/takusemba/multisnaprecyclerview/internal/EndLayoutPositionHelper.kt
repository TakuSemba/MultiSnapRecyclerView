package com.takusemba.multisnaprecyclerview.internal

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

internal class EndLayoutPositionHelper : LayoutPositionHelper {

  override fun getDistance(
      layoutManager: RecyclerView.LayoutManager, targetView: View,
      helper: OrientationHelper
  ): Int {
    val childEnd = getChildPosition(targetView, helper)
    val containerEnd = getContainerPosition(layoutManager, helper)
    return childEnd - containerEnd
  }

  override fun getContainerPosition(
      layoutManager: RecyclerView.LayoutManager,
      helper: OrientationHelper
  ): Int {
    return if (layoutManager.clipToPadding)
      helper.startAfterPadding + helper.totalSpace
    else
      helper.end - helper.endPadding
  }

  override fun getChildPosition(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView)
  }

  override fun shouldSkipTarget(
      targetView: View, layoutManager: RecyclerView.LayoutManager,
      helper: OrientationHelper, forwardDirection: Boolean
  ): Boolean {
    return if (forwardDirection)
      getDistance(layoutManager, targetView, helper) < 0
    else
      getDistance(layoutManager, targetView, helper) > 0
  }
}