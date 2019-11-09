package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper

class EndCoordinateHelper : CoordinateHelper {

  override fun getBaseCoordinate(helper: OrientationHelper): Int {
    return helper.endAfterPadding
  }

  override fun getTargetCoordinate(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView)
  }
}