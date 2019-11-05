package com.takusemba.multisnaprecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import android.view.View;

/**
 * Helper class that extends SnapHelper to archive snapping
 **/
class MultiSnapHelper extends SnapHelper {

  private BaseSnapHelperDelegator snapHelper;
  private LinearSmoothScroller scroller;

  /**
   * Creates MultiSnapHelper
   *
   * @param gravity gravity to which the RecyclerView snaps
   * @param snapCount the number of items to scroll over
   */
  MultiSnapHelper(SnapGravity gravity, int snapCount, LinearSmoothScroller scroller) {
    this.scroller = scroller;
    switch (gravity) {
      case CENTER:
        snapHelper = new CenterSnapHelperDelegator(snapCount);
        break;
      case START:
        snapHelper = new StartSnapHelperDelegator(snapCount);
        break;
      case END:
        snapHelper = new EndSnapHelperDelegator(snapCount);
        break;
      default:
        throw new IllegalArgumentException("not supported gravity");
    }
  }

  @Nullable @Override
  public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
      @NonNull View view) {
    return snapHelper.calculateDistanceToFinalSnap(layoutManager, view);
  }

  @Nullable @Override public View findSnapView(RecyclerView.LayoutManager layoutManager) {
    return snapHelper.findSnapView(layoutManager);
  }

  @Override
  public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX,
      int velocityY) {
    return snapHelper.findTargetSnapPosition(layoutManager, velocityX, velocityY);
  }

  void setListener(OnSnapListener listener) {
    snapHelper.setListener(listener);
  }

  /**
   * Creates a scroller to be used in the snapping implementation.
   *
   * @param layoutManager The {@link RecyclerView.LayoutManager} associated with the attached {@link
   * RecyclerView}.
   * @return a {@link LinearSmoothScroller} which will handle the scrolling.
   */
  @Nullable protected LinearSmoothScroller createSnapScroller(
      final RecyclerView.LayoutManager layoutManager) {
    if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
      return null;
    }
    return scroller;
  }
}
