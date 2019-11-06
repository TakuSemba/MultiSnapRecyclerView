package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

/**
 * Helper class that extends SnapHelper to archive snapping
 *
 * @param gravity gravity to which the RecyclerView snaps
 * @param snapCount the number of items to scroll over
 */
class MultiSnapHelper(
    gravity: SnapGravity,
    snapCount: Int,
    private val scroller: LinearSmoothScroller
) : SnapHelper() {

  private var snapHelper: BaseSnapHelperDelegator? = null

  init {
    when (gravity) {
      SnapGravity.CENTER -> snapHelper = CenterSnapHelperDelegator(snapCount)
      SnapGravity.START -> snapHelper = StartSnapHelperDelegator(snapCount)
      SnapGravity.END -> snapHelper = EndSnapHelperDelegator(snapCount)
    }
  }

  override fun calculateDistanceToFinalSnap(
      layoutManager: RecyclerView.LayoutManager,
      view: View
  ): IntArray? {
    return snapHelper!!.calculateDistanceToFinalSnap(layoutManager, view)
  }

  override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
    return snapHelper!!.findSnapView(layoutManager)
  }

  override fun findTargetSnapPosition(
      layoutManager: RecyclerView.LayoutManager, velocityX: Int,
      velocityY: Int
  ): Int {
    return snapHelper!!.findTargetSnapPosition(layoutManager, velocityX, velocityY)
  }

  fun setListener(listener: OnSnapListener) {
    snapHelper!!.setListener(listener)
  }

  /**
   * Creates a scroller to be used in the snapping implementation.
   *
   * @param layoutManager The [RecyclerView.LayoutManager] associated with the attached [ ].
   * @return a [LinearSmoothScroller] which will handle the scrolling.
   */
  override fun createSnapScroller(
      layoutManager: RecyclerView.LayoutManager?
  ): LinearSmoothScroller? {
    return if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
      null
    } else scroller
  }
}
