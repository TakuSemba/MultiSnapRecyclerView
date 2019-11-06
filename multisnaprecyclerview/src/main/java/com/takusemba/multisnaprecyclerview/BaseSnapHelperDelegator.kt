package com.takusemba.multisnaprecyclerview

import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * abstract class to delegate [MultiSnapHelper] methods.
 */
abstract class BaseSnapHelperDelegator {

  /**
   * delegate method of
   * [MultiSnapHelper.calculateDistanceToFinalSnap]
   *
   * @return the distance to the gravitated snap position
   */
  abstract fun calculateDistanceToFinalSnap(
      layoutManager: RecyclerView.LayoutManager,
      targetView: View
  ): IntArray

  /**
   * delegate method of [MultiSnapHelper.findSnapView]
   *
   * @return the view to snap when RecyclerView is in the idle state
   */
  abstract fun findSnapView(layoutManager: RecyclerView.LayoutManager): View?

  /**
   * delegate method of
   * [MultiSnapHelper.findTargetSnapPosition]
   *
   * @return the view to snap when RecyclerView is fling
   */
  abstract fun findTargetSnapPosition(
      layoutManager: RecyclerView.LayoutManager, velocityX: Int,
      velocityY: Int
  ): Int

  /**
   * set a listener to return a position of the snapped view
   *
   * @param listener [OnSnapListener]
   */
  abstract fun setListener(listener: OnSnapListener)
}
