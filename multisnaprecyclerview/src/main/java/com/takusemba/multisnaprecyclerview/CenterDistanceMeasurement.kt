package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

class CenterDistanceMeasurement : DistanceMeasurement {

  override fun measureContainer(
      layoutManager: RecyclerView.LayoutManager,
      helper: OrientationHelper
  ): Int {
    return if (layoutManager.clipToPadding)
      helper.startAfterPadding + helper.totalSpace / 2
    else
      helper.end / 2
  }

  override fun measureChild(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView) / 2
  }
}