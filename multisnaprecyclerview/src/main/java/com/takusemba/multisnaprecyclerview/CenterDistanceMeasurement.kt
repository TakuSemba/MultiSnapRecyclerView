package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

class CenterDistanceMeasurement : DistanceMeasurement {

  override fun getDistance(
      layoutManager: RecyclerView.LayoutManager,
      targetView: View,
      helper: OrientationHelper
  ): Int {
    val childCenter = measureChildDistance(targetView, helper)
    val containerCenter = measureContainerDistance(layoutManager, helper)
    return childCenter - containerCenter
  }

  override fun measureContainerDistance(
      layoutManager: RecyclerView.LayoutManager,
      helper: OrientationHelper
  ): Int {
    return if (layoutManager.clipToPadding)
      helper.startAfterPadding + helper.totalSpace / 2
    else
      helper.end / 2
  }

  override fun measureChildDistance(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView) / 2
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