package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

class EndDistanceMeasurement : DistanceMeasurement {

  override fun measureContainer(
      layoutManager: RecyclerView.LayoutManager,
      helper: OrientationHelper
  ): Int {
    return if (layoutManager.clipToPadding)
      helper.startAfterPadding + helper.totalSpace
    else
      helper.end - helper.endPadding
  }

  override fun measureChild(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView)
  }
}