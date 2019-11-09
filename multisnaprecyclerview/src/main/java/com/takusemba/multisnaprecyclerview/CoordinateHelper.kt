package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

interface CoordinateHelper {

  /**
   * find the position to snap.
   *
   * @return the gravitated snap position.
   */
  fun getBaseCoordinate(
      layoutManager: RecyclerView.LayoutManager,
      helper: OrientationHelper
  ): Int

  /**
   * find the position where the RecyclerView will start to snap
   *
   * @return the position of the gravitated side on the target view
   */
  fun getChildCoordinate(targetView: View, helper: OrientationHelper): Int
}