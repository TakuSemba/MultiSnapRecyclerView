package com.takusemba.multisnaprecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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
    SnapHelperDelegator(int snapCount) {
        this.snapCount = snapCount;
    }

    private int snapCount;
    private int previousClosestPosition = RecyclerView.NO_POSITION;

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
        final OrientationHelper helper = layoutManager.canScrollHorizontally()
                ? OrientationHelper.createHorizontalHelper(layoutManager)
                : OrientationHelper.createVerticalHelper(layoutManager);
        final int containerPosition = getContainerPosition(layoutManager, helper);
        final int childCount = layoutManager.getChildCount();
        final boolean isReverseLayout = ((LinearLayoutManager) layoutManager).getReverseLayout();
        final int startPosition = isReverseLayout ? layoutManager.getItemCount() - 1 : 0;
        final int endPosition = isReverseLayout ? 0 : layoutManager.getItemCount() - 1;

        View closestChild = null;
        int closestPosition = RecyclerView.NO_POSITION;
        int absClosest = Integer.MAX_VALUE;

        if (childCount == 0) return null;
        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childPosition = getChildPosition(child, helper);
            int absDistance = Math.abs(childPosition - containerPosition);
            if (helper.getDecoratedStart(child) == 0
                    && previousClosestPosition != startPosition
                    && layoutManager.getPosition(child) == startPosition) {
                //RecyclerView reached start
                closestChild = child;
                closestPosition = layoutManager.getPosition(closestChild);
                break;
            }
            if (helper.getDecoratedEnd(child) == helper.getTotalSpace()
                    && previousClosestPosition != endPosition
                    && layoutManager.getPosition(child) == endPosition) {
                //RecyclerView reached end
                closestChild = child;
                closestPosition = layoutManager.getPosition(closestChild);
                break;
            }
            if (previousClosestPosition == layoutManager.getPosition(child) && containerPosition == childPosition) {
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
        previousClosestPosition = closestPosition == RecyclerView.NO_POSITION
                ? previousClosestPosition
                : closestPosition;
        return closestChild;
    }

    @Override
    int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        final boolean isReverseLayout = ((LinearLayoutManager) layoutManager).getReverseLayout();
        if (previousClosestPosition == RecyclerView.NO_POSITION) {
            View view = findSnapView(layoutManager);
            return layoutManager.getPosition(view);
        }

        boolean forwardDirection = layoutManager.canScrollHorizontally() ? velocityX > 0 : velocityY > 0;
        forwardDirection = isReverseLayout != forwardDirection;
        if (forwardDirection) {
            for (int i = 1; i <= snapCount; i++) {
                if ((previousClosestPosition + i) % snapCount == 0) {
                    return previousClosestPosition + i;
                }
            }
        } else {
            for (int i = 1; i <= snapCount; i++) {
                if ((previousClosestPosition - i) % snapCount == 0) {
                    return previousClosestPosition - i;
                }
            }
        }
        int targetSnapPosition = forwardDirection
                ? previousClosestPosition + snapCount
                : previousClosestPosition - snapCount;
        return isReverseLayout ? layoutManager.getItemCount() - targetSnapPosition : targetSnapPosition;
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

}
