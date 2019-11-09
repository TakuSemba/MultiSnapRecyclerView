package com.takusemba.multisnaprecyclerview

import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.OrientationHelper.createHorizontalHelper
import androidx.recyclerview.widget.OrientationHelper.createVerticalHelper
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

  private val coordinateHelper: CoordinateHelper = createLayoutPositionHelper(gravity)
  private var orientationHelper: OrientationHelper? = null

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
    val distance = getCoordinateDelta(targetView, getOrientationHelper(layoutManager))
    return intArrayOf(
        if (layoutManager.canScrollHorizontally()) distance else 0, // x-axis
        if (layoutManager.canScrollVertically()) distance else 0 // y-axis
    )
  }

  override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
    val helper = getOrientationHelper(layoutManager)
    val childCount = layoutManager.childCount
    if (childCount == 0) return null

    var closestChild: View? = null
    var closestPosition = RecyclerView.NO_POSITION
    var absClosest = Integer.MAX_VALUE

    for (i in 0 until childCount) {
      val child = layoutManager.getChildAt(i) as View
      val delta = getCoordinateDelta(child, getOrientationHelper(layoutManager))
      if (helper.getDecoratedStart(child) == 0 &&
          previousClosestPosition != 0 &&
          layoutManager.getPosition(child) == 0) {
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
      if (previousClosestPosition == layoutManager.getPosition(child) &&
          getCoordinateDelta(child, getOrientationHelper(layoutManager)) == 0
      ) {
        // child is already set to the position.
        closestChild = child
        closestPosition = layoutManager.getPosition(closestChild)
        break
      }
      if (layoutManager.getPosition(child) % interval != 0) {
        continue
      }
      if (delta < absClosest) {
        absClosest = delta
        closestChild = child
        closestPosition = layoutManager.getPosition(closestChild)
      }
    }
    previousClosestPosition = if (closestPosition == RecyclerView.NO_POSITION) previousClosestPosition else closestPosition
    if (listener != null && closestPosition != RecyclerView.NO_POSITION) {
      listener?.snapped(closestPosition)
    }
    return closestChild
  }

  /**
   * Finds and returns the next index to snap.
   *
   * For forward scrolling, this tries to find the index from the first index by incrementing.
   * Returns the index if the valid next index is found, otherwise returns the final index which means reaching the end edge.
   *
   * For backward scrolling, this tries to find the index from the last index by decrementing.
   * Returns the index if the valid next index is found, otherwise returns the final index which means reaching the start edge.
   */
  override fun findTargetSnapPosition(
      layoutManager: RecyclerView.LayoutManager,
      velocityX: Int,
      velocityY: Int
  ): Int {
    val velocity = if (layoutManager.canScrollHorizontally()) velocityX else velocityY
    val first = 0
    val last = layoutManager.itemCount - 1
    val range = if (0 < velocity) first..last else last downTo first
    val progression = range step 1
    val iterator = progression.iterator()

    var index: Int = if (0 < velocity) first else last

    // find first valid position
    // FIXME binary search will improve the speed to find the first valid position.
    while (iterator.hasNext()) {
      index = iterator.next()
      val view = layoutManager.findViewByPosition(index) ?: continue
      val delta = getCoordinateDelta(view, getOrientationHelper(layoutManager))
      if (if (0 < velocity) 0 < delta else delta < 0) {
        break
      }
    }
    // first first valid position in interval
    if (index % interval == 0) {
      return index
    } else {
      while (iterator.hasNext()) {
        index = iterator.next()
        if (index % interval == 0) {
          return index
        }
      }
    }
    return index // reached the edge
  }

  /**
   * Creates a scroller to make a smooth scroll with specified speed.
   *
   * This is almost the same as [SnapHelper.createSnapScroller] except for the speed handling.
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

  private fun getCoordinateDelta(targetView: View, orientationHelper: OrientationHelper): Int {
    val childCoordinate = coordinateHelper.getTargetCoordinate(targetView, orientationHelper)
    val baseCoordinate = coordinateHelper.getBaseCoordinate(orientationHelper)
    return childCoordinate - baseCoordinate
  }

  private fun getOrientationHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
    return orientationHelper ?: when {
      layoutManager.canScrollHorizontally() -> createHorizontalHelper(layoutManager)
      layoutManager.canScrollVertically() -> createVerticalHelper(layoutManager)
      else -> throw IllegalStateException("unknown orientation")
    }.also { newOrientationHelper ->
      this.orientationHelper = newOrientationHelper
    }
  }

  private fun createLayoutPositionHelper(gravity: SnapGravity): CoordinateHelper {
    return when (gravity) {
      SnapGravity.CENTER -> CenterCoordinateHelper()
      SnapGravity.START -> StartCoordinateHelper()
      SnapGravity.END -> EndCoordinateHelper()
    }
  }

  companion object {

    val DEFAULT_GRAVITY = SnapGravity.START

    const val DEFAULT_INTERVAL = 1

    const val DEFAULT_SPEED_MS_PER_INCH = 100f
  }
}
