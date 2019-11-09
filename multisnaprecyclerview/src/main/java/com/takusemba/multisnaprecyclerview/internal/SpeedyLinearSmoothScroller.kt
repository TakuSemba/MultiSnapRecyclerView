package com.takusemba.multisnaprecyclerview.internal

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller
import com.takusemba.multisnaprecyclerview.SnapGravity

internal class SpeedyLinearSmoothScroller(
    context: Context,
    private val gravity: SnapGravity,
    private val speedMsPerInch: Float
) : LinearSmoothScroller(context) {

  override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
    return super.computeScrollVectorForPosition(targetPosition)
  }

  override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
    return speedMsPerInch / displayMetrics.densityDpi
  }
}