package com.takusemba.multisnaprecyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Implements common methods.
 *
 * @param snapCount the number of items to scroll over
 */
abstract class SnapHelperDelegator(private val snapCount: Int) : BaseSnapHelperDelegator() {

  /**
   * previousClosestPosition should only be set in [findSnapView]
   */
  private var previousClosestPosition = 0
  private var listener: OnSnapListener? = null

  override fun setListener(listener: OnSnapListener) {
    this.listener = listener
  }

  override fun calculateDistanceToFinalSnap(
      layoutManager: RecyclerView.LayoutManager,
      targetView: View
  ): IntArray {
    val out = IntArray(2)
    if (layoutManager.canScrollHorizontally()) {
      out[0] = getDistance(
          layoutManager,
          targetView,
          OrientationHelper.createHorizontalHelper(layoutManager)
      )
    } else {
      out[0] = 0
    }

    if (layoutManager.canScrollVertically()) {
      out[1] = getDistance(
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
    val containerPosition = getContainerPosition(layoutManager, helper)
    var absClosest = Integer.MAX_VALUE

    for (i in 0 until childCount) {
      val child = layoutManager.getChildAt(i)!!
      val childPosition = getChildPosition(child, helper)
      val absDistance = Math.abs(childPosition - containerPosition)
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
      if (previousClosestPosition == layoutManager.getPosition(child) && getDistance(
              layoutManager, child, helper) == 0) {
        // child is already set to the position.
        closestChild = child
        closestPosition = layoutManager.getPosition(closestChild)
        break
      }
      if (layoutManager.getPosition(child) % snapCount != 0) {
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
      if (view == null || shouldSkipTarget(view, layoutManager, helper, forwardDirection)) {
        i = if (forwardDirection) i + 1 else i - 1
        continue
      }
      return if (forwardDirection) {
        val diff = i - previousClosestPosition
        val factor = if (diff % snapCount == 0) diff / snapCount else diff / snapCount + 1
        previousClosestPosition + snapCount * factor
      } else {
        val diff = previousClosestPosition - i
        val factor = if (diff % snapCount == 0) diff / snapCount else diff / snapCount + 1
        if (previousClosestPosition == layoutManager.itemCount - 1 && previousClosestPosition % snapCount != 0) {
          previousClosestPosition - previousClosestPosition % snapCount + snapCount - snapCount * factor
        } else {
          previousClosestPosition - snapCount * factor
        }
      }
    }
    // reached to end or start
    return if (forwardDirection) layoutManager.itemCount - 1 else 0
  }

  /**
   * calculate the distance between the
   * [SnapHelperDelegator.getContainerPosition]
   * and the [SnapHelperDelegator.getChildPosition]
   *
   * @return the distance to the gravitated snap position
   */
  abstract fun getDistance(
      layoutManager: RecyclerView.LayoutManager, targetView: View,
      helper: OrientationHelper
  ): Int

  /**
   * find the position to snap.
   *
   * @return the gravitated snap position.
   */
  abstract fun getContainerPosition(
      layoutManager: RecyclerView.LayoutManager,
      helper: OrientationHelper
  ): Int

  /**
   * find the position where the RecyclerView will start to snap
   *
   * @return the position of the gravitated side on the target view
   */
  abstract fun getChildPosition(targetView: View, helper: OrientationHelper): Int

  /**
   * check if the view should be skipped or not
   *
   * @return true if the view should be skipped, otherwise false
   */
  abstract fun shouldSkipTarget(
      targetView: View, layoutManager: RecyclerView.LayoutManager,
      helper: OrientationHelper, forwardDirection: Boolean
  ): Boolean
}
