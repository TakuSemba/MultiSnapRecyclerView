package com.takusemba.multisnaprecyclerview

import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kotlin.math.abs
import kotlin.math.max

/**
 * Helper class that extends SnapHelper to archive snapping
 *
 * @param gravity gravity to which the RecyclerView snaps
 * @param interval the number of items to scroll over
 */
class MultiSnapHelper(
    gravity: SnapGravity = DEFAULT_GRAVITY,
    private val interval: Int = DEFAULT_INTERVAL,
    private val speedMsPerInch: Float = DEFAULT_SPEED_MS_PER_INCH
) : SnapHelper() {

  private val layoutPositionHelper: LayoutPositionHelper = gravity.createLayoutPositionHelper()

  private var recyclerView: RecyclerView? = null

  /**
   * previousClosestPosition should only be set in [findSnapView]
   */
  private var previousClosestPosition = 0
  private var listener: OnSnapListener? = null

  fun setListener(listener: OnSnapListener) {
    this.listener = listener
  }

  override fun attachToRecyclerView(recyclerView: RecyclerView?) {
    super.attachToRecyclerView(recyclerView)
    this.recyclerView = recyclerView
  }

  override fun calculateDistanceToFinalSnap(
      layoutManager: RecyclerView.LayoutManager,
      targetView: View
  ): IntArray {
    val out = IntArray(2)
    if (layoutManager.canScrollHorizontally()) {
      out[0] = layoutPositionHelper.getDistance(
          layoutManager,
          targetView,
          OrientationHelper.createHorizontalHelper(layoutManager)
      )
    } else {
      out[0] = 0
    }

    if (layoutManager.canScrollVertically()) {
      out[1] = layoutPositionHelper.getDistance(
          layoutManager,
          targetView,
          OrientationHelper.createVerticalHelper(layoutManager)
      )
    } else {
      out[1] = 0
    }
    return out
  }

  override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
    val helper = if (layoutManager.canScrollHorizontally()) {
      OrientationHelper.createHorizontalHelper(layoutManager)
    } else {
      OrientationHelper.createVerticalHelper(layoutManager)
    }
    val childCount = layoutManager.childCount
    if (childCount == 0) return null

    var closestChild: View? = null
    var closestPosition = RecyclerView.NO_POSITION
    val containerPosition = layoutPositionHelper.getContainerPosition(layoutManager, helper)
    var absClosest = Integer.MAX_VALUE

    for (i in 0 until childCount) {
      val child = layoutManager.getChildAt(i)!!
      val childPosition = layoutPositionHelper.getChildPosition(child, helper)
      val absDistance = abs(childPosition - containerPosition)
      if (helper.getDecoratedStart(child) == 0 && previousClosestPosition != 0
          && layoutManager.getPosition(child) == 0) {
        // RecyclerView reached start
        closestChild = child
        closestPosition = layoutManager.getPosition(closestChild)
        break
      }
      if (helper.getDecoratedEnd(child) == helper.endAfterPadding
          && previousClosestPosition != layoutManager.itemCount - 1
          && layoutManager.getPosition(child) == layoutManager.itemCount - 1) {
        // RecyclerView reached end
        closestChild = child
        closestPosition = layoutManager.getPosition(closestChild)
        break
      }
      if (previousClosestPosition == layoutManager.getPosition(
              child) && layoutPositionHelper.getDistance(
              layoutManager, child, helper) == 0) {
        // child is already set to the position.
        closestChild = child
        closestPosition = layoutManager.getPosition(closestChild)
        break
      }
      if (layoutManager.getPosition(child) % interval != 0) {
        continue
      }
      if (absDistance < absClosest) {
        absClosest = absDistance
        closestChild = child
        closestPosition = layoutManager.getPosition(closestChild)
      }
    }
    previousClosestPosition = if (closestPosition == RecyclerView.NO_POSITION) previousClosestPosition else closestPosition
    if (listener != null && closestPosition != RecyclerView.NO_POSITION) {
      listener!!.snapped(closestPosition)
    }
    return closestChild!!
  }

  override fun findTargetSnapPosition(
      layoutManager: RecyclerView.LayoutManager, velocityX: Int,
      velocityY: Int
  ): Int {
    val helper = if (layoutManager.canScrollHorizontally()) {
      OrientationHelper.createHorizontalHelper(layoutManager)
    } else {
      OrientationHelper.createVerticalHelper(layoutManager)
    }
    val forwardDirection = if (layoutManager.canScrollHorizontally()) velocityX > 0 else velocityY > 0
    val firstExpectedPosition: Int
    firstExpectedPosition = if (forwardDirection) 0 else layoutManager.itemCount - 1
    var i = firstExpectedPosition
    while (if (forwardDirection) i <= layoutManager.itemCount - 1 else i >= 0) {
      val view = layoutManager.findViewByPosition(i)
      if (view == null || layoutPositionHelper.shouldSkipTarget(view, layoutManager, helper,
              forwardDirection)) {
        i = if (forwardDirection) i + 1 else i - 1
        continue
      }
      return if (forwardDirection) {
        val diff = i - previousClosestPosition
        val factor = if (diff % interval == 0) diff / interval else diff / interval + 1
        previousClosestPosition + interval * factor
      } else {
        val diff = previousClosestPosition - i
        val factor = if (diff % interval == 0) diff / interval else diff / interval + 1
        if (previousClosestPosition == layoutManager.itemCount - 1 && previousClosestPosition % interval != 0) {
          previousClosestPosition - previousClosestPosition % interval + interval - interval * factor
        } else {
          previousClosestPosition - interval * factor
        }
      }
    }
    // reached to end or start
    return if (forwardDirection) layoutManager.itemCount - 1 else 0
  }

  /**
   * Creates a scroller with [speedMsPerInch].
   *
   * This is almost the same as [SnapHelper.createSnapScroller] except for speed handling.
   */
  override fun createScroller(
      layoutManager: RecyclerView.LayoutManager?
  ): RecyclerView.SmoothScroller? {
    // return null if RecyclerView.SmoothScroller.ScrollVectorProvider was not implemented.
    if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
      return null
    }
    // return null if current RecyclerView is null.
    val rv = recyclerView ?: return null
    return object : LinearSmoothScroller(rv.context) {
      override fun onTargetFound(targetView: View, state: RecyclerView.State, action: Action) {
        val snapDistances = calculateDistanceToFinalSnap(layoutManager, targetView)
        val dx = snapDistances[0]
        val dy = snapDistances[1]
        val time = calculateTimeForDeceleration(max(abs(dx), abs(dy)))
        if (time > 0) {
          action.update(dx, dy, time, mDecelerateInterpolator)
        }
      }

      override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
        return speedMsPerInch / displayMetrics.densityDpi
      }
    }
  }

  companion object {

    val DEFAULT_GRAVITY = SnapGravity.START

    const val DEFAULT_INTERVAL = 1

    const val DEFAULT_SPEED_MS_PER_INCH = 100f
  }
}
