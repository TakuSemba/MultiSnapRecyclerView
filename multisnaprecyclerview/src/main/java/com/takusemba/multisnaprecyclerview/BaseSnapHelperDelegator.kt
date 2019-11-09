package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Abstract class to delegate [MultiSnapHelper] methods.
 */
abstract class BaseSnapHelperDelegator {

  /**
   * A delegate method of [MultiSnapHelper.calculateDistanceToFinalSnap]
   *
   * @return the distance to the gravitated snap position
   */
  abstract fun calculateDistanceToFinalSnap(
      layoutManager: RecyclerView.LayoutManager,
      targetView: View
  ): IntArray

  /**
   * A Delegate method of [MultiSnapHelper.findSnapView]
   *
   * @return the view to snap when RecyclerView is in the idle state
   */
  abstract fun findSnapView(layoutManager: RecyclerView.LayoutManager): View?

  /**
   * A Delegate method of [MultiSnapHelper.findTargetSnapPosition]
   *
   * @return the view to snap when RecyclerView is fling
   */
  abstract fun findTargetSnapPosition(
      layoutManager: RecyclerView.LayoutManager,
      velocityX: Int,
      velocityY: Int
  ): Int

  /**
   * Sets a listener to return a position of the snapped view
   *
   * @param listener [OnSnapListener]
   */
  abstract fun setListener(listener: OnSnapListener)
}
