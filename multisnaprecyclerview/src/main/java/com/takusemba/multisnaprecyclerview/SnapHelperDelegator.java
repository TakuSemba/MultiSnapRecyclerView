package com.takusemba.multisnaprecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Target
 *
 * @author takusemba
 * @since 30/07/2017
 **/
abstract class SnapHelperDelegator extends BaseSnapHelperDelegator {

    /**
     * Constructor
     *
     * @param snapCount the number of items to scroll over
     */
    SnapHelperDelegator(int snapCount, boolean isNoOffset) {
        this.snapCount = snapCount;
        this.isNoOffset = isNoOffset;
    }

    private int snapCount;
    private boolean isNoOffset;
    private int previousClosestPosition = 0;
    private OnSnapListener listener;

    void setListener(OnSnapListener listener){
        this.listener = listener;
    }

    @Override
    int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = getDistance(layoutManager, targetView, OrientationHelper.createHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = getDistance(layoutManager, targetView, OrientationHelper.createVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    @Override
    View findSnapView(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper helper = layoutManager.canScrollHorizontally()
                ? OrientationHelper.createHorizontalHelper(layoutManager)
                : OrientationHelper.createVerticalHelper(layoutManager);
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) return null;

        View closestChild = null;
        int closestPosition = RecyclerView.NO_POSITION;
        final int containerPosition = getContainerPosition(layoutManager, helper);
        int absClosest = Integer.MAX_VALUE;

        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childPosition = getChildPosition(child, helper);
            int absDistance = Math.abs(childPosition - containerPosition);
            if (helper.getDecoratedStart(child) == 0
                    && previousClosestPosition != 0
                    && layoutManager.getPosition(child) == 0) {
                //RecyclerView reached start
                closestChild = child;
                closestPosition = layoutManager.getPosition(closestChild);
                break;
            }
            if (helper.getDecoratedEnd(child) == helper.getTotalSpace()
                    && previousClosestPosition != layoutManager.getItemCount() - 1
                    && layoutManager.getPosition(child) == layoutManager.getItemCount() - 1) {
                //RecyclerView reached end
                closestChild = child;
                closestPosition = layoutManager.getPosition(closestChild);
                break;
            }
            if (previousClosestPosition == layoutManager.getPosition(child) && getDistance(layoutManager, child, helper) == 0) {
                //child is already set to the position.
                closestChild = child;
                break;
            }
            if (layoutManager.getPosition(child) % snapCount != 0) {
                continue;
            }
            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
                closestPosition = layoutManager.getPosition(closestChild);
            }
        }
        previousClosestPosition = closestPosition == RecyclerView.NO_POSITION ? previousClosestPosition : closestPosition;
        isNoOffset = getDistance(layoutManager, closestChild, helper) == 0;
        if (listener != null && closestPosition != RecyclerView.NO_POSITION) {
            listener.snapped(closestPosition);
        }
        return closestChild;
    }

    @Override
    int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        OrientationHelper helper = layoutManager.canScrollHorizontally()
                ? OrientationHelper.createHorizontalHelper(layoutManager)
                : OrientationHelper.createVerticalHelper(layoutManager);
        boolean forwardDirection = layoutManager.canScrollHorizontally() ? velocityX > 0 : velocityY > 0;
        if (!isNoOffset) {
            if (forwardDirection) {
                for (int i = 1; i <= layoutManager.getItemCount(); i++) {
                    if ((previousClosestPosition + i) % snapCount == 0) {
                        View view = layoutManager.findViewByPosition(previousClosestPosition + i);
                        if (view != null && shouldSkipTarget(view, layoutManager, helper, forwardDirection)) {
                            continue;
                        }
                        return previousClosestPosition + i;
                    }
                }
            } else {
                for (int i = 1; i <= layoutManager.getItemCount(); i++) {
                    if ((previousClosestPosition - i) % snapCount == 0) {
                        View view = layoutManager.findViewByPosition(previousClosestPosition - i);
                        if (view != null && shouldSkipTarget(view, layoutManager, helper, forwardDirection)) {
                            continue;
                        }
                        return previousClosestPosition - i;
                    }
                }
            }
        }
        return forwardDirection ? previousClosestPosition + snapCount : previousClosestPosition - snapCount;
    }

    /**
     * calculate the distance between
     * the {@link SnapHelperDelegator#getContainerPosition(RecyclerView.LayoutManager, OrientationHelper)} and
     * the {@link SnapHelperDelegator#getChildPosition(View, OrientationHelper)}
     *
     * @return the distance to the gravitated snap position
     */
    abstract int getDistance(RecyclerView.LayoutManager layoutManager, View targetView, OrientationHelper helper);

    /**
     * find the position to snap.
     *
     * @return the gravitated snap position.
     */
    abstract int getContainerPosition(RecyclerView.LayoutManager layoutManager, OrientationHelper helper);

    /**
     * find the position where the RecyclerView will start to snap
     *
     * @return the position of the gravitated side on the target view
     */
    abstract int getChildPosition(View targetView, OrientationHelper helper);

    /**
     * check if the view should be skipped or not
     *
     * @return true if the view should be skipped, otherwise false
     */
    abstract boolean shouldSkipTarget(View targetView, RecyclerView.LayoutManager layoutManager, OrientationHelper helper, boolean forwardDirection);

}
