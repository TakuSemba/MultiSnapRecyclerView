package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper

/**
 * [CoordinateHelper] for the end gravity.
 */
class EndCoordinateHelper : CoordinateHelper {

  /**
   * Gets the coordinate of the end of RecyclerView.
   */
  override fun getBaseCoordinate(helper: OrientationHelper): Int {
    return helper.endAfterPadding
  }

  /**
   * Gets the coordinate from the end of [targetView].
   */
  override fun getTargetCoordinate(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView)
  }
}