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
 * [SnapHelper] that archives multi interval snapping.
 *
 * @param gravity the gravity to which the RecyclerView snaps.
 * @param interval the number of items to scroll over.
 * @param speedMsPerInch the speed of scrolling.
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
  private var previousClosestPosition = 0 // set the first index
  private var listener: OnSnapListener? = null

  override fun attachToRecyclerView(recyclerView: RecyclerView?) {
    super.attachToRecyclerView(recyclerView)
    this.recyclerView = recyclerView
  }

  /**
   * Calculates the distance from [targetView].
   * This will be calculated based off of the specified [SnapGravity].
   */
  override fun calculateDistanceToFinalSnap(
      layoutManager: RecyclerView.LayoutManager,
      targetView: View
  ): IntArray {
    val helper = getOrientationHelper(layoutManager)
    val distance = getCoordinateDelta(targetView, helper)
    return intArrayOf(
        if (layoutManager.canScrollHorizontally()) distance else 0, // x-axis
        if (layoutManager.canScrollVertically()) distance else 0 // y-axis
    )
  }

  /**
   * Finds and returns the views to snap.
   *
   * Iterates the all children views currently inflated and calculates the distance from base coordinate.
   * After checking all the children views, returns the child view that has the closest distance.
   * If all children views are invalid, just returns null.
   */
  override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
    val helper = getOrientationHelper(layoutManager)
    val firstIndex = 0
    val lastIndex = layoutManager.itemCount - 1
    val childCount = layoutManager.childCount
    if (childCount == 0) return null

    var closestChild: View? = null
    var closestPosition = RecyclerView.NO_POSITION
    var closestDelta = Integer.MAX_VALUE

    for (i in 0 until childCount) {
      val child = layoutManager.getChildAt(i) as View
      val position = layoutManager.getPosition(child)
      val delta = abs(getCoordinateDelta(child, helper))

      // RecyclerView reached start
      if (previousClosestPosition != firstIndex && position == firstIndex) {
        if (isReachedToTheStartEdge(child, layoutManager)) {
          closestChild = child
          closestPosition = position
          break
        }
      }
      // RecyclerView reached end
      if (previousClosestPosition != lastIndex && position == lastIndex) {
        if (isReachedToTheEndEdge(child, layoutManager)) {
          closestChild = child
          closestPosition = position
          break
        }
      }

      // position is invalid
      if (position % interval != 0) {
        continue
      }

      // check if it has the closest delta
      if (delta < closestDelta) {
        closestDelta = delta
        closestChild = child
        closestPosition = position
      }
    }
    if (closestPosition != RecyclerView.NO_POSITION) {
      previousClosestPosition = closestPosition
    }
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
    val helper = getOrientationHelper(layoutManager)
    val velocity = if (layoutManager.canScrollHorizontally()) velocityX else velocityY
    val firstIndex = 0
    val lastIndex = layoutManager.itemCount - 1
    val range = if (0 < velocity) firstIndex..lastIndex else lastIndex downTo firstIndex
    val progression = range step 1
    val iterator = progression.iterator()

    var index: Int = if (0 < velocity) firstIndex else lastIndex

    // find first valid position
    // FIXME binary search could improve the speed to find the first the valid position.
    while (iterator.hasNext()) {
      index = iterator.next()
      val view = layoutManager.findViewByPosition(index) ?: continue
      val delta = getCoordinateDelta(view, helper)
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

  /**
   * Sets [OnSnapListener] to [MultiSnapHelper]
   */
  fun setListener(listener: OnSnapListener) {
    this.listener = listener
  }

  /**
   * Gets the delta between base coordinate and [targetView] coordinate.
   * If the returned value is positive, it means [targetView] is after the base coordinate,
   * otherwise [targetView] is before the base coordinate.
   */
  private fun getCoordinateDelta(targetView: View, orientationHelper: OrientationHelper): Int {
    val childCoordinate = coordinateHelper.getTargetCoordinate(targetView, orientationHelper)
    val baseCoordinate = coordinateHelper.getBaseCoordinate(orientationHelper)
    return childCoordinate - baseCoordinate
  }

  /**
   * Gets [OrientationHelper] based on the layout orientation.
   * If [OrientationHelper] was created before, it returns the one previously created.
   */
  private fun getOrientationHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
    return orientationHelper ?: when {
      layoutManager.canScrollHorizontally() -> createHorizontalHelper(layoutManager)
      layoutManager.canScrollVertically() -> createVerticalHelper(layoutManager)
      else -> throw IllegalStateException("unknown orientation")
    }.also { newOrientationHelper ->
      this.orientationHelper = newOrientationHelper
    }
  }

  /**
   * Creates and returns [CoordinateHelper] base on the [SnapGravity]
   *
   * @see [CoordinateHelper]
   */
  private fun createLayoutPositionHelper(gravity: SnapGravity): CoordinateHelper {
    return when (gravity) {
      SnapGravity.CENTER -> CenterCoordinateHelper()
      SnapGravity.START -> StartCoordinateHelper()
      SnapGravity.END -> EndCoordinateHelper()
    }
  }

  /**
   * Check if [view] is reached to the Start edge.
   */
  private fun isReachedToTheStartEdge(
      view: View,
      layoutManager: RecyclerView.LayoutManager
  ): Boolean {
    val orientationHelper = getOrientationHelper(layoutManager)
    val coordinateHelper = StartCoordinateHelper()
    val childCoordinate = coordinateHelper.getTargetCoordinate(view, orientationHelper)
    val baseCoordinate = coordinateHelper.getBaseCoordinate(orientationHelper)
    return abs(childCoordinate - baseCoordinate) == 0
  }

  /**
   * Check if [view] is reached to the End edge.
   */
  private fun isReachedToTheEndEdge(
      view: View,
      layoutManager: RecyclerView.LayoutManager
  ): Boolean {
    val orientationHelper = getOrientationHelper(layoutManager)
    val coordinateHelper = EndCoordinateHelper()
    val childCoordinate = coordinateHelper.getTargetCoordinate(view, orientationHelper)
    val baseCoordinate = coordinateHelper.getBaseCoordinate(orientationHelper)
    return abs(childCoordinate - baseCoordinate) == 0
  }

  companion object {

    val DEFAULT_GRAVITY = SnapGravity.START

    const val DEFAULT_INTERVAL = 1

    const val DEFAULT_SPEED_MS_PER_INCH = 100f
  }
}
