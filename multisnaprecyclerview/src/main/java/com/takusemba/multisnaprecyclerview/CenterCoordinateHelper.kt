package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper

class CenterCoordinateHelper : CoordinateHelper {

  override fun getBaseCoordinate(helper: OrientationHelper): Int {
    return helper.startAfterPadding + helper.totalSpace / 2
  }

  override fun getTargetCoordinate(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView) / 2
  }
}