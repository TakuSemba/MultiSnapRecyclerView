package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

class StartDistanceMeasurement : DistanceMeasurement {

  override fun measureContainer(
      layoutManager: RecyclerView.LayoutManager,
      helper: OrientationHelper
  ): Int {
    return helper.startAfterPadding
  }

  override fun measureChild(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView)
  }
}