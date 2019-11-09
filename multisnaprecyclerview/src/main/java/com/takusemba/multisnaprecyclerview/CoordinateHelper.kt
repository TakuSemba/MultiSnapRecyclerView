package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper

interface CoordinateHelper {

  /**
   * find the position to snap.
   *
   * @return the gravitated snap position.
   */
  fun getBaseCoordinate(helper: OrientationHelper): Int

  /**
   * find the position where the RecyclerView will start to snap
   *
   * @return the position of the gravitated side on the target view
   */
  fun getTargetCoordinate(targetView: View, helper: OrientationHelper): Int
}