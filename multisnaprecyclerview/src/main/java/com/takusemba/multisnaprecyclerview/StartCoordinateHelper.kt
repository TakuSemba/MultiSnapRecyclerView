package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper

/**
 * [CoordinateHelper] for the start gravity.
 */
class StartCoordinateHelper : CoordinateHelper {

  /**
   * Gets the coordinate of the start of RecyclerView.
   */
  override fun getBaseCoordinate(helper: OrientationHelper): Int {
    return helper.startAfterPadding
  }

  /**
   * Gets the coordinate from the start of [targetView].
   */
  override fun getTargetCoordinate(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView)
  }
}