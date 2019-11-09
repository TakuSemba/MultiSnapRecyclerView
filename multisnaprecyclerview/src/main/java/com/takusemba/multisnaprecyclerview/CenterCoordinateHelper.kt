package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper

/**
 * [CoordinateHelper] for the center gravity.
 */
class CenterCoordinateHelper : CoordinateHelper {

  /**
   * Gets the coordinate of the center of RecyclerView.
   */
  override fun getBaseCoordinate(helper: OrientationHelper): Int {
    return helper.startAfterPadding + helper.totalSpace / 2
  }

  /**
   * Gets the coordinate from the center of [targetView].
   */
  override fun getTargetCoordinate(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView) / 2
  }
}