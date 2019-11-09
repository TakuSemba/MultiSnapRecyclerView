package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper

interface CoordinateHelper {

  /**
   * find the coordinate of RceyclerView.
   */
  fun getBaseCoordinate(helper: OrientationHelper): Int

  /**
   * find the coordinate of [targetView] in RecyclerView.
   */
  fun getTargetCoordinate(targetView: View, helper: OrientationHelper): Int
}