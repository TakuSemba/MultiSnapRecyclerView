package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper

class StartCoordinateHelper : CoordinateHelper {

  override fun getBaseCoordinate(helper: OrientationHelper): Int {
    return helper.startAfterPadding
  }

  override fun getTargetCoordinate(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView)
  }
}